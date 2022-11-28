package com.example.simplesaletransection;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.simplesaletransection.network.ActiveRequestPacket;
import com.example.simplesaletransection.network.CommObservable;
import com.example.simplesaletransection.network.CoreException;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class CardDetailActivity extends AppCompatActivity {

    LinkedHashMap<String,String> stringHashMap=new LinkedHashMap<String, String>();
    String requestPacket="";
    Button sbutton;
    EditText cardno,cardexp;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public void onCreate( @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);
        cardno=(EditText)findViewById(R.id.cardno);
        cardexp=(EditText)findViewById(R.id.cardexp);
        sbutton=(Button)findViewById(R.id.submitbutton);
        sbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cardnos = cardno.getText().toString();
                String expiry = cardexp.getText().toString();

                stringHashMap.put("TPDU","6000530000");
                stringHashMap.put("MTI","0200");
                stringHashMap.put("BITMAP","7024058000C00000");
                stringHashMap.put("DE2",cardnos);
                stringHashMap.put("DE3","000000");
                stringHashMap.put("DE4","000000002002");
                stringHashMap.put("DE11","007778");
                stringHashMap.put("DE14",expiry);
                stringHashMap.put("DE22","0011");
                stringHashMap.put("DE24","0280");
                stringHashMap.put("DE25","00");
                stringHashMap.put("DE41","485059424C303038");
                stringHashMap.put("DE42","48505942494A41C49503030303038");

                Iterator<Map.Entry<String,String>> it=stringHashMap.entrySet().iterator();
                while (it.hasNext()){
                    Map.Entry<String,String> set=(Map.Entry<String, String>) it.next();
                    System.out.println(set.getKey()+"="+ set.getValue());

                    requestPacket+=set.getValue();


                }
                ActiveRequestPacket.getInstance().setRequest(requestPacket);

                new MyRequest().execute();


            }
        });


        //return activitypaymentcardBinding.getRoot();
    }
    private class MyRequest extends AsyncTask<Void,Void,Void> {
        String result;


        @Override
        protected Void doInBackground(Void... voids) {

            try {

                CommObservable.process();
//                responseEntity.setValues();
            }catch (CoreException e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid){

            super.onPostExecute(aVoid);


        }


    }

}
