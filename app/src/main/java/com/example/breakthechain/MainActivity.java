package com.example.breakthechain;

import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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
        callStateApi();

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
                        callStateApi();
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
                Log.i("Malaysia Data error", error.getMessage());
            }
        });

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        queue.add(stringRequest);
    }

    private void callStateApi(){
        // have to use 10.0.2.2
        String url = "http://10.0.2.2:5225/api/data/stateLatestData";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArr = new JSONArray(response);
                    ArrayList<TextView> textViews = new ArrayList<TextView>();
                    textViews.add(findViewById(R.id.idTVJohorNew));
                    textViews.add(findViewById(R.id.idTVJohorRecovered));
                    textViews.add(findViewById(R.id.idTVJohorDeath));

                    textViews.add(findViewById(R.id.idTVKedahNew));
                    textViews.add(findViewById(R.id.idTVKedahRecovered));
                    textViews.add(findViewById(R.id.idTVKedahDeath));

                    textViews.add(findViewById(R.id.idTVKelantanNew));
                    textViews.add(findViewById(R.id.idTVKelantanRecovered));
                    textViews.add(findViewById(R.id.idTVKelantanDeath));

                    textViews.add(findViewById(R.id.idTVMelakaNew));
                    textViews.add(findViewById(R.id.idTVMelakaRecovered));
                    textViews.add(findViewById(R.id.idTVMelakaDeath));

                    textViews.add(findViewById(R.id.idTVSembilanNew));
                    textViews.add(findViewById(R.id.idTVSembilanRecovered));
                    textViews.add(findViewById(R.id.idTVSembilanDeath));

                    textViews.add(findViewById(R.id.idTVPahangNew));
                    textViews.add(findViewById(R.id.idTVPahangRecovered));
                    textViews.add(findViewById(R.id.idTVPahangDeath));

                    textViews.add(findViewById(R.id.idTVPerakNew));
                    textViews.add(findViewById(R.id.idTVPerakRecovered));
                    textViews.add(findViewById(R.id.idTVPerakDeath));

                    textViews.add(findViewById(R.id.idTVPerlisNew));
                    textViews.add(findViewById(R.id.idTVPerlisRecovered));
                    textViews.add(findViewById(R.id.idTVPerlisDeath));

                    textViews.add(findViewById(R.id.idTVPenangNew));
                    textViews.add(findViewById(R.id.idTVPenangRecovered));
                    textViews.add(findViewById(R.id.idTVPenangDeath));

                    textViews.add(findViewById(R.id.idTVSabahNew));
                    textViews.add(findViewById(R.id.idTVSabahRecovered));
                    textViews.add(findViewById(R.id.idTVSabahDeath));

                    textViews.add(findViewById(R.id.idTVSarawakNew));
                    textViews.add(findViewById(R.id.idTVSarawakRecovered));
                    textViews.add(findViewById(R.id.idTVSarawakDeath));

                    textViews.add(findViewById(R.id.idTVSelangorNew));
                    textViews.add(findViewById(R.id.idTVSelangorRecovered));
                    textViews.add(findViewById(R.id.idTVSelangorDeath));

                    textViews.add(findViewById(R.id.idTVTerengganuNew));
                    textViews.add(findViewById(R.id.idTVTerengganuRecovered));
                    textViews.add(findViewById(R.id.idTVTerengganuDeath));

                    textViews.add(findViewById(R.id.idTVKLNew));
                    textViews.add(findViewById(R.id.idTVKLRecovered));
                    textViews.add(findViewById(R.id.idTVKLDeath));

                    textViews.add(findViewById(R.id.idTVLabuanNew));
                    textViews.add(findViewById(R.id.idTVLabuanRecovered));
                    textViews.add(findViewById(R.id.idTVLabuanDeath));

                    textViews.add(findViewById(R.id.idTVPutrajayaNew));
                    textViews.add(findViewById(R.id.idTVPutrajayaRecovered));
                    textViews.add(findViewById(R.id.idTVPutrajayaDeath));

                    int j = 0;
                    for (int i = 0; i < textViews.size();) {
                        textViews.get(i++).setText(jsonArr.getJSONObject(j).getString("newCases"));
                        textViews.get(i++).setText(jsonArr.getJSONObject(j).getString("recoveredCases"));
                        textViews.get(i++).setText(jsonArr.getJSONObject(j).getString("deathCases"));
                        j++;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("State Data error", error.getMessage());
            }
        });

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        queue.add(stringRequest);
    }
}