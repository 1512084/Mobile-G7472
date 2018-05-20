package ttcnpm.g24.tuyendung;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public  class StdRegisterActivity extends AppCompatActivity {
    private static final String MA = "MA";
    Info info=RegisterActivity.info;
    private Calendar myCalendar = Calendar.getInstance();
    private EditText edtDate;
    private DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stdregister);
        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(StdRegisterActivity.this, RegisterActivity.class);
                startActivity(intent1);
            }
        });
        edtDate = findViewById(R.id.editTextBirthday);
        setDateTimeDialog();
        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
                doPOST();
                //Toast.makeText(StdRegisterActivity.this,info.getType()+"-"+info.getUsername()+"-"+info.getSex()+"-"+"Successful Register",Toast.LENGTH_LONG).show();
//


                //finish();

            }
        });
    }

    private void setDateTimeDialog(){



        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(StdRegisterActivity.this, AlertDialog.THEME_HOLO_DARK, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtDate.setText(sdf.format(myCalendar.getTime()));
    }
    public void doPOST() {
        // final String username = null;
        // final String password = null;
        String url="http://jws-app-mobile.a3c1.starter-us-west-1.openshiftapps.com/studentregister";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("username", info.getUsername().toString());
            jsonObject.put("password", info.getPassword().toString());
            jsonObject.put("name", info.getName().toString());
            jsonObject.put("address", info.getAddress().toString());
            jsonObject.put("phone", info.getPhone().toString());
            jsonObject.put("type", info.getType().toString());
            jsonObject.put("birthday", info.getBirthday().toString());
            jsonObject.put("sex", info.getSex().toString());
            jsonObject.put("university", info.getUniversity().toString());
            jsonObject.put("major", info.getMajor().toString());
            jsonObject.put("expected", info.getExpt().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // final String finalUsername = username;
        //final String finalPassword = password;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(StdRegisterActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    String status = response.getString("STATUS");
                    switch (status){
                        case "SUCCESS":
                            Toast.makeText(StdRegisterActivity.this,"Successful Register",Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(StdRegisterActivity.this, LoginActivity.class);
                            startActivity(intent1);
                            break;
                        case "FAIL":
                            Toast.makeText(StdRegisterActivity.this,"Please, create again!",Toast.LENGTH_LONG).show();
                            Intent intent2 = new Intent(StdRegisterActivity.this, RegisterActivity.class);
                            startActivity(intent2);
                            break;
                        case "EXIST":
                            Toast.makeText(StdRegisterActivity.this,"Username has existed! Please, create another one!",Toast.LENGTH_LONG).show();
                            Intent intent3 = new Intent(StdRegisterActivity.this, RegisterActivity.class);
                            startActivity(intent3);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StdRegisterActivity.this, "Error.", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", info.getUsername().toString());
                params.put("password", info.getPassword().toString());
                params.put("name", info.getName().toString());
                params.put("address", info.getAddress().toString());
                params.put("phone", info.getPhone().toString());
                params.put("type", info.getType().toString());
                params.put("birthday", info.getBirthday().toString());
                params.put("sex", info.getSex().toString());
                params.put("university", info.getUniversity().toString());
                params.put("major,", info.getMajor().toString());
                params.put("expected", info.getExpt().toString());

                return params;
            }
        };
        //requestQueue.add(request);
        MySingleton.getmInstance(this).addToRequestque(request);
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
    public void goBack(View v){
        //updateMortgageObject();
        //update();
        this.finish();
    }
    protected void onStart(){
        super.onStart();
        Log.w(MA,"Inside StdRegisterActivity: onReStart\n");
        //update();
    }

    public void update() {


        EditText birthdayET=(EditText) findViewById(R.id.editTextBirthday);
        info.setBirthday(birthdayET.getText().toString());
        Log.d("day",birthdayET.getText().toString());
        RadioButton male=(RadioButton) findViewById(R.id.pickMale);
        RadioButton female=(RadioButton) findViewById(R.id.pickfemale);
        if (male.isChecked()) info.setSex("Male"); else info.setSex("Female");
        EditText univET=(EditText) findViewById(R.id.editTextSchool);
        info.setUniversity(univET.getText().toString());
        //univET.setText(info.getUniversity());
        EditText majorET=(EditText) findViewById(R.id.editTextMajor);
        info.setMajor(majorET.getText().toString());
        //majorET.setText(info.getMajor());
        EditText exptET=(EditText) findViewById(R.id.editTextExpt);
        info.setExpt(exptET.getText().toString());
        //exptET.setText(info.getExpt());
    }
    public void register(View v){

        Intent myIntent = new Intent(this,LoginActivity.class);
        this.startActivity(myIntent);

    }
    protected void onReStart(){
        super.onRestart();
        Log.w(MA,"StdRegisterActivity: onReStart\n");
    }
    protected void onResume(){
        super.onResume();
        Log.w(MA,"StdRegisterActivity: onResume\n");
    }
    protected void onPause(){
        super.onPause();
        Log.w(MA,"StdRegisterActivity: onPause\n");
    }
    protected void onStop(){
        super.onStop();
        Log.w(MA,"StdRegisterActivity: onStop\n");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.w(MA,"StdRegisterActivity: onDestroy\n");
    }

}
