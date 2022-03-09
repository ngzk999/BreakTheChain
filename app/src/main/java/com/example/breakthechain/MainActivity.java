package com.example.breakthechain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Window;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        chipNavigationBar = findViewById(R.id.nav_menu);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new StatisticsFragment()).commit();
        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                Fragment fragment = null;
                switch (i){
                    case R.id.nav_statistics:
                        fragment = new StatisticsFragment();
                        break;
                    case R.id.nav_record:
                        fragment = new RecordFragment();
                        break;
                    case R.id.nav_info:
                        fragment = new InfoFragment();
                        break;
                    case R.id.nav_profile:
                        fragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
            }
        });
    }
}