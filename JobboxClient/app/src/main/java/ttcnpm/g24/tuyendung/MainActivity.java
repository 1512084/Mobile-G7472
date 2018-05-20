package ttcnpm.g24.tuyendung;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String MA = "MA";
    User database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        //TextView textViewCreateAccount = (TextView) findViewById(R.id.textLogin);
        //Button btn=(Button) findViewById(R.id.buttonLogin);

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });*/
       /* //tao
        database=new User(this,"ghichu.sqlite",null,1);
        //tao bang
        database.QuerryData("CREATE TABLE IF NOT EXISTS Congviec(Id INTEGER PRIMARY KEY AUTOINCREMENT, TenCV VARCHAR(200))");
        //insert data
       // database.QuerryData("INSERT INTO Congviec VALUES(null,'abc')");

        //select data
        Cursor dataCongviec=database.GetData("SELECT *FROM Congviec");
        while (dataCongviec.moveToNext()){
            String ten =dataCongviec.getString(1);
            Toast.makeText(this,ten,Toast.LENGTH_SHORT).show();
        }*/

    }
    protected void onReStart(){
        super.onRestart();
        Log.w(MA,"MainActivity: onReStart\n");
        finish();
        //updateView();
    }
    protected void onResume(){
        super.onResume();
        Log.w(MA,"MainActivity: onResume\n");
        finish();
        //updateView();
    }
    protected void onPause(){
        super.onPause();
        Log.w(MA,"MainActivity: onPause\n");
        // update();
    }
    protected void onStop(){
        super.onStop();
        Log.w(MA,"MainActivity: onStop\n");
        //update();
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.w(MA,"MainActivity: onDestroy\n");
        // update();
    }


}
