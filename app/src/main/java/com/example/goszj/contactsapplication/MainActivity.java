package com.example.goszj.contactsapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayout;
    ContactsItemListener rvListener;
    ArrayList<PojoContact> dataSet = new ArrayList<>();
    ProgressDialog progressDialog;
    private static final String url = "https://jaksiemaszcare-training-mobile.herokuapp.com/api";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.contactsView);

        rv.setHasFixedSize(true);
        new GetContacts().execute();
        rvLayout = new LinearLayoutManager(this);
        rv.setLayoutManager(rvLayout);

        rvListener = new ContactsItemListener(getApplicationContext(),rv,new ContactsItemListener.ContactTouchListener(){

            @Override
            public void onClickItem(View v, int position) {
                Log.d("click","item clicked");
                Intent intent = new Intent(getApplicationContext(),DisplayContact.class);
                //putExtra
                intent.putExtra("PojoContact",dataSet.get(position));
                intent.putExtra("position",position);
                startActivityForResult(intent,1);
            }

            @Override
            public void onLongTouchItem(View v, int position) {
                Log.d("click", "item long clicked");
                ((ContactsAdapter)rv.getAdapter()).removeItem(position);
            }
        });
        rv.addOnItemTouchListener(rvListener);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                PojoContact pj = (PojoContact)data.getSerializableExtra("PojoContact");
                int pos = data.getIntExtra("position",-1);
                ((ContactsAdapter)rv.getAdapter()).updateItem(pos,pj);
            }
        }
    }

    private class GetContacts extends AsyncTask<Void,Void,Void>{
        private final String TAG = "GetContacts";
        @Override
        protected Void doInBackground(Void... voids) {
            /**
             * uzupelnij dataSet danymi sparsowanymi z JSON
             */
            Log.d(TAG,"Handler");
            HttpHandler httpHandler = new HttpHandler();
            String jsonString=httpHandler.makeServiceCall(url);
            if(jsonString!=null){
                try{
                    Log.d(TAG,"overlooping array");
                    JSONArray data = new JSONArray(jsonString);
                    for(int i=0; i<data.length(); i++){
                        JSONObject jsonContact = data.getJSONObject(i);
                        String id = jsonContact.getString("id");
                        String firstName = jsonContact.getString("firstName");
                        String lastName = jsonContact.getString("lastName");
                        String email=jsonContact.getString("email");
                        String phone=jsonContact.getString("phone");
                        String avatar=jsonContact.getString("avatar");

                        PojoContact pojoContact = new PojoContact(firstName + " " + lastName, phone, R.drawable.sharapova);
                        dataSet.add(pojoContact);

                    }

                }catch(JSONException e){
                    Log.e("GetContacts", "JSONException");
                    e.printStackTrace();
                }
            }else{
                Log.e("GetContacts", "no JSON String");
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Wait for Parsing...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            /**
             * Po tym jak dataSet zostanie uzupelnione w doInBackground, przypisz adepter
             */
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            rvAdapter= new ContactsAdapter(dataSet);
            rv.setAdapter(rvAdapter);
        }
    }

}
