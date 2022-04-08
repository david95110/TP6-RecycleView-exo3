package com.example.tp6_recycleview_exo3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.tp6_recycleview_exo3.message.MyKeys;
import com.example.tp6_recycleview_exo3.viewmodel.MyViewModel;

public class AddActivity extends AppCompatActivity {

    private EditText editTextName, editTextFirstname;
    private Button addButton;
    private MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addButton = findViewById(R.id.button_add);
        editTextName = findViewById(R.id.edittext_delete_name);
        editTextFirstname = findViewById(R.id.edittext_delete_firstname);
        addButton.setOnClickListener(v -> doAdd());
        viewModel = new ViewModelProvider((ViewModelStoreOwner) MainActivity.activity).get(MyViewModel.class);
        //viewModel = new ViewModelProvider((ViewModelStoreOwner) this.getApplicationContext()).get(MyViewModel.class);
    }

    private void doAdd() {
        Intent intent = new Intent();
        String name = editTextName.getText().toString();
        String firstname = editTextFirstname.getText().toString();

        if(name.length() >0 && firstname.length()>0) {
            int index = viewModel.addPerson(name,firstname);

            if(index != -1) {
                intent.putExtra(MyKeys.KEY_ADD_OK, "");
                intent.putExtra(MyKeys.KEY_INDEX, index);

            }
            else {
                intent.putExtra(MyKeys.KEY_ADD_FAILED, "failed to insert into database");
            }

            setResult(RESULT_OK, intent);
            finish();
        }
        else{
            Toast.makeText(this, "incorrect input", Toast.LENGTH_SHORT).show();
        }
    }
}