package ttcnpm.g24.tuyendung;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.*;


public class LoginActivity extends AppCompatActivity {
    private static final String MA ="MA" ;
    private EditText usernameText;
    private EditText passwordText;
    private boolean error=false;
    public static String User="";
    public static String Pass="";
    private String inform="";
    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
        textViewCreateAccount.setText(fromHtml("<font color='#ffffff'>I don't have account yet. </font><font color='#00b8d4'>create one</font>"));
        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        usernameText=(EditText) findViewById(R.id.editTextEmail);
        passwordText=(EditText) findViewById(R.id.editTextPassword);
        //final String Username=usernameText.toString();
        //final String Password=passwordText.toString();
        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Login(usernameText.getText().toString(),passwordText.getText().toString());

            }
        });
        //new ReadJSONObject().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo1.json");
        //  new ReadJSONArray().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo2.json");
        // new ReadJSONArray().execute("https://khoapham.vn/KhoaPhamTraining/json/tien/demo3.json");
        //String url ="https://khoapham.vn/KhoaPhamTraining/json/tien/demo4.json";

        /*StringRequest stringRequest =new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginActivity.this,response,Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Error\n"+error.toString());
                    }
                }
        );
        requestQueue.add(stringRequest);*/

        /*JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String monhoc=response.getString("monhoc");
                            Toast.makeText(LoginActivity.this,monhoc,Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                Log.d("AAA","Error\n"+error.toString());
            }
        });
        requestQueue.add(jsonObjectRequest);*/

        /*JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String khoahoc = object.getString("khoahoc");
                                String hocphi = object.getString("hocphi");
                                Toast.makeText(LoginActivity.this, khoahoc + "-" + hocphi, Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                Log.d("AAA","Error\n"+error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }*/



    }

    private void Login(String Username,String Password) {

        doPOST(Username,Password);
        //doGET(Username,Password);
    }


    public void doPOST(final String Username, final String Password) {
        // final String username = null;
        // final String password = null;
        String url="http://jws-app-mobile.a3c1.starter-us-west-1.openshiftapps.com/LoginServer";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // final String finalUsername = username;
        //final String finalPassword = password;
        final JSONObject json = new JSONObject();
        try {
            json.put("username",Username);
            json.put("password",Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String stt=response.getString("STT");
                    String type=response.getString("type");
                    AccountInfo.accountInfo.username = Username;

                        if(stt.equals("SUCCESS")&& type.equals("doanhnghiep")) {
                            String jsonString = response.getString("data");

                            JSONObject json = new JSONObject(jsonString);
                            AccountInfo.accountInfo.hexStringAvatar = json.getString("avatar");
                            AccountInfo.accountInfo.name = json.getString("fullname");
                            AccountInfo.accountInfo.type = "C";
                            Intent intent = new Intent(LoginActivity.this, CompanyActivity.class);
                            intent.putExtra(User,Username);
                            intent.putExtra(Pass,Password);
                            startActivity(intent);
                    } else if(stt.equals("SUCCESS")&& type.equals("student")){
                            String jsonString = response.getString("data");
                            JSONObject json = new JSONObject(jsonString);
                            Log.d("mydata",jsonString);
                            AccountInfo.accountInfo.hexStringAvatar = json.getString("avatar");
                            AccountInfo.accountInfo.name = json.getString("fullname");
                            AccountInfo.accountInfo.type = "S";

                            Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                            intent.putExtra(User,Username);
                            intent.putExtra(Pass,Password);
                            startActivity(intent);
                    } else if(stt.equals("FAIL")){Toast.makeText(LoginActivity.this,"Tên tài khoản không tồn tại hoặc sai mật khẩu",Toast.LENGTH_LONG).show();}
                    else Toast.makeText(LoginActivity.this,"Check again!"+stt.toString()+type.toString(),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Error.", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }
    /*
        private class ReadJSONObject extends AsyncTask<String,Void,String>{
            @Override
            protected String doInBackground(String... strings){
                StringBuilder content=new StringBuilder();
                try {
                    URL url =new URL(strings[0]);
                    InputStreamReader inputStreamReader=new InputStreamReader((url.openConnection().getInputStream()));
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line="";
                    while ((line=bufferedReader.readLine())!=null){
                        content.append(line);
                    }
                    bufferedReader.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return content.toString();
            }

            protected void onPostExcute(String s){
                super.onPostExecute(s);
                try {
                    JSONObject object = new JSONObject(s);
                    JSONObject language=object.getJSONObject("language");
                    JSONObject vn=language.getJSONObject("vn");
                    String ten=vn.getString("name");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(LoginActivity.this,s,Toast.LENGTH_LONG).show();
            }
        }
        private  class ReadJSONArray extends AsyncTask<String, String, String> {
           protected String doInBackground(String... strings){
               StringBuilder content=new StringBuilder();
               try {
                   URL url = new URL(strings[0]);
                   InputStreamReader inputStreamReader=new InputStreamReader(url.openConnection().getInputStream());
                    BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                    String line="";
                    while ((line=bufferedReader.readLine())!=null){
                        content.append(line);
                    }
                    bufferedReader.close();
               } catch (MalformedURLException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }
               return content.toString();
           }

           protected void onPostExcute (String s){
                super.onPostExecute(s);
               try {
                   JSONObject object=new JSONObject(s);
                   JSONArray array=object.getJSONArray("danhsach");
                   for (int i=0;i<array.length();i++){
                       JSONObject object1=array.getJSONObject(i);
                       String name=object1.getString("khoahoc");
                       Toast.makeText(LoginActivity.this,name,Toast.LENGTH_SHORT).show();
                   }
                   Toast.makeText(LoginActivity.this,""+array.length(),Toast.LENGTH_SHORT).show();
               } catch (JSONException e) {
                   e.printStackTrace();
               }
               Toast.makeText(LoginActivity.this,s,Toast.LENGTH_SHORT).show();
               //pDialog.dismiss();
           }
        }*/
    public void doGET(String Username,String Password){
        String url="";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String stt=response.getString("stt");
                            String type=response.getString("type");
                            String error=response.getString("error");
                            Toast.makeText(LoginActivity.this,stt+"-"+type+"-"+"-"+error,Toast.LENGTH_SHORT).show();
                            if(stt.equals("SUCCESS")&& type.equals("company")) {
                                AccountInfo.accountInfo.type = "C";
                                Intent intent = new Intent(LoginActivity.this, CompanyActivity.class);
                                startActivity(intent);
                            } else if(stt.equals("SUCCESS")&& type.equals("student")){
                                AccountInfo.accountInfo.type = "S";
                                Intent intent = new Intent(LoginActivity.this, StudentActivity.class);
                                startActivity(intent);
                            } else if(stt.equals("FAIL")){Toast.makeText(LoginActivity.this,error,Toast.LENGTH_LONG).show();}
                            else Toast.makeText(LoginActivity.this,"Check again!",Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_SHORT).show();
                Log.d("Err","Error\n"+error.toString());
            }
        }

        );
        requestQueue.add(jsonObjectRequest);
    }
    public void goBack(View v){
        //updateMortgageObject();
        this.finish();
    }
    protected void onStart(){
        super.onStart();
        Log.w(MA,"Inside LoginActivity: onReStart\n");
        //update();
    }
    protected void onReStart(){
        super.onRestart();
        Log.w(MA,"LoginActivity: onReStart\n");
        //Toast.makeText(RegisterActivity.this,info.getUsername(),Toast.LENGTH_LONG).show();
        // updateView();
    }
    protected void onResume(){
        super.onResume();
        Log.w(MA,"LoginActivity: onResume\n");
        // Toast.makeText(RegisterActivity.this,info.getUsername(),Toast.LENGTH_LONG).show();
        // updateView();
    }
    protected void onPause(){
        super.onPause();
        Log.w(MA,"LoginActivity: onPause\n");
        // update();
    }
    protected void onStop(){
        super.onStop();
        Log.w(MA,"LoginActivity: onStop\n");
        //update();
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.w(MA,"LoginActivity: onDestroy\n");
        // update();
    }

}
