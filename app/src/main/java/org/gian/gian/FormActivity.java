package org.gian.gian;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = FormActivity.class.getClass().getSimpleName();
    private static final int Request_Code = 43;
    private static final int MY_SOCKET_TIMEOUT_MS = 5000 ;

    Button  upload_data;
    TextView uri_txt;
    EditText txtemail, txtname, txtphno, idea;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String choice = null;

    private String upload_url ="http://ljmcaintegrated.dx.am/gian_app/connection.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


        txtemail = (EditText) findViewById(R.id.txt_email);
        txtname = (EditText) findViewById(R.id.txt_name);
        txtphno = (EditText) findViewById(R.id.txt_phno);
        upload_data = (Button) findViewById(R.id.submit);
        idea = (EditText) findViewById(R.id.ideas);

        upload_data.setOnClickListener(this);
    }

    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.rad);

        int selectedId = radioGroup.getCheckedRadioButtonId();

        radioButton = (RadioButton) findViewById(selectedId);
        choice = radioButton.getText().toString().trim();
    }

    private void search_file() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, Request_Code);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == Request_Code && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                Path path = null;
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
                    path = Paths.get(uri.getPath());
                }
                uri_txt.setText(path.toString());
                Toast.makeText(this, "URI:: " + uri, Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submit:
                upload_data();
                break;
        }
    }

    public void upload_data() {
        addListenerOnButton();

        Toast.makeText(FormActivity.this, "Upload Data Pressed", Toast.LENGTH_SHORT).show();


        String urlPsrsmd = "?name=" + txtname.getText().toString().trim() +
                "&email=" + txtemail.getText().toString().trim() +
                "&phno=" + txtphno.getText().toString().trim() +
                "&occupation=" + radioButton.getText().toString().trim() +
                "&idea=" + idea.getText().toString().trim() +
                "&file=No_file";


        Log.d(TAG, "Url: " + upload_url + urlPsrsmd);
        try {
            StringRequest req = new StringRequest(Request.Method.GET, upload_url + urlPsrsmd, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.d(TAG, "Response: " + response);
                        Toast.makeText(FormActivity.this, "Upload Data Done", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Log.e(TAG, "Error: ", e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error: ", error);
                    Toast.makeText(FormActivity.this, "Upload Data Failed", Toast.LENGTH_SHORT).show();
                }
            });
            RetryPolicy policy = new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            req.setRetryPolicy(policy);
            MySingletone.getInstance(FormActivity.this).addToRequestQueue(req);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error: ", e);
        }
    }
}
// get selected radio button from radioGroup
// find the radiobutton by returned id
                /*Toast.makeText(FormActivity.this,
                        radioButton.getText(), Toast.LENGTH_SHORT).show();*/

 /*    select_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_file();
            }
        });
  */

 /*{
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", txtname.getText().toString().trim());
                    params.put("email", txtemail.getText().toString().trim());
                    params.put("phno", txtphno.getText().toString().trim());
                    params.put("occupation", radioButton.getText().toString().trim());
                    params.put("idea", idea.getText().toString().trim());
                    params.put("file", "No file");
                    return params;
                }
            }
            req.setRetryPolicy(new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
      */
//        params.put("name", txtname.getText().toString().trim());
//        params.put("email", txtemail.getText().toString().trim());
//        params.put("phno", txtphno.getText().toString().trim());
//        params.put("occupation", radioButton.getText().toString().trim());
//        params.put("idea", idea.getText().toString().trim());
//        params.put("file", "No file");
//        Multipart Request, Android Hive
//        HashMap<String, String> params = new HashMap<>();
//        params.put("name", txtname.getText().toString().trim());
//        params.put("email", txtemail.getText().toString().trim());
//        params.put("phno", txtphno.getText().toString().trim());
//        params.put("occupation", radioButton.getText().toString().trim());
//        params.put("idea", idea.getText().toString().trim());
//        String str1 = "abc";
//        str1 = uri_txt.getText().toString();
//        params.put("file", str1);
//private String upload_url ="http://192.168.210.2/gian_app/connection.php";
//"http://localhost/gian_app/connection.php";
//http://192.168.210.2/gian_app/connection.php";
// "localhost/gian_app/connection.php";
//int selected_id  = radioGroup.getCheckedRadioButtonId();
//choice = radioButton.getText().toString();
//radioGroup = (RadioGroup) findViewById(R.id.rad);
//select_files.setOnClickListener(this);
//select_files = (Button) findViewById(R.id.select_file);
//uri_txt = (TextView) findViewById(R.id.txt_uri);

    /*public void upload_dataa()
    {

        Toast.makeText(FormActivity.this,"Email " +choice,Toast.LENGTH_LONG).show();
        //Toast.makeText(FormActivity.this,"Submit pressed",Toast.LENGTH_LONG).show();
    }*/