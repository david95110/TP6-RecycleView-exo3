package com.example.tp6_recycleview_exo3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.tp6_recycleview_exo3.message.MyKeys;
import com.example.tp6_recycleview_exo3.viewmodel.MyViewModel;

public class DeleteActivity extends AppCompatActivity {

    private EditText editTextName, editTextFirstname;
    private Button button;
    private int indexToDelete;
    private ActivityResultLauncher<Intent> resultLauncher;
    private MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> onResult(result));

        setContentView(R.layout.activity_delete);

        editTextName = findViewById(R.id.edittext_delete_name);
        editTextFirstname = findViewById(R.id.edittext_delete_firstname);
        button = findViewById(R.id.button_delete);
        button.setOnClickListener(v -> doDelete(v.getContext()));

        Intent intent = getIntent();
        String name = intent.getStringExtra(MyKeys.KEY_DELETE_NAME);
        String firstname = intent.getStringExtra(MyKeys.KEY_DELETE_FIRSTNAME);
        indexToDelete = intent.getIntExtra(MyKeys.KEY_INDEX, -1);

        editTextName.setText(name);
        editTextFirstname.setText(firstname);
        viewModel = new ViewModelProvider((ViewModelStoreOwner) MainActivity.activity).get(MyViewModel.class);
    }

    private void doDelete(Context context) {

        if(indexToDelete == -1) {
            Intent intent = new Intent();
            intent.putExtra(MyKeys.KEY_DELETE_FAILED, "error of index");
            setResult(RESULT_OK, intent);
            finish();
            return;
        }

        Intent intent = new Intent(context,DeleteConfirmationActivity.class);
        intent.putExtra(MyKeys.KEY_DELETE_NAME,editTextName.getText().toString());
        intent.putExtra(MyKeys.KEY_DELETE_FIRSTNAME, editTextFirstname.getText().toString());
        resultLauncher.launch(intent);
    }

    private void onResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            boolean deleted = viewModel.deletePersonAt(indexToDelete);
            Intent intent = new Intent();

            if( deleted == true ) {
                intent.putExtra(MyKeys.KEY_DELETE_OK, "");
            }
            else {
                intent.putExtra(MyKeys.KEY_DELETE_FAILED, "failed to delete in database");
            }

            setResult(RESULT_OK, intent);
            finish();
        }
    }
}