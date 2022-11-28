/*
 *  Copyright (c) 2020, Girmiti Software Pvt. Ltd. and/or its
 *  affiliates. All rights reserved.
 *
 *  Please refer to the file LICENSE.TXT for full details.
 *
 *  TO THE EXTENT PERMITTED BY LAW, THE SOFTWARE IS PROVIDED "AS IS", WITHOUT
 *  WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NON INFRINGEMENT. TO THE EXTENT PERMITTED BY LAW, IN NO EVENT SHALL
 *  MASTERCARD OR ITS AFFILIATES BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *  IN THE SOFTWARE.
 */
package com.example.simplesaletransection.network;




import static cn.verifone.commujar.CommuJarError.COMMUJAR_PARAMETER_INVALID;
import static cn.verifone.commujar.CommuJarError.COMMUJAR_READ_TIMEOUT;
import static cn.verifone.commujar.CommuJarError.COMMUJAR_SOCKET_DISCONNECTED;

import android.util.Log;
import android.util.Pair;

import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

import cn.verifone.commujar.SocketConnection;


public class SocketClient implements NetworkClient {

//    private static Logger logger = Logger.getNewLogger(SocketClient.class.getName());

    private SocketConnection socketConnection;
    private static final int ISO_PACKET_LENGTH = 2;
    private Socket socket = null;

    public SocketClient() {
        socketConnection = new SocketConnection();
    }

    @Override
    public boolean connect(com.example.simplesaletransection.network.CommData commDataIn, NetworkCommListener listener) throws CoreException {

        boolean isSSLMode = false;
        String certPassWord = null;
        InputStream inputStream = null;

        ArrayList<Pair> pairArrayList = new ArrayList<>();
        pairArrayList.add(Pair.create(commDataIn.getHostIp(), commDataIn.getPort()));

        if (commDataIn.isBackupIpValid()) {
            pairArrayList.add(Pair.create(commDataIn.getHostIp2(), commDataIn.getPort2()));
        }

        //Try multi times
        for (int curTimes = 1; curTimes <= commDataIn.getConnectTimes(); curTimes++) {
            //Try ip1, ip2
            for (Pair pair : pairArrayList
            ) {
                listener.onNext(com.example.simplesaletransection.network.CommData.getInstance(CommStatus.ON_CONNECTING, (String) pair.first, (int) pair.second, curTimes));
//                logger.info("Connecting..... IP: " + pair.first + "  PORT :" + pair.second + "....");
                Log.i("SocketClient","Connecting..... IP: " + pair.first + "  PORT :" + pair.second + "....");
                if (socket != null) {
//                    logger.info("Connected IP :" + pair.first + "  PORT:" + pair.second + "......");
                    Log.i("SocketClient","Connected IP :" + pair.first + "  PORT:" + pair.second + "......");
                    listener.onNext(com.example.simplesaletransection.network.CommData.getInstance(CommStatus.ON_CONNECTED));
                }
                socket = socketConnection.connect2Host((String) pair.first, (int) pair.second, commDataIn.getCommTimeout(), isSSLMode, inputStream, certPassWord);
                if (socket != null) {
//                    logger.info("Connected IP: " + pair.first + "  PORT: " + pair.second + ".....");
                    listener.onNext(com.example.simplesaletransection.network.CommData.getInstance(CommStatus.ON_CONNECTED));
                    return true;
                } else {
                    listener.onError(new CoreException(CoreExceptionType.NETWORK_SOCKET_EXCEPTION));
                }
            }
        }

        return false;
    }

    @Override
    public void send(com.example.simplesaletransection.network.CommData commDataIn, boolean disconnect, NetworkCommListener listener) throws CoreException {

        if (!this.connect(commDataIn, listener)) {
            return;
        }

        if (!this.socketCommSend(commDataIn, listener)) {
            return;
        }

        this.socketCommRecv(commDataIn, listener);
    }

    @Override
    public boolean disconnect() {
        socketConnection.close(socket);
//        logger.info("Socket is disconnected");
        socket = null;
        return true;
    }

