package com.example.simplesaletransection;

import android.content.Context;

public class ResponseActivity{

    public void saved(ResponseEntity responseEntity){
        DatabaseHelper.getInstance(getApplicationContext()).getAppDatabase().responseDataDao().registerResponseData(responseEntity);
    }
    public Context getApplicationContext() {

        Context applicationContext=null;
        return applicationContext;
    }
}
