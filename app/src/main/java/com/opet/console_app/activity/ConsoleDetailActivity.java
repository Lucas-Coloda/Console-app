package com.opet.console_app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.widget.Toast;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.opet.console_app.R;
import com.opet.console_app.model.Console;
import com.opet.console_app.util.APISingleton;

import org.json.JSONObject;
import org.json.JSONException;

public class ConsoleDetailActivity extends AppCompatActivity {
    private TextView textName, textYear, textPrice, textActive, textAmountGames;
    private Console console;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console_detail);

        id = getIntent().getLongExtra("ID", 0);

        textName = findViewById(R.id.textName);
        textYear = findViewById(R.id.textYear);
        textPrice = findViewById(R.id.textPrice);
        textActive = findViewById(R.id.textActive);
        textAmountGames = findViewById(R.id.textAmountGames);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadConsole();
    }

    private void loadConsole() {
        String url = "http://10.0.2.2:5000/api/console/" + id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                console = new Console();
                try {
                    System.out.println(response);
                    console.setId(response.getLong("id"));
                    console.setName(response.getString("name"));
                    console.setYear(response.getInt("year"));
                    console.setPrice(response.getDouble("price"));
                    console.setActive(response.getBoolean("active"));
                    console.setAmountGames(response.getInt("amount_games"));

                    textName.setText(console.getName());
                    textYear.setText(String.valueOf(console.getYear()));
                    textPrice.setText(String.valueOf(console.getPrice()));
                    textActive.setText(console.isActive()? "Ativo" : "não ativo");
                    textAmountGames.setText(String.valueOf(console.getAmountGames()));
                } catch (JSONException e) {
                    e.printStackTrace();
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

    public void updateConsole(View view) {
        Intent newConsole = new Intent(ConsoleDetailActivity.this, NewConsoleActivity.class);
        newConsole.putExtra("ID", console.getId());
        startActivity(newConsole);
    }

    public void deleteConsole(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Tem certeza que deseja excluir o console " + console.getId() + "?");
        builder.setTitle("Excluir");
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String url = "http://10.0.2.2:5000/api/console/" + id;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(ConsoleDetailActivity.this, "Console " + response.getLong("id") + " removido.", Toast.LENGTH_SHORT).show();
                            Intent main = new Intent(ConsoleDetailActivity.this, MainActivity.class);
                            startActivity(main);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                APISingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
            }
        });
        builder.setNegativeButton("NÃO", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
