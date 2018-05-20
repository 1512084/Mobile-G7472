package ttcnpm.g24.tuyendung;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {
    private static final String MA = "MA";
    public static Info info;
    private boolean error=false;
    private String inform="";
    //Info info1=CpnRegisterActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        info=new Info();

        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent1);
            }
        });
        Button buttonRegister = (Button) findViewById(R.id.buttonNext);
        final RadioButton std=(RadioButton) findViewById(R.id.pickstd);
        final RadioButton cpn=(RadioButton) findViewById(R.id.pickcpn);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // EditText editText = findViewById(R.id.editTextEmail);
                update();
                check_error();
                if (inform!="")
                    Toast.makeText(RegisterActivity.this,inform,Toast.LENGTH_LONG).show();
                if(cpn.isChecked()&& error==false) {
                    Intent intent1 = new Intent(RegisterActivity.this, CpnRegisterActivity.class);
                    // intent1.putExtra("name",editText.getText().toString());
                    startActivity(intent1);
                }
                else if (std.isChecked()&& error==false){
                    Intent intent1 = new Intent(RegisterActivity.this, StdRegisterActivity.class);
                    startActivity(intent1);
                }
            }

        });


    }

    private void check_error() {
        error=false; inform="";
        if(info.getPassword().length()<8) {error=true; inform="Password must have length more 8 letters";}
        if(!info.getUsername().contains("@") || !info.getUsername().contains(".")) {error=true; inform="Check Username again!";}
        if(info.getUsername().isEmpty() || info.getPassword().isEmpty()) {error=true; inform="Check Username or Password again!";}
    }


    public void goBack(View v){
        //updateMortgageObject();
        this.finish();
    }
    protected void onStart(){
        super.onStart();
        Log.w(MA,"Inside RegisterActivity: onReStart\n");
        //update();
    }
    public void update() {
        EditText usernameET=(EditText) findViewById(R.id.editTextEmail);
        info.setUsername(usernameET.getText().toString());
        // usernameET.setText(info.getUsername());
        //Toast.makeText(RegisterActivity.this,info.getUsername(),Toast.LENGTH_LONG).show();
        EditText passwordET=(EditText) findViewById(R.id.editTextPassword);
        info.setPassword(passwordET.getText().toString());
        // passwordET.setText(info.getPassword());
        EditText nameET=(EditText) findViewById(R.id.editTextName);
        info.setname(nameET.getText().toString());
        //nameET.setText(info.getName());
        EditText addrET=(EditText) findViewById(R.id.editTextAddress);
        info.setAddress(addrET.getText().toString());
        //addrET.setText(info.getAddress());
        EditText phoneET=(EditText) findViewById(R.id.editTextPhone);
        info.setPhone(phoneET.getText().toString());
        //phoneET.setText(""+info.getPhone());
        RadioButton cpnRB=(RadioButton) findViewById(R.id.pickcpn);
        RadioButton stdRB=(RadioButton) findViewById(R.id.pickstd);
        if(stdRB.isChecked()) info.setType("Student"); else info.setType("Company");

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





    protected void onReStart(){
        super.onRestart();
        Log.w(MA,"RegisterActivity: onReStart\n");
        //Toast.makeText(RegisterActivity.this,info.getUsername(),Toast.LENGTH_LONG).show();
        // updateView();
    }
    protected void onResume(){
        super.onResume();
        Log.w(MA,"RegisterActivity: onResume\n");
        // Toast.makeText(RegisterActivity.this,info.getUsername(),Toast.LENGTH_LONG).show();
        // updateView();
    }
    protected void onPause(){
        super.onPause();
        Log.w(MA,"RegisterActivity: onPause\n");
        // update();
    }
    protected void onStop(){
        super.onStop();
        Log.w(MA,"RegisterActivity: onStop\n");
        //update();
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.w(MA,"RegisterActivity: onDestroy\n");
        // update();
    }
}