    private boolean socketCommSend(com.example.simplesaletransection.network.CommData commDataIn, NetworkCommListener listener) throws CoreException {

        if (this.socket == null) {
//            logger.info("Socket is null");
            listener.onError(new CoreException(CoreExceptionType.NETWORK_SEND_EXCEPTION));
            return false;
        }
        // Send data
        Log.i("SocketClient","Sending..... IP: " + socket.getInetAddress().getHostAddress() + "[" + socket.getPort() + "]");
        listener.onNext(com.example.simplesaletransection.network.CommData.getInstance(CommStatus.ON_SENDING, socket.getInetAddress().getHostAddress(), socket.getPort()));

        // add data header, 2 bytes
        byte[] initData = com.example.simplesaletransection.network.Utils.asc2Bcd(commDataIn.getSendData());
//        byte[] sendData = new byte[initData.length + 3];
//        sendData[0] = (byte) ((initData.length + 1 >> 8) & 0xFF);
//        sendData[1] = (byte) (initData.length + 1 & 0xFF);
//        sendData[2] = (byte) ( 0x42);
//        System.arraycopy(initData, 0, sendData, 3, initData.length);

        byte[] sendData = new byte[initData.length + 2];
        sendData[0] = (byte) ((initData.length >> 8) & 0xFF);
        sendData[1] = (byte) (initData.length & 0xFF);
        System.arraycopy(initData, 0, sendData, 2, initData.length);

//        logger.info("--> Request " + Utils.bcd2Asc(sendData));

        if (socketConnection.sendData(socket, sendData)) {
            listener.onNext(com.example.simplesaletransection.network.CommData.getInstance(CommStatus.ON_SENT, commDataIn.getCurrSendRetryTimes()));
            return true;
        } else {
            // Disconnect socket
            this.disconnect();
            listener.onError(new CoreException(CoreExceptionType.NETWORK_SEND_EXCEPTION));
            return false;
        }
    }

    private boolean socketCommRecv(CommData commDataIn, NetworkCommListener listener) throws CoreException {

        int errorCode;
        // Receive data header
        Log.i("SocketClient","Receiving..... IP: " + socket.getInetAddress().getHostAddress() + "[" + socket.getPort() + "]");
        listener.onNext(CommData.getInstance(CommStatus.ON_RECEIVING, socket.getInetAddress().getHostAddress(), socket.getPort()));

        byte[] readLengthBuffer = socketConnection.recvData(socket, 2, commDataIn.getCommTimeout());

        if (readLengthBuffer == null) {
            // Disconnect socket
            this.disconnect();
            errorCode = socketConnection.getErrorCode();
            if (errorCode == COMMUJAR_PARAMETER_INVALID) {
                listener.onError(new CoreException(CoreExceptionType.NETWORK_INPUT_PARAM_EXCEPTION));
                return false;
            } else if (errorCode == COMMUJAR_SOCKET_DISCONNECTED) {
                listener.onError(new CoreException(CoreExceptionType.NETWORK_DISCONNECT_EXCEPTION));
                return false;
            } else if (errorCode == COMMUJAR_READ_TIMEOUT) {
                listener.onError(new CoreException(CoreExceptionType.NETWORK_TIMEOUT_EXCEPTION));
                return false;
            } else {
                listener.onError(new CoreException(CoreExceptionType.NETWORK_RECEIVE_EXCEPTION));
                return false;
            }
        }

        if (readLengthBuffer.length != ISO_PACKET_LENGTH) {
            // Disconnect socket
            this.disconnect();
            listener.onError(new CoreException(CoreExceptionType.NETWORK_RECEIVE_EXCEPTION));
            return false;
        }

        int readLength = (readLengthBuffer[1] & 0xFF) + ((readLengthBuffer[0] & 0xFF) << 8);
        Log.i("SocketClient","Received length:" + readLength);

        // read & ignore encoder byte
        socketConnection.recvData(socket, 1, commDataIn.getCommTimeout());

        // Receive data
        byte[] recvData = socketConnection.recvData(socket, readLength, commDataIn.getCommTimeout());

        if (recvData == null) {
            // Disconnect socket
            this.disconnect();
            errorCode = socketConnection.getErrorCode();
            if (errorCode == COMMUJAR_PARAMETER_INVALID) {
                listener.onError(new CoreException(CoreExceptionType.NETWORK_INPUT_PARAM_EXCEPTION));
                return false;
            } else if (errorCode == COMMUJAR_SOCKET_DISCONNECTED) {
                listener.onError(new CoreException(CoreExceptionType.NETWORK_DISCONNECT_EXCEPTION));
                return false;
            } else if (errorCode == COMMUJAR_READ_TIMEOUT) {
                listener.onError(new CoreException(CoreExceptionType.NETWORK_TIMEOUT_EXCEPTION));
                return false;
            } else {
                listener.onError(new CoreException(CoreExceptionType.NETWORK_RECEIVE_EXCEPTION));
                return false;
            }
        }

        if (recvData.length != readLength) {
            // Disconnect socket
            this.disconnect();
            listener.onError(new CoreException(CoreExceptionType.NETWORK_RECEIVE_EXCEPTION));
            return false;
        }

//        logger.info("--> Response " + Utils.bcd2Asc(recvData));
        listener.onNext(com.example.simplesaletransection.network.CommData.getInstance(CommStatus.ON_RECEIVED, com.example.simplesaletransection.network.Utils.bcd2Asc(recvData)));
        commDataIn.setCurrSendRetryTimes(0);

        // Disconnect socket
        this.disconnect();

        return true;
    }
}