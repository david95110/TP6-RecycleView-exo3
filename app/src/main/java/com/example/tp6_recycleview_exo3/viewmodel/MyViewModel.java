package com.example.tp6_recycleview_exo3.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tp6_recycleview_exo3.database.ContractProvider;
import com.example.tp6_recycleview_exo3.database.MyContentProvider;
import com.example.tp6_recycleview_exo3.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private final List<Person> list = new ArrayList<>();

    public MyViewModel(@NonNull Application application) {
        super(application);
        fetchDataFromLocalDB();
    }

    public boolean changePersonAtIndex(int indexInList, String name, String firstname) {
        ContentValues values = new ContentValues();
        values.put(ContractProvider.Person.COL_NAME, name);
        values.put(ContractProvider.Person.COL_FIRSTNAME, firstname);
        String whereClause = "ID = " + list.get(indexInList).getId();
        int i = getApplication().getContentResolver().update(ContractProvider.Person.CONTENTURI, values,
                whereClause, null);

        if (i == 1) {
            list.get(indexInList).setName(name);
            list.get(indexInList).setFirstname(firstname);
            return true;
        }

        return false;
    }

    public int updatePersonInDB(int idToChange, String name, String firstname) {
        ContentValues values = new ContentValues();
        values.put(ContractProvider.Person.COL_NAME, name);
        values.put(ContractProvider.Person.COL_FIRSTNAME, firstname);
        String whereClause = "ID = " + idToChange;
        return getApplication().getContentResolver().update(ContractProvider.Person.CONTENTURI, values,
                whereClause, null);
    }


    @SuppressLint("Range")
    private void fetchDataFromLocalDB() {
        Log.i("AppInfo", "Fetching Data from DB");

        try (
                Cursor cursor = getApplication().getContentResolver().query(ContractProvider.Person.CONTENTURI,
                        null, null, null, null)
        ) {
            for (boolean c = cursor.moveToFirst(); c; c = cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ContractProvider.Person.COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(ContractProvider.Person.COL_NAME));
                String firstname = cursor.getString(cursor.getColumnIndex(ContractProvider.Person.COL_FIRSTNAME));
                Person p = new Person(name, firstname);
                p.setId(id);
                list.add(p);
                Log.i("AppInfo", "Person fetched from db: " + p);
            }
        }

    }

    public List<Person> getList() {
        return Collections.unmodifiableList(list);
    }

    /**
     * Add a person to the list and returns its index.
     *
     * @param person
     * @return the position of the added person
     */
    public int addPerson(Person person) {
        list.add(person);
        return list.size() - 1;
    }

    public int addPerson(String name, String firstname) {
        ContentValues values = new ContentValues();
        values.put(ContractProvider.Person.COL_NAME, name);
        values.put(ContractProvider.Person.COL_FIRSTNAME, firstname);
        Uri uri = getApplication().getContentResolver().insert(ContractProvider.Person.CONTENTURI, values);

        if (uri != null) {
            Log.i("AppInfo", "new uri inserted: " + uri);
            Person p = new Person(name, firstname);
            p.setId(MyContentProvider.extractIdFromUri(uri));
            list.add(p);

            return list.size() - 1;
        } else {
            Log.i("AppInfo", "insertion failed");
            return -1;
        }
    }

    public boolean deletePersonAt(int indexToDelete) {
        String whereClause = "ID = " + list.get(indexToDelete).getId();
        int i = getApplication().getContentResolver().delete(ContractProvider.Person.CONTENTURI, whereClause, null);

        if (i == 1) {
            list.remove(indexToDelete);
            return true;
        }

        return false;
    }

}
