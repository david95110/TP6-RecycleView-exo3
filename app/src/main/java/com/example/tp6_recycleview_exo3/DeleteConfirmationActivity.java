package com.example.tp6_recycleview_exo3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.tp6_recycleview_exo3.message.MyKeys;

public class DeleteConfirmationActivity extends AppCompatActivity {

    private TextView textViewName, textViewFirstname;
    private Button yes, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_confirmation);

        textViewName = findViewById(R.id.text_delete_confirmation_name);
        textViewFirstname = findViewById(R.id.text_delete_confirmation_firstname);
        yes = findViewById(R.id.button_delete_yes);
        no = findViewById(R.id.button_delete_no);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MyKeys.KEY_DELETE_NAME);
        String firstname = intent.getStringExtra(MyKeys.KEY_DELETE_FIRSTNAME);
        textViewName.setText(name);
        textViewFirstname.setText(firstname);

        yes.setOnClickListener(v -> doYes());
        no.setOnClickListener(v -> doNo());
    }

    private void doNo() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void doYes() {
        setResult(RESULT_OK);
        finish();
    }
}