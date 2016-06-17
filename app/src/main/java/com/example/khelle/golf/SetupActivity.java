package com.example.khelle.golf;

import com.example.khelle.golf.DatabaseHandler;

import android.content.Intent;
import android.database.DatabaseErrorHandler;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

// TO DO: keep info on orientation change


public class SetupActivity extends AppCompatActivity {

    private String[] nav_drawer_menu_items;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Button mTeeOffButton;
    private Button mAddPlayerButton;
    private LinearLayout mPlayerList;
    private Integer playerCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        nav_drawer_menu_items = getResources().getStringArray(R.array.nav_drawer_menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mTeeOffButton = (Button) findViewById(R.id.begin_game_button);
        mAddPlayerButton = (Button) findViewById(R.id.add_player_button);
        mPlayerList = (LinearLayout) findViewById(R.id.linear_layout_player_list);
        playerCount = 0;


        // Set adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, nav_drawer_menu_items));

        // Set drawer list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // Set AddPlayer button's click listener
        mAddPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(playerCount < 5){
                    EditText newPlayer = new EditText(getApplicationContext());
                    newPlayer.setHint("Player name");
                    newPlayer.setHintTextColor(Color.parseColor("#000000"));
                    newPlayer.setTextColor(Color.parseColor("#000000"));
                    mPlayerList.addView(newPlayer, 0);
                    playerCount++;
                } else {
                    Toast.makeText(getApplicationContext(), "Max 5 players per game", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Set TeeOff button's click listener
        mTeeOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle extras = new Bundle();
                int numPlayers = 0;

                for(int i = 0; i < mPlayerList.getChildCount(); i++){
                    EditText player = (EditText) mPlayerList.getChildAt(i);
                    String playerName = player.getText().toString();
                    if(playerName != ""){
                        extras.putString("player"+i, playerName);
                        numPlayers++;
                    } else {
                        // do nothing
                    }
                }

                if(numPlayers > 0) {
                    // Pass playerCount to Game activity to populate scorecard
                    extras.putInt("numPlayers", numPlayers);
                    Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter at least 1 player", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
