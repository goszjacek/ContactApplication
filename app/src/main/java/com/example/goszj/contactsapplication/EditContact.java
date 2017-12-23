package com.example.goszj.contactsapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditContact extends AppCompatActivity {
    EditText name;
    EditText number;
    PojoContact pojoContact;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

         pojoContact = (PojoContact) getIntent().getSerializableExtra("PojoContact");
        position = getIntent().getIntExtra("position",-1);

        name = (EditText)findViewById(R.id.editName);
        number = (EditText)findViewById(R.id.editNumber);

        name.setText(pojoContact.getName());
        number.setText(pojoContact.getPhoneNumber());
    }

    public void save(View view){
        Intent intent = new Intent();
        PojoContact pj = new PojoContact(name.getText().toString(),number.getText().toString(),pojoContact.getImageDrawable());
        intent.putExtra("PojoContact",pj);
        intent.putExtra("position",position);
        setResult(RESULT_OK,intent);
        finish();
    }
}
