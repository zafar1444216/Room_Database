package com.example.simplesaletransection.network;

public class ActiveRequestPacket {

    private static ActiveRequestPacket currentData;
    private String request;
    private String response;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public static synchronized ActiveRequestPacket getInstance() {
        if (currentData == null) {
            currentData = new ActiveRequestPacket();
        }
        return currentData;
    }
}
