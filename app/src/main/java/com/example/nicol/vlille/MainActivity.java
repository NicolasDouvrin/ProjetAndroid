package com.example.nicol.vlille;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;

    ArrayList<HashMap<String, String>> vlilleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vlilleList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetVlille().execute();
    }

    private class GetVlille extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://opendata.lillemetropole.fr/api/records/1.0/search/?dataset=vlille-realtime";
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONObject records = new JSONObject("records");
                    JSONArray vlilles = new JSONArray(records.getString("fields"));

                    // looping through All Contacts
                    for (int i = 0; i < records.length(); i++) {
                        JSONObject c = vlilles.getJSONObject(i);
                        String nom = c.getString("nom");
                        String etat = c.getString("etat");
                        String commune = c.getString("commune");

                        // tmp hash map for single contact
                        HashMap<String, String> vlille = new HashMap<>();

                        // adding each child node to HashMap key => value
                        vlille.put("nom", nom);
                        vlille.put("commune", commune);
                        vlille.put("etat", etat);


                        // adding contact to contact list
                        vlilleList.add(vlille);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, vlilleList,
                    R.layout.list_item, new String[]{ "nom","commune","etat"},
                    new int[]{R.id.nom, R.id.commune,R.id.etat});
            lv.setAdapter(adapter);
        }
    }
}