package com.example.goszj.contactsapplication;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayContact extends AppCompatActivity {
    ImageView image;
    TextView name;
    TextView number;
    PojoContact pojoContact;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);


        pojoContact = (PojoContact) getIntent().getSerializableExtra("PojoContact");
        position = getIntent().getIntExtra("position",-1);

        image = (ImageView) findViewById(R.id.displayImage);
        name = (TextView)findViewById(R.id.displayName);
        number = (TextView)findViewById(R.id.displayNumber);

        image.setImageResource(R.drawable.sharapova);
        name.setText(pojoContact.getName());
        number.setText(pojoContact.getPhoneNumber());
    }
}
