package com.example.myfirstapp.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.Button;
import android.view.View.OnClickListener;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends ActionBarActivity {


    //Create new task button
    Button create;
    Button delete;
    //sort buttons
    Button defaultSort;
    Button importance;
    Button date;

    TaskGrid taskGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridview = (GridView) findViewById(R.id.taskGrid);
        taskGrid = new TaskGrid(getApplicationContext());
        gridview.setAdapter(taskGrid);

        addListenerOnButton();
        /*
          Set click listener

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(HelloGridView.this, "" + position, Toast.LENGTH_SHORT).show();
                */
    }

    public void addListenerOnButton() {

        create = (Button) findViewById(R.id.createB);
        delete = (Button) findViewById(R.id.deleteB);
        defaultSort = (Button) findViewById(R.id.defaultB);
        importance = (Button) findViewById(R.id.importanceB);
        date = (Button) findViewById(R.id.dateB);

        create.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

               //needs to change view and make a new task

                taskGrid.createNewTask("Hello World!!!", ImportanceLevel.normal, new Date(Calendar.MILLISECOND));
            }

        });

         delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //delete task
                // (need to know which task to create)

            }

        });
        defaultSort.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //set to default ordering

            }

        });
        importance.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //set ordering by importance
            }

        });
        date.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                //set ordering by date

            }

        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
