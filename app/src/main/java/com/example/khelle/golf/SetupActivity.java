package com.example.khelle.golf;

import com.example.khelle.golf.DatabaseHandler;

import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class SetupActivity extends AppCompatActivity {

    private String[] nav_drawer_menu_items;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Button mTeeOffButton;
    private EditText player1;
    private EditText player2;
    private EditText player3;
    private EditText player4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        nav_drawer_menu_items = getResources().getStringArray(R.array.nav_drawer_menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mTeeOffButton = (Button) findViewById(R.id.begin_game_button);
        player1 = (EditText) findViewById(R.id.player1_to_add);
        player2 = (EditText) findViewById(R.id.player2_to_add);
        player3 = (EditText) findViewById(R.id.player3_to_add);
        player4 = (EditText) findViewById(R.id.player4_to_add);



        // Set adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, nav_drawer_menu_items));

        // Set list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // Set TeeOff button's click listener
        mTeeOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat currentDateAndTime = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String dateAndTime = currentDateAndTime.format(new Date());
                DatabaseHandler db = new DatabaseHandler(getApplicationContext());

                if(player1.getText().toString().equals("")){
                    // do not add
                } else {
                    String player1Name = player1.getText().toString();
                    db.addPlayer(new Player(player1Name, dateAndTime));
                }

                if(player2.getText().toString().equals("")){
                    // do not add
                } else {
                    String player2Name = player2.getText().toString();
                    db.addPlayer(new Player(player2Name, dateAndTime));
                }

                if(player3.getText().toString().equals("")){
                    // do not add
                } else {
                    String player3Name = player3.getText().toString();
                    db.addPlayer(new Player(player3Name, dateAndTime));
                }

                if(player4.getText().toString().equals("")){
                    // do not add
                } else {
                    String player4Name = player4.getText().toString();
                    db.addPlayer(new Player(player4Name, dateAndTime));
                }

                startGame(v);
            }
        });
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

}
