package com.dev.bilo.myapplication;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Receipts extends AppCompatActivity {

    List<Mpesa_messages_> employees;
    ProgressBar prog1;
    ListView listview;

    RelativeLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts);

        back =(RelativeLayout) findViewById(R.id.back_);
        prog1 = (ProgressBar) findViewById(R.id.prog);
        listview = (ListView) findViewById(R.id.listview);

        Online_check();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });
    }

    private void Online_check(){

        //TOKEN SET ON LOGIN.JAVA

        prog1.setVisibility(VISIBLE);

        RequestQueue MyRequestQueue = Volley.newRequestQueue(Receipts.this);

        //String url = API.SHOW_PROJECTS + "/" + project_id1 + "?token=" + token1;
        String url = Api.TRANSACTIONS_READ;

        Log.d("URL",url);
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                prog1.setVisibility(GONE);
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("A319", response);

                // JSON string (as you provided)
                //String jsonString = "{\"data\":[{\"phone\":\"\",\"payment_status\":\"paid\",\"payment_code\":\"\",\"date_time\":\"2024-09-07 20:55:03\"},{\"phone\":\"\",\"payment_status\":\"pending\",\"payment_code\":\"Request cancelled by user\",\"date_time\":\"2024-09-07 20:52:48\"},...]}";
                employees = new ArrayList<>();
                try {
                    // Convert the string to a JSONObject
                    JSONObject jsonObject = new JSONObject(response);

                    // Get the data array
                    JSONArray dataArray = jsonObject.getJSONArray("data");

                    // Loop through the data array
                    for (int i = 0; i < dataArray.length(); i++) {
                        // Get each JSON object inside the array
                        JSONObject obj3 = dataArray.getJSONObject(i);

                        String phone = obj3.getString("phone");
                        String payment_status = obj3.getString("payment_status");
                        String payment_code = obj3.getString("payment_code");
                        String date_time = obj3.getString("date_time");

                        //Log.d("BN2",organization_id);
                        employees.add(new Mpesa_messages_(phone,payment_status,payment_code,date_time));


                    }
                    Display_data_Adapter val = new Display_data_Adapter(Receipts.this, R.layout.list_layout_employees_main, employees);
                    //val.clear();
                    listview.setAdapter(val);
                    val.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }











            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                prog1.setVisibility(GONE);
                try {
                    if (error.networkResponse != null) {


                        int statusCode = error.networkResponse.statusCode;
                        if (error.networkResponse.data != null) {

                            String body = new String(error.networkResponse.data, "UTF-8");

                            Log.d("A36", body);

                            if (statusCode == 500) {

                                JSONObject obj = new JSONObject(body);
                                String errormessage = obj.getString("error");
                                String feedback = obj.getString("feedback");


                            }
                        }
                    }
                } catch (UnsupportedEncodingException | JSONException e) {
                    e.printStackTrace();
                    Log.e("TAG", "UNKNOWN ERROR :" + e.getMessage());
                    Toast.makeText(Receipts.this, "Something went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

        })  {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();

                //TOKEN SET ON LOGIN.JAVA
                SharedPreferences prefs1 = getApplicationContext().getSharedPreferences("LOGIN_CREDENTIALS", MODE_PRIVATE);
                String token = prefs1.getString("token", "");
                String organization_id = prefs1.getString("organization_id", "");

                MyData.put("token", token);
                MyData.put("organization_id", organization_id);

                Log.d("ABN",organization_id);


                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);

    }


    public class Display_data_Adapter extends ArrayAdapter<Mpesa_messages_> {


        Context mCtx;
        int listLayoutRes;
        List<Mpesa_messages_> employees;
        SQLiteDatabase mDatabase;

        //private DBHelper mydb ;

        public Display_data_Adapter(Context mCtx, int listLayoutRes, List<Mpesa_messages_> SMSList) {
            super(mCtx, listLayoutRes, SMSList);

            this.mCtx = mCtx;
            this.listLayoutRes = listLayoutRes;
            this.employees = SMSList;
            this.mDatabase = mDatabase;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View view = convertView;
            LayoutInflater lInflater = LayoutInflater.from(mCtx);
            if (view == null) {
                view = lInflater.inflate(listLayoutRes, null);
            }

            final Mpesa_messages_ aa = employees.get(position);

            //Log.d("BN3",aa.getEmployee_id());
            Log.d("BN3","IAKKKS");


            TextView pho_nu = view.findViewById(R.id.phone_number);
            TextView payment_code = view.findViewById(R.id.payment_code);
            TextView payment_status = view.findViewById(R.id.payment_status);
            TextView date = view.findViewById(R.id.date);

            pho_nu.setText(aa.getPhone());
            payment_code.setText(aa.getPayment_code());
            payment_status.setText(aa.getPayment_status());
            date.setText(aa.getDate());



            return view;

        }


    }

}