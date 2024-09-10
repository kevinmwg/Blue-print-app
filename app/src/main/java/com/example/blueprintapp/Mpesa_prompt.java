package com.example.blueprintapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Mpesa_prompt extends AppCompatActivity {

    ProgressBar pb;
    AppCompatButton process;
    EditText phone_number,amount;
    String aa="";
    String bb="";
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        pb = (ProgressBar) findViewById(R.id.prog);
        process = (AppCompatButton) findViewById(R.id.process); 
        phone_number =(EditText) findViewById(R.id.phone_number);
        amount =(EditText) findViewById(R.id.amount);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Mpesa_prompt.this,Receipts.class);
                startActivity(intent);
                finish();

            }
        });
        
        process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aa = phone_number.getText().toString();
                bb = amount.getText().toString();


                //validating inputs
                if (TextUtils.isEmpty(aa)) {
                    phone_number.setError("this field cannot be empty");
                    phone_number.requestFocus();
                    return;
                }

                //validating inputs
                if (TextUtils.isEmpty(bb)) {
                    amount.setError("this field cannot be empty");
                    amount.requestFocus();
                    return;
                }


                submit_online ul = new submit_online(aa,bb);
                ul.execute();
            }
        });
    }

    class submit_online  extends AsyncTask<Void, Void, String> {

        String email,password;


        submit_online(String email, String password) {
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);


        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("AV1", String.valueOf("babu1"));

            pb.setVisibility(View.GONE);

            super.onPostExecute(s);
            Log.d("AV1", String.valueOf("babu2"));

            Log.d("TEST999", " internet available");
            Log.d("AV2", String.valueOf(s));
            try {
                JSONObject obj = new JSONObject(s);
                Log.d("JY0", String.valueOf(obj));

                String error = obj.getString("error");
                Log.d("JY1", String.valueOf(error));

                if(error.equals("false")){


                }

                if(error.equals("true")){


                }


            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("ERR", String.valueOf(e));
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            //creating request handler object
            //=============================================================
            // your get/post related code..like HttpPost = new HttpPost(url);
            RequestHandler requestHandler = new RequestHandler();

            //creating request parameters
            HashMap<String, String> MyData = new HashMap<>();

            MyData.put("phone_number", aa);
            MyData.put("amount", bb);

            //returing the response
            return requestHandler.sendPostRequest(Api.PROCESS_PAYMENT, MyData);

            //=============================================================
        }

    }

}
