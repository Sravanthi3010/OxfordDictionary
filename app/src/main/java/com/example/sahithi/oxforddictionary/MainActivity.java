package com.example.sahithi.oxforddictionary;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText word;
    TextView meaning;
    Button button;

    String root = "https://od-api.oxforddictionaries.com:443/api/v1/entries/en/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        word = findViewById(R.id.word);
        meaning = findViewById(R.id.display);
        button = findViewById(R.id.getm);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performReq(root+word.getText().toString().toLowerCase());
            }
        });

    }
    public void performReq(String url) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject obj=new JSONObject(response);
                            JSONArray results=obj.getJSONArray("results");

                            JSONObject obj1=results.getJSONObject(0);
                            JSONArray results1=obj1.getJSONArray("lexicalEntries");

                            JSONObject obj2=results1.getJSONObject(0);
                            JSONArray results2=obj2.getJSONArray("entries");

                            JSONObject obj3=results2.getJSONObject(0);
                            JSONArray results3=obj3.getJSONArray("senses");

                            JSONObject obj4=results3.getJSONObject(0);
                            JSONArray results4=obj4.getJSONArray("definitions");
                            meaning.setText(results4.getString(0));
                        }
                        catch (Exception e)
                        {
                        }

                        //The string response contains the response you get back from the request it could be json, xml or just string





                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                })            {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Accept","application/json");
                headers.put("app_id","e963ef3c");
                headers.put("app_key","a359129c16f18811012a487f50171258");

                //Add headers here as headers.put("key","value");

                return headers;
            }
            @Override
            public Map<String,String> getParams(){
                Map<String,String> mParams=new HashMap<>();
                //Add your parameters here as mParams.put("key","value"); you will most probably use this method when doing a post request
                return mParams;
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }

        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }



}
