package com.opet.console_app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.opet.console_app.R;
import com.opet.console_app.model.Console;
import com.opet.console_app.util.APISingleton;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    private ListView lvConsoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvConsoles = findViewById(R.id.listConsoles);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadConsoles();
    }

    private void loadConsoles() {
        String url = "http://10.0.2.2:5000/api/consoles";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Console> consoles = new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject object = (JSONObject) response.get(i);
                        Console console = new Console();
                        console.setId(object.getLong("id"));
                        console.setName(object.getString("name"));

                        consoles.add(console);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    setConsoleAdapter(consoles);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        APISingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void setConsoleAdapter(List<Console> consoles) {

        ArrayAdapter<Console> adapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_list_item_1, consoles);
        lvConsoles.setAdapter(adapter);
        lvConsoles.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Console console = (Console) adapterView.getItemAtPosition(position);
        Intent detailConsole = new Intent(MainActivity.this, ConsoleDetailActivity.class);

        detailConsole.putExtra("ID", console.getId());
        startActivity(detailConsole);
    }

    public void newConsole(View view) {
        Intent newConsole = new Intent(MainActivity.this, NewConsoleActivity.class);
        startActivity(newConsole);
    }
}
