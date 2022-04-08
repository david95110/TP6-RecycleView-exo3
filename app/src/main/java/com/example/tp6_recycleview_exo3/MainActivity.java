package com.example.tp6_recycleview_exo3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp6_recycleview_exo3.message.MyKeys;
import com.example.tp6_recycleview_exo3.viewmodel.MyViewModel;

public class MainActivity extends AppCompatActivity {

    private Button addButton;
    private ActivityResultLauncher<Intent> resultLauncher;
    private MyAdapter myAdapter;

    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        addButton = findViewById(R.id.button_add_from_main);
        addButton.setOnClickListener(v -> doAdd());

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> onResult(result));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        myAdapter = new MyAdapter(viewModel.getList(), resultLauncher);
        recyclerView.setAdapter(myAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void doAdd() {
        Intent intent = new Intent(this, AddActivity.class);
        resultLauncher.launch(intent);
    }

    private void onResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent intent = result.getData();
            if (intent.getStringExtra(MyKeys.KEY_ADD_OK) != null) {
                Toast.makeText(MainActivity.this, "Person added", Toast.LENGTH_SHORT).show();
                int index = intent.getIntExtra(MyKeys.KEY_INDEX,-1);
                if(index == -1) return;
                myAdapter.notifyItemChanged(index);
            }
            else if(intent.getStringExtra(MyKeys.KEY_ADD_FAILED) != null) {
                Toast.makeText(activity, intent.getStringExtra(MyKeys.KEY_ADD_FAILED), Toast.LENGTH_SHORT).show();
            }
            else if(intent.getStringExtra(MyKeys.KEY_EDIT_OK) != null) {
                Toast.makeText(MainActivity.this, "Person edited", Toast.LENGTH_SHORT).show();
                int index = intent.getIntExtra(MyKeys.KEY_INDEX, -1);
                if(index == -1) return;
                myAdapter.notifyItemChanged(index);
            }
            else if(intent.getStringExtra(MyKeys.KEY_EDIT_FAILED) != null) {
                Toast.makeText(MainActivity.this, intent.getStringExtra(MyKeys.KEY_EDIT_FAILED), Toast.LENGTH_SHORT).show();
            }
            else if(intent.getStringExtra(MyKeys.KEY_DELETE_FAILED) != null) {
                Toast.makeText(this, intent.getStringExtra(MyKeys.KEY_DELETE_FAILED), Toast.LENGTH_SHORT).show();
            }
            else if(intent.getStringExtra(MyKeys.KEY_DELETE_OK) != null) {
                Toast.makeText(this, "Person deleted", Toast.LENGTH_SHORT).show();
                myAdapter.notifyDataSetChanged();
            }
        }
    }
}