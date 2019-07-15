package com.svatikk.linuxworld;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class introduction extends AppCompatActivity {





    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Movies json url
    private static final String url = "https://badshahsharmaforever.000webhostapp.com/introduction.json";
    private ProgressDialog pDialog;
    private ProgressBar progressBar;
    private List<Data> dataList = new ArrayList<Data>();
    private ListView listView;
    private ListAdapterForIntroduction adapter;


    private RequestQueue requestQueue;

    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        if (getSupportActionBar() != null){

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //below the code add require the back button
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }



        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeView);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                //Add your own code to refresh






                dataList.clear();//
                adapter.notifyDataSetChanged();
                getDataFromUrl();
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.VISIBLE);



            }

            private void getDataFromUrl() {

                //2nd Volley call active when refresh is needed



                // Creating volley request obj
                final JsonArrayRequest movieReq = new JsonArrayRequest(url,
                        new com.android.volley.Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.d(TAG, response.toString());
                                progressBar.setVisibility(View.GONE);

                                // Parsing json
                                for (int i = 0; i < response.length(); i++) {
                                    try {

                                        JSONObject obj = response.getJSONObject(i);
                                        Data data = new Data();
                                        data.setTitle(obj.getString("title"));
                                        data.setThumbnailUrl(obj.getString("image"));









                                        // adding data to data array
                                        dataList.add(data);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                // notifying list adapter about data changes
                                // so that it renders the list view with updated data
                                adapter.notifyDataSetChanged();
                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());

                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(), "Please swipe down to refresh", Toast.LENGTH_SHORT).show();


                        Cache cache = new DiskBasedCache(getCacheDir(),1024*1024*10);
                        Network network =new BasicNetwork(new HurlStack());



                        requestQueue = new RequestQueue(cache,network);
                        requestQueue.start();











                    }
                });

                // Adding request to request queue
                MySingletoneForAll.getInstance().addToRequestQueue(movieReq);


















            }

        });







        Animation animation = AnimationUtils.loadAnimation(getApplication(), R.anim.move_up);




        listView = (ListView) findViewById(R.id.list);
        adapter = new ListAdapterForIntroduction(this, dataList);
        listView.setAdapter(adapter);
        listView.setAnimation(animation);



        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);






        // Creating volley request obj
        JsonArrayRequest movieReq = new JsonArrayRequest(url,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        progressBar.setVisibility(View.GONE);

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Data data = new Data();
                                data.setTitle(obj.getString("title"));
                                data.setThumbnailUrl(obj.getString("image"));









                                // adding data to data array
                                dataList.add(data);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                progressBar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), "please swipe down to refresh", Toast.LENGTH_SHORT).show();


                Cache cache = new DiskBasedCache(getCacheDir(),1024*1024*10);
                Network network =new BasicNetwork(new HurlStack());



                requestQueue = new RequestQueue(cache,network);
                requestQueue.start();


            }
        });

        // Adding request to request queue
        MySingletoneForAll.getInstance().addToRequestQueue(movieReq);

























    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {


            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
