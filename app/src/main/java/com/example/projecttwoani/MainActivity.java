package com.example.projecttwoani;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String URL = "https://pixabay.com/api/?key=21762589-eccf546162757c2653ac25ec6&q=puppies&image_type=photo&pretty=true";
    private GridView gridv;
    private GridImage_Adapter gridImage_adapter;
    private ArrayList<GridImages_Class> arayGridImages;
    private TextView txtConnection;
    boolean connected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridv = (GridView)findViewById(R.id.image_gridview);
        txtConnection = (TextView)findViewById(R.id.txt_connection);
        arayGridImages = new ArrayList<>();
        gridImage_adapter = new GridImage_Adapter(MainActivity.this, R.layout.grid_items, arayGridImages);
        gridv.setAdapter(gridImage_adapter);

        //for checking connectivity
        checkInternetConnection();

        //on gridview image clicked
        gridv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("grid item click", String.valueOf(arayGridImages.get(position)));

               /* String img = arayGridImages.get(position).getLargeImageURL();
                Intent i = new Intent(MainActivity.this, EnlargeImage.class);
                i.putExtra("clicked", img);
                startActivity(i);
                */
//              OR
                startActivity(new Intent(MainActivity.this, EnlargeImage.class).putExtra("clicked", arayGridImages.get(position).getLargeImageURL()));
            }
        });

//        new getGrid().execute(URL);

    }

    private void checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            connected = true;
        }
        else
        {
            connected = false;
        }
        Log.i("internet", String.valueOf(connected));
        if(connected==true) {
            txtConnection.setText("GridView Images");
            //for AsyncTask Method
            new getGrid().execute(URL);
        }
        else{
            txtConnection.setText("Please connect to Internet and Come back again");
        }
    }

    public class getGrid extends AsyncTask<String, Void, Integer> {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                int statusCode = response.code();
                if (statusCode == 200) {
//                    Log.i("result-DIB", String.valueOf(statusCode));
                    String res = response.body().string();
                    parseResult(res);
                    result = 1; // Successful
 //                   Log.i("result200", result.toString());
                } else {
                    result = 0; //"Failed
 //                   Log.i("result200", result.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
//                Log.i("catch", e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                Log.i("print grid", arayGridImages.toString());
                gridImage_adapter.setArrayGrid(arayGridImages);
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }

        private void parseResult(String resp) {
            try {
                JSONObject json = new JSONObject(resp);
                JSONArray jArray = new JSONArray(json.getString("hits"));
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jObject = jArray.getJSONObject(i);
                    GridImages_Class img_class = new GridImages_Class();
                    img_class.setId(jObject.getString("id"));
                    img_class.setPreviewURL(jObject.getString("previewURL"));
                    img_class.setLargeImageURL(jObject.getString("largeImageURL"));
                    arayGridImages.add(img_class);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}