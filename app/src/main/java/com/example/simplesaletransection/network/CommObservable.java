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


import android.util.Log;


import com.example.simplesaletransection.ResponseActivity;
import com.example.simplesaletransection.ResponseEntity;
import com.example.simplesaletransection.iso.Iso8583BitProfile;
import com.example.simplesaletransection.iso.IsoJson;

import org.json.JSONException;
import org.json.JSONObject;

public class CommObservable {



    //    private static final Logger logger = Logger.getNewLogger(CommObservable.class.getName());
    private static SocketClient socketClient = null;

    private CommObservable() {
    }

    public static void process() throws CoreException {
//        logger.info("## CommObservable::process");

//        if (ActiveTransactionCache.getInstance().getRequest() == null) {
//            logger.info("## CommObservable::no data ignore");
//            return;
//        }
//        String resData = "600053000002003020058000C009140000000000000050000077820011028000485059424C30303848505942494A414C49503030303038303030303030303030303030303030303030303030303030303100123030303030303030353030300006303030303033";
        String resData = "600053000002007024058000C0091616522852030008790700000000000000200200777802220011028000485059424C30303848505942494A414C4950303030303830303030303030303030303030303030303030303030303030310012303030303030303032303030000630303030303100143338303030303030303030303032";

        CommData commData = CommData.getInstance();
//        commData.setTransType(ActiveTransactionCache.getInstance().getTransType());
        commData.setCommType(CommType.COMM_TYPE_SOCKET);

        System.out.println("ActiveRequestPacket.getInstance().getRequest() : "+ActiveRequestPacket.getInstance().getRequest());
        commData.setHostIp("192.168.137.1");
        commData.setPort(5555);

        commData.setCommTimeout(30 * 1000);
        commData.setConnectTimes(1);
//        commData.setSendData(resData);
        commData.setSendData(ActiveRequestPacket.getInstance().getRequest());

//        logger.info("## CommObservable::process Request : " + ActiveTransactionCache.getInstance().getRequest());

        socketClient = new SocketClient();
        socketClient.send(commData, true, new NetworkCommListener() {
            @Override
            public void onNext(CommData commData) throws CoreException {
//                logger.info("## NetworkCommListener::onNext");
                if (commData != null) {
                    if (commData.getCommStatus().equals(CommStatus.ON_RECEIVED)) {
                        System.out.println("commData.getRecvData() : " + commData.getRecvData());
                        System.out.println(">>>commData.getRecvData() :" + Utils.asc2Bcd(commData.getRecvData()));
                        ActiveRequestPacket.getInstance().setResponse(commData.getRecvData());
                        /*byte[] readBuffer = Utils.asc2Bcd(commData.getRecvData());
                        byte[] btIso8583 = new byte[readBuffer.length - 4];

//                        System.arraycopy(readBuffer, 0, btTpdu, 0, 5);
                        System.arraycopy(readBuffer, 4, btIso8583, 0, readBuffer.length - 4);

                        String strIso8583 = Utils.bcd2Str(btIso8583);
                        Log.i("TAG", "strIso8583: " + strIso8583);
                        IsoJson.isoData2Json(strIso8583);*/
                        String bitAttrStr = Iso8583BitProfile.bitArr_req.replaceAll("'", "\"");
                        IsoJson.setBitFormat(bitAttrStr);
                        byte[] readBuffer = Utils.asc2Bcd("000000530210703800800E4000001234123412341234000000000000002002007712453005210031323334353637383930313236353433323130303848505942494A41C495030303030300");
                        byte[] btIso8583 = new byte[readBuffer.length - 4];
                        byte[] btTpdu = new byte[5];

                        System.arraycopy(readBuffer, 0, btTpdu, 0, 5);
                        System.arraycopy(readBuffer, 4, btIso8583, 0, readBuffer.length - 4);

                        String strIso8583 = Utils.bcd2Str(btIso8583);
                        Log.i(">>>", "strIso8583: " + strIso8583);
//                        IsoJson.isoData2Json(strIso8583);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject = IsoJson.isoData2Json(strIso8583);

                        try {
                            ResponseEntity responseEntity = new ResponseEntity();
                            responseEntity.setF0(jsonObject.getInt("F0"));
                            responseEntity.setF2(jsonObject.getInt("F2"));
                            responseEntity.setF3(jsonObject.getInt("F3"));
                            responseEntity.setF4(jsonObject.getInt("F4"));
                            responseEntity.setF11(jsonObject.getInt("F11"));
                            responseEntity.setF12(jsonObject.getInt("F12"));
                            responseEntity.setF13(jsonObject.getInt("F13"));
                            responseEntity.setF25(jsonObject.getInt("F25"));
                            responseEntity.setF37(jsonObject.getString("F37"));
                            responseEntity.setF38(jsonObject.getString("F38"));
                            responseEntity.setF39(jsonObject.getString("F39"));
                            responseEntity.setF42(jsonObject.getString("F42"));


                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    ResponseActivity responseActivity=new ResponseActivity();
                                    responseActivity.saved(responseEntity);
                                }
                            }).start();




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//
//                        ActiveTransactionCache.getInstance().setIsoDump(ISOUtil.parseResponseISODump(commData.getRecvData()));
                    }
                } else {
//                    logger.info("## NetworkCommListener::CommData is null");
                    throw new CoreException(CoreExceptionType.GENERAL_ERROR_OBJECT_NULL);
                }
            }


            @Override
            public void onError(Throwable throwable) throws CoreException {
//                logger.info("## NetworkCommListener::onError");
                if (throwable instanceof CoreException) {
                    throw (CoreException) throwable;
                }
            }
        });


    }


//    public static void checkNetworkStatus() throws CoreException {
////        logger.info("## CommObservable::checkNetworkStatus");
//
//        if (!isNetworkAvailable()) {
//            throw new CoreException(CoreExceptionType.NETWORK_SOCKET_EXCEPTION);
//        } else {
////            logger.info("## CommObservable::checkNetworkStatus : Available");
//        }
//
//    }

//    public static boolean isNetworkAvailable() {
//        ConnectivityManager cm = (ConnectivityManager) AppContext.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = cm.getActiveNetworkInfo();
//        if (info != null && info.isConnected())
//            return true;
//
//        return false;
//    }
}
