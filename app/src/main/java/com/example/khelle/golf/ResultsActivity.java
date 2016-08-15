package com.example.khelle.golf;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private String[] nav_drawer_menu_items;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        nav_drawer_menu_items = getResources().getStringArray(R.array.nav_drawer_menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, nav_drawer_menu_items));

        Intent scoresIntent = getIntent();
        ArrayList<Integer> scores = scoresIntent.getIntegerArrayListExtra("gameScorecard");
        Log.i("", "gameScorecard for Results Activity is: " + scores.toString());
        Toast.makeText(this.getApplicationContext(), "Score is: " + scores.toString(), Toast.LENGTH_LONG);

        // Set drawer list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

    }
}
