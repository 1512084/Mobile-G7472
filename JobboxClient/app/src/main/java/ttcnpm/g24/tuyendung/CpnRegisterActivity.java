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

public class CpnRegisterActivity extends AppCompatActivity{
    private static final String MA ="MA" ;
    Info info=RegisterActivity.info;
    private Calendar myCalendar = Calendar.getInstance();
    private EditText edtDate;
    private DatePickerDialog.OnDateSetListener date;
    //public static Info info1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cpnregister);
        Bundle bundle = getIntent().getExtras();
        //Toast.makeText(this, bundle.getString("name"), Toast.LENGTH_SHORT).show();
        //Log.d("username",bundle.getString("name"));

        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(CpnRegisterActivity.this, RegisterActivity.class);
                startActivity(intent1);
                //updateView();
            }
        });
        //Toast.makeText(CpnRegisterActivity.this,info.getUsername(),Toast.LENGTH_LONG).show();
       // update();
        //info1=new Info();
        Button buttonRegister = (Button) findViewById(R.id.buttonRegister);
        edtDate = findViewById(R.id.editTextDate);
        setDateTimeDialog();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
                //info1=info;
                doPOST();
//
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
                new DatePickerDialog(CpnRegisterActivity.this, AlertDialog.THEME_HOLO_DARK, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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
        String url="http://jws-app-mobile.a3c1.starter-us-west-1.openshiftapps.com/companyregister";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        // final String finalUsername = username;
        //final String finalPassword = password;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", info.getUsername().toString());
            jsonObject.put("password", info.getPassword().toString());
            jsonObject.put("name", info.getName().toString());
            jsonObject.put("address", info.getAddress().toString());
            jsonObject.put("phone", info.getPhone().toString());
            jsonObject.put("type", info.getType().toString());
            jsonObject.put("series", info.getSeries().toString());
            jsonObject.put("datefoundation", info.getDatefound().toString());
            jsonObject.put("field", info.getField().toString());
            jsonObject.put("description,", info.getDesc().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(CpnRegisterActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    String status = response.getString("STATUS");
                    switch (status){
                        case "SUCCESS":
                            Toast.makeText(CpnRegisterActivity.this,"Successful Register",Toast.LENGTH_LONG).show();
                            Intent intent1 = new Intent(CpnRegisterActivity.this, LoginActivity.class);
                            startActivity(intent1);
                            break;
                        case "FAIL":
                            Toast.makeText(CpnRegisterActivity.this,"Please, create again!",Toast.LENGTH_LONG).show();
                            Intent intent2 = new Intent(CpnRegisterActivity.this, RegisterActivity.class);
                            startActivity(intent2);
                            break;
                        case "EXIST":
                            Toast.makeText(CpnRegisterActivity.this,"Username has existed! Please, create another one!",Toast.LENGTH_LONG).show();
                            Intent intent3 = new Intent(CpnRegisterActivity.this, RegisterActivity.class);
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
                Toast.makeText(CpnRegisterActivity.this, "Error.", Toast.LENGTH_SHORT).show();
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
                params.put("series", info.getSeries().toString());
                params.put("datefoundation", info.getDatefound().toString());
                params.put("field", info.getField().toString());
                params.put("description,", info.getDesc().toString());

                return params;
            }
        };
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
       // updateView();
        this.finish();
    }
    protected void onStart(){
        super.onStart();
        Log.w(MA,"Inside CpnRegisterActivity: onReStart\n");
        //update();
    }

    public void update() {
       // Info info=RegisterActivity.info;

        EditText seriesET=(EditText) findViewById(R.id.editTextSeries);
        info.setSeries(seriesET.getText().toString());
        //seriesET.setText(info.getSeries());
        EditText datefoundET=(EditText) findViewById(R.id.editTextDate);
        info.setDatefound(datefoundET.getText().toString());
        // datefoundET.setText(""+info.getDatefound());
        EditText fieldET=(EditText) findViewById(R.id.editTextField);
        info.setField(fieldET.getText().toString());
        //fieldET.setText(info.getField());
        EditText descET=(EditText) findViewById(R.id.editTextDesc);
        info.setDesc(descET.getText().toString());

    }
    public void updateView() {
        EditText usernameET=(EditText) findViewById(R.id.editTextEmail);
        //info.setUsername(usernameET.getText().toString());

        usernameET.setText(info.getUsername());
        // Toast.makeText(RegisterActivity.this,info.getUsername(),Toast.LENGTH_LONG).show();
        EditText passwordET=(EditText) findViewById(R.id.editTextPassword);
        //info.setPassword(passwordET.getText().toString());
        passwordET.setText(info.getPassword());
        EditText nameET=(EditText) findViewById(R.id.editTextName);
        //info.setname(nameET.getText().toString());
        nameET.setText(info.getName());
        EditText addrET=(EditText) findViewById(R.id.editTextAddress);
        //info.setAddress(addrET.getText().toString());
        addrET.setText(info.getAddress());
        EditText phoneET=(EditText) findViewById(R.id.editTextPhone);
        //info.setPhone(phoneET.getText().toString());
        phoneET.setText(info.getPhone());
        RadioButton cpnRB=(RadioButton) findViewById(R.id.pickcpn);
        RadioButton stdRB=(RadioButton) findViewById(R.id.pickstd);
        if(stdRB.isChecked()) info.setType("Student"); else info.setType("Company");

    }
    public void register(View v){

        Intent myIntent = new Intent(this,LoginActivity.class);
        this.startActivity(myIntent);
    }
    protected void onReStart(){
        super.onRestart();
        Log.w(MA,"CpnRegisterActivity: onReStart\n");
    }
    protected void onResume(){
        super.onResume();
        Log.w(MA,"CpnRegisterActivity: onResume\n");
    }
    protected void onPause(){
        super.onPause();
        Log.w(MA,"CpnRegisterActivity: onPause\n");
    }
    protected void onStop(){
        super.onStop();
        Log.w(MA,"CpnRegisterActivity: onStop\n");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.w(MA,"CpnRegisterActivity: onDestroy\n");
    }
}
