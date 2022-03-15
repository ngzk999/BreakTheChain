package com.example.breakthechain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.regex.Pattern;

public class RecordFragment extends Fragment {
    Spinner spinner;
    String selectedName;
    EditText contactEt;
    EditText tempEt;
    Button btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        spinner = view.findViewById(R.id.name_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.name_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        contactEt = view.findViewById(R.id.idETContact);
        tempEt = view.findViewById(R.id.idETTemp);
        btnSubmit = view.findViewById(R.id.idBtnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ValidateName() && ValidateTemperature() && ValidateContactNumber()){
                        SaveToDB();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;

    }

    private boolean ValidateTemperature() {
        boolean isValidated = true;
        float temperature;

        try {
            temperature = Float.parseFloat(String.valueOf(tempEt.getText()));
            if (temperature <= 35) {
                Toast.makeText(getContext(), "Submission failed. Temperature too low!", Toast.LENGTH_LONG).show();
                isValidated = false;
            }
            else if (temperature >= 44){
                Toast.makeText(getContext(), "Submission failed. Temperature too high!", Toast.LENGTH_LONG).show();
                isValidated = false;
            }
        }
        catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Submission failed. Invalid temperature format or null!", Toast.LENGTH_LONG).show();
            isValidated = false;
        }
        return isValidated;
    }

    private boolean ValidateContactNumber() {
        boolean isValidated = true;
        String contactNumber = String.valueOf(contactEt.getText());

        if (contactNumber.length() > 14){
            isValidated = false;
            Toast.makeText(getContext(), "Submission failed. Contact number is too long!", Toast.LENGTH_LONG).show();
        }

        return isValidated;
    }

    private boolean ValidateName(){
        boolean isValidated = true;

        selectedName = spinner.getSelectedItem().toString();
        if (selectedName.equals("No Name Selected"))
        {
            isValidated = false;
            Toast.makeText(getContext(), "Submission failed. Please select a name!", Toast.LENGTH_LONG).show();
        }

        return isValidated;
    }

    private void SaveToDB() throws JSONException {
        String url = "http://10.0.2.2:5225/api/employee";

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("employeeName", selectedName.toLowerCase());
        jsonBody.put("temperature", tempEt.getText());
        jsonBody.put("contactNumber", contactEt.getText());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("status").trim().equals("Ok"))
                        Toast.makeText(getContext(), "Submitted successfully", Toast.LENGTH_LONG).show();
                    else if (response.getString("status").trim().equals("Failed"))
                        Toast.makeText(getContext(), "Failed to submit", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }


}