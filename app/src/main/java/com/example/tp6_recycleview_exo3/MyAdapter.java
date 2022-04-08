package com.example.tp6_recycleview_exo3;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tp6_recycleview_exo3.message.MyKeys;
import com.example.tp6_recycleview_exo3.model.Person;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewName, textViewFirstname;
        private ImageView editImage, deleteImage;

        public TextView getTextViewName() {
            return textViewName;
        }

        public ImageView getEditImage() {
            return editImage;
        }

        public ImageView getDeleteImage() {
            return deleteImage;
        }

        public TextView getTextViewFirstname() {
            return textViewFirstname;
        }

        public void setTextViewFirstname(TextView textViewFirstname) {
            this.textViewFirstname = textViewFirstname;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.list_textview_1);
            textViewFirstname = itemView.findViewById(R.id.list_textview_2);
            editImage = itemView.findViewById(R.id.imageview_edit);
            deleteImage = itemView.findViewById(R.id.imageview_delete);
        }
    }

    private List<Person> list;
    private ActivityResultLauncher<Intent> resultLauncher;

    public MyAdapter(List<Person> list, ActivityResultLauncher<Intent> resultintent) {
        this.list = list;
        resultLauncher = resultintent;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.getTextViewName().setText(list.get(position).getName());
        holder.getTextViewFirstname().setText(list.get(position).getFirstname());
        holder.getEditImage().setOnClickListener(v -> doEdit(v.getContext(), position));
        holder.getDeleteImage().setOnClickListener(v -> doDelete(v.getContext(), position));
    }

    private void doDelete(Context context, int position) {
        Log.i("AppInfo", "DELETE: " + position);
        Intent intent = new Intent(context, DeleteActivity.class);
        intent.putExtra(MyKeys.KEY_DELETE_NAME, list.get(position).getName());
        intent.putExtra(MyKeys.KEY_DELETE_FIRSTNAME, list.get(position).getFirstname());
        intent.putExtra(MyKeys.KEY_INDEX, position);
        resultLauncher.launch(intent);
    }

    private void doEdit(Context context, int position) {
        Log.i("AppInfo", "EDIT: " + position);
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra(MyKeys.KEY_EDIT_NAME, list.get(position).getName());
        intent.putExtra(MyKeys.KEY_EDIT_FIRSTNAME, list.get(position).getFirstname());
        intent.putExtra(MyKeys.KEY_INDEX, position);
        resultLauncher.launch(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
