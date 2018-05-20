package ttcnpm.g24.tuyendung;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import ttcnpm.g24.tuyendung.FollowedCompanies.FollowedCompaniesFragment;
import ttcnpm.g24.tuyendung.Newsfeed.CompanyNewsFeedFragment;
import ttcnpm.g24.tuyendung.Newsfeed.StudentNewsfeedFragment;
import ttcnpm.g24.tuyendung.Searching.SearchingFragment;

public class StudentActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView tvCompanyEmail = header.findViewById(R.id.tvStudentEmail);
        TextView tvCompanyName = header.findViewById(R.id.tvStudentName);
        tvCompanyEmail.setText(AccountInfo.accountInfo.username);
        tvCompanyName.setText(AccountInfo.accountInfo.name);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        if(AccountInfo.accountInfo.hexStringAvatar.equals("")){

        }else{
            byte[] b = new byte[AccountInfo.accountInfo.hexStringAvatar.length() / 2];
            for (int j = 0; j < b.length; j++) {
                int index = j * 2;
                int v = Integer.parseInt(AccountInfo.accountInfo.hexStringAvatar.substring(index, index + 2), 16);
                b[j] = (byte) v;
            }

            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            ImageView imageView = header.findViewById(R.id.avatarStudent);
            imageView.setImageBitmap(bitmap);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        fragment=new StudentNewsfeedFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.contain_student,fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_student) {
            fragment=new StudentNewsfeedFragment();
        } else if (id == R.id.nav_setting) {
            //fragment =new PostFragment();
            fragment=new FollowedCompaniesFragment();
        } else if (id == R.id.nav_searching) {
            fragment = new SearchingFragment();
        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(StudentActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_top) {
        } else if (id == R.id.nav_about) {

        }
        if (fragment !=null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contain_student,fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

