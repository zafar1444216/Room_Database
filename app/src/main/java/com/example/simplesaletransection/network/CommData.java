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


public class CommData {

    private CommType commType;
    private String hostIp;
    private int port;
    private String hostIp2;
    private int port2;
    private String currentIp;
    private int currentPort;
    private boolean backupIpValid;
    private String hostUrl;
    private int commTimeout;
    private int connectTimes;
    private int currentConnectTimes = 1;
    private int currSendRetryTimes;
    private String sendData;
    private String recvData;
    private com.example.simplesaletransection.network.CommStatus commStatus;

    public static CommData getInstance() {
        return new CommData();
    }

    public static CommData getInstance(com.example.simplesaletransection.network.CommStatus commStatus) {
        CommData commData = new CommData();
        commData.setCommStatus( commStatus );
        return commData;
    }

    public static CommData getInstance(com.example.simplesaletransection.network.CommStatus commStatus, int retryTimes) {
        CommData commData = new CommData();
        commData.setCommStatus( commStatus );
        switch (commStatus) {
            case ON_CONNECTING:
            case ON_CONNECTED:
                commData.setCurrentConnectTimes( retryTimes );
                break;
            case ON_SENDING:
            case ON_SENT:
                commData.setCurrSendRetryTimes( retryTimes );
                break;
            default:
                break;
        }
        return commData;
    }

    public static CommData getInstance(com.example.simplesaletransection.network.CommStatus commStatus, String recvData) {
        CommData commData = new CommData();
        commData.setCommStatus( commStatus );
        commData.setRecvData( recvData );
        return commData;
    }

    public static CommData getInstance(CommStatus commStatus, String ip, int port) {
        CommData commData = new CommData();
        commData.setCommStatus( commStatus );
        commData.setCurrentIp(ip);
        commData.setCurrentPort(port);
        return commData;
    }

    public static CommData getInstance(CommStatus commStatus, String ip, int port, int currentConnectTimes) {
        CommData commData = new CommData();
        commData.setCommStatus( commStatus );
        commData.setCurrentIp(ip);
        commData.setCurrentPort(port);
        commData.setCurrentConnectTimes(currentConnectTimes);
        return commData;
    }

    public CommType getCommType() {
        return commType;
    }

    public void setCommType(CommType commType) {
        this.commType = commType;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostIp2() {
        return hostIp2;
    }

    public void setHostIp2(String hostIp2) {
        this.hostIp2 = hostIp2;
    }

    public int getPort2() {
        return port2;
    }

    public void setPort2(int port2) {
        this.port2 = port2;
    }

    public String getCurrentIp() {
        return currentIp;
    }

    public void setCurrentIp(String currentIp) {
        this.currentIp = currentIp;
    }

    public int getCurrentPort() {
        return currentPort;
    }

    public void setCurrentPort(int currentPort) {
        this.currentPort = currentPort;
    }

    public boolean isBackupIpValid() {
        return backupIpValid;
    }

    public void setBackupIpValid(boolean backupIpValid) {
        this.backupIpValid = backupIpValid;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public int getCommTimeout() {
        return commTimeout;
    }

    public void setCommTimeout(int commTimeout) {
        this.commTimeout = commTimeout;
    }

    public int getConnectTimes() {
        return connectTimes;
    }

    public void setConnectTimes(int connectTimes) {
        this.connectTimes = connectTimes;
    }

    public int getCurrentConnectTimes() {
        return currentConnectTimes;
    }

    public void setCurrentConnectTimes(int currentConnectTimes) {
        this.currentConnectTimes = currentConnectTimes;
    }

    public int getCurrSendRetryTimes() {
        return currSendRetryTimes;
    }

    public void setCurrSendRetryTimes(int currSendRetryTimes) {
        this.currSendRetryTimes = currSendRetryTimes;
    }

    public String getSendData() {
        return sendData;
    }

    public void setSendData(String sendData) {
        this.sendData = sendData;
    }

    public String getRecvData() {
        return recvData;
    }

    public void setRecvData(String recvData) {
        this.recvData = recvData;
    }

    public CommStatus getCommStatus() {
        return commStatus;
    }

    public void setCommStatus(CommStatus commStatus) {
        this.commStatus = commStatus;
    }
}
