package com.example.samouille.vlille4;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        getJSON("https://opendata.lillemetropole.fr/api/records/1.0/search/?dataset=vlille-realtime&rows=222&facet=libelle&facet=nom&facet=commune");
    }


    //this method is actually fetching the json string
    private void getJSON(final String urlWebService) {
        /*
        * As fetching the json string is a network operation
        * And we cannot perform a network operation in main thread
        * so we need an AsyncTask
        * The constrains defined here are
        * Void -> We are not passing anything
        * Void -> Nothing at progress update as well
        * String -> After completion it should return a string and it will be the json string
        * */
        class GetJSON extends AsyncTask<Void, Void, String> {

            //this method will be called before execution
            //you can display a progress bar or something
            //so that user can understand that he should wait
            //as network operation may take some time
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            //this method will be called after execution
            //so here we are displaying a toast with the json string
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //in this method we are fetching the json string
            @Override
            protected String doInBackground(Void... voids) {



                try {
                    //creating a URL
                    URL url = new URL(urlWebService);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }

            }
        }

        //creating asynctask object and executing it
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONObject jsonObjet = new JSONObject(json);
        JSONArray records = jsonObjet.getJSONArray("records");

        String[] listNom = new String[records.length()];
        String[] listAdresses = new String[records.length()];

        int[] listNbPlaces = new int[records.length()];
        int[] listNbVelos = new int[records.length()];
        String[] listCommunes = new String[records.length()];
        double[] listLatitude = new double[records.length()];
        double[] listLongitude = new double[records.length()];

        for (int i = 0; i < records.length(); i++) {
            JSONObject obj = records.getJSONObject(i);
            JSONObject fields = obj.getJSONObject("fields");

            listAdresses[i] = fields.getString("adresse");
            listNom[i] = fields.getString("nom");
            listNbPlaces[i] = fields.getInt("nbPlacesDispo");
            listNbVelos[i] = fields.getInt("nbVelosDispo");
            listCommunes[i] = fields.getString("commune");
            JSONArray geo = fields.getJSONArray("geo");
            listLatitude[i]=geo.getDouble(0);
            listLongitude[i]=geo.getDouble(1);
            Log.d("myTag", listNom[i]);
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.view, listNom);
        listView.setAdapter(adapter);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,R.layout.view, R.id.TvAdresse, listAdresses);
        listView.setAdapter(arrayAdapter2);

        ImageView image = (ImageView) findViewById(R.id.img);
        image.setImageResource(R.drawable.checkvert);

            }
}
