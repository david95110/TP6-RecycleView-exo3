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

public class EditActivity extends AppCompatActivity {

    private int indexInList;
    private EditText editTextName, editTextFirstname;
    private Button button;
    private MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTextName = findViewById(R.id.edittext_delete_name);
        editTextFirstname = findViewById(R.id.edittext_delete_firstname);
        button = findViewById(R.id.button_delete);
        button.setOnClickListener(v -> doEdit());

        Intent intent = getIntent();
        String name = intent.getStringExtra(MyKeys.KEY_EDIT_NAME);
        String firstname = intent.getStringExtra(MyKeys.KEY_EDIT_FIRSTNAME);
        indexInList = intent.getIntExtra(MyKeys.KEY_INDEX, -1);

        editTextName.setText(name);
        editTextFirstname.setText(firstname);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) MainActivity.activity).get(MyViewModel.class);
    }

    private void doEdit() {
        Intent intent = new Intent();

        if (indexInList == -1) {
            intent.putExtra(MyKeys.KEY_EDIT_FAILED, "Error of index");
            setResult(RESULT_OK, intent);
            finish();
            return;
        }

        String name = editTextName.getText().toString();
        String firstname = editTextFirstname.getText().toString();

        if (name.length() > 0 && firstname.length() > 0) {
            //DaoPerson.changePersonAtIndex(indexInList,name,firstname);
            boolean changed = viewModel.changePersonAtIndex(indexInList, name, firstname);

            if( changed == true) {
                intent.putExtra(MyKeys.KEY_EDIT_OK, "");
            }
            else {
                intent.putExtra(MyKeys.KEY_EDIT_FAILED, "failed to change into database");
            }

            intent.putExtra(MyKeys.KEY_INDEX,indexInList);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "incorrect input", Toast.LENGTH_SHORT).show();
        }
    }
}