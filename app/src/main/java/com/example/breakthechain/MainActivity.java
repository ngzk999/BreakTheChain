package com.example.breakthechain;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        chipNavigationBar = findViewById(R.id.nav_menu);
        chipNavigationBar.setItemSelected(R.id.nav_statistics, true);
        callMyApi();
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
                        callMyApi();
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

    private void callMyApi(){
        // have to use 10.0.2.2
        String url = "http://10.0.2.2:5225/api/data/malaysiaLatestData";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    RelativeLayout loadingPanel = findViewById(R.id.loadingPanel);

                    TextView malaysiaTitle = findViewById(R.id.idTVMyTitle);
                    malaysiaTitle.setText("Malaysia Cases\n" + response.getString("date"));

                    TextView myNew = findViewById(R.id.idTVMyNew);
                    myNew.setText(response.getString("newCases"));

                    TextView myRecovered = findViewById(R.id.idTVMyRecovered);
                    myRecovered.setText(response.getString("recoveredCases"));

                    TextView myDeath = findViewById(R.id.idTVMyDeath);
                    myDeath.setText(response.getString("deathCases"));

                    loadingPanel.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }
}