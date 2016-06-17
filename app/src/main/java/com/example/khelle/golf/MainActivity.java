package com.example.khelle.golf;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private String[] nav_drawer_menu_items;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Button mainPlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav_drawer_menu_items = getResources().getStringArray(R.array.nav_drawer_menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mainPlayButton = (Button) findViewById(R.id.main_play_button);

        // Set adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, nav_drawer_menu_items));

        // Set drawer list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Set mainPlayButton's click listener
        mainPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterSetup(v);
            }
        });

    }

    // Called when user clicks mainPlayButton
    public void enterSetup(View view) {
        Intent intent = new Intent(this, SetupActivity.class);
        startActivity(intent);
    }
}
