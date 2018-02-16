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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rv;
    RecyclerView.Adapter rvAdapter;
    RecyclerView.LayoutManager rvLayout;
    ContactsItemListener rvListener;
    ArrayList<PojoContact> dataSet = new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataSet.add(new PojoContact("Anna Malinka","123456789",R.drawable.sharapova));
        dataSet.add(new PojoContact("Stefania Mazdek","123456789",R.drawable.sharapova));
        dataSet.add(new PojoContact("Eugenia Takze","123456789",R.drawable.sharapova));
        dataSet.add(new PojoContact("Róża Pach","123456789",R.drawable.sharapova));
        dataSet.add(new PojoContact("Anna Malinka","987654321",R.drawable.sharapova));
        dataSet.add(new PojoContact("Stefania Mazdek","987654321",R.drawable.sharapova));
        dataSet.add(new PojoContact("Eugenia Takze","987654321",R.drawable.sharapova));
        dataSet.add(new PojoContact("Róża Pach","987654321",R.drawable.sharapova));

        rv = findViewById(R.id.contactsView);

        rv.setHasFixedSize(true);

        rvLayout = new LinearLayoutManager(this);
        rv.setLayoutManager(rvLayout);

        Log.d("debug","layout added");

        rvAdapter = new ContactsAdapter(dataSet);
        rv.setAdapter(rvAdapter);
        Log.d("debug","adapter added");

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

        @Override
        protected Void doInBackground(Void... voids) {
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
            super.onPostExecute(aVoid);
        }
    }

}
