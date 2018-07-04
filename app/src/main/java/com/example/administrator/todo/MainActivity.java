package com.example.administrator.todo;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    private DbAdapter dba;
    ListView list_contacts;
    Intent intent;
    private SimpleCursorAdapter dataSimpleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dba = new DbAdapter(this);
        list_contacts = findViewById(R.id.todolist);
        ListView();
    }
    private void ListView(){
        Cursor cursor = dba.listContacts();
        String[] columns = new String[]{
                dba.KEY_TIME,
                dba.KEY_LIST,
        };
        int[] view = new int[]{
                R.id.timepick,
                R.id.list,
        };
        dataSimpleAdapter = new SimpleCursorAdapter(this, R.layout.item_view, cursor, columns, view, 0);
        list_contacts.setAdapter(dataSimpleAdapter);
        list_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor item_cursor = (Cursor) list_contacts.getItemAtPosition(position);
                String item_name = item_cursor.getString(item_cursor.getColumnIndexOrThrow("name"));
                intent = new Intent();
                intent.putExtra("item_name",item_name);
                intent.setClass(MainActivity.this, ShowActivity.class );
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                intent = new Intent();
                intent.putExtra("type","add");
                intent.setClass(MainActivity.this, EditActivity.class );
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}