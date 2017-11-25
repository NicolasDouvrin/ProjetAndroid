package com.example.samouille.vlille4;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity {

    TextView vlille_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView;

    }

    public void onClick(View view) {
        VlilleRepo repo = new VlilleRepo(this);
        if(ConnexionInternet.isConnectedInternet(MainActivity.this))
        {
            getJSON("https://opendata.lillemetropole.fr/api/records/1.0/search/?dataset=vlille-realtime&rows=100&facet=libelle&facet=nom&facet=commune");
        }
        ArrayList<HashMap<String, String>> vlilleList =  repo.getVlilleList();

    }

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
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    InsertIntoDb(s);
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

    private void InsertIntoDb(String json) throws JSONException {
        VlilleRepo repo = new VlilleRepo(this);
        VLille v = new VLille();
        JSONObject jsonObjet = new JSONObject(json);
        JSONArray records = jsonObjet.getJSONArray("records");
        for (int i = 0; i < records.length(); i++) {
            JSONObject obj = records.getJSONObject(i);
            JSONObject fields = obj.getJSONObject("fields");
            v.id = 0;
            v.nom = fields.getString("nom");
            v.commune = fields.getString("adresse");
            v.adresse = fields.getString("commune");
            v.nbVelosDispo = fields.getInt("nbPlacesDispo");
            v.nbPlacesDispo = fields.getInt("nbVelosDispo");
            JSONArray geo = fields.getJSONArray("geo");
            v.latitude =geo.getDouble(0);
            v.longitude=geo.getDouble(1);
            repo.insert(v);
        }
    }
}

        /*** FAIRE UN GETVIEW pour adapter les listes dans la listview et la view***/


        /***  POUR AFFICHER LES IMAGES EN FONCTION DES STATIONS
         *
         * if(listNbPlaces==0){
         * if(listNbVelos==0){
         * Image = (ImageView) findViewById(R.id.checkvert);
         * } else {
         * Image = (ImageView) findViewById(R.id.checkjaune);
         * } } else {
         * if(listNbVelos==0){
         * Image = (ImageView) findViewById(R.id.checkjaune);
         * } else{
         * Image = (ImageView) findViewById(R.id.checkvert);
         * }}
         *
         *
         ***/

