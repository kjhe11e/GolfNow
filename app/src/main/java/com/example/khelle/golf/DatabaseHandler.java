package com.example.khelle.golf;

import com.example.khelle.golf.Player;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by khelle on 6/8/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 2;

    // Database name
    private static final String DATABASE_NAME = "ScorecardsDatabase";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create scorecard table
        String CREATE_SCORECARD_TABLE = "CREATE TABLE scorecards ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "date TEXT )";

        // Create scorecard table
        db.execSQL(CREATE_SCORECARD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older scorecards table if existed
        db.execSQL("DROP TABLE IF EXISTS scorecards");

        // Create new scorecards table
        this.onCreate(db);
    }


    //----------------------------------------
    // CRUD operations
    //----------------------------------------

    // Scorecard table name
    private static final String TABLE_SCORECARD = "scorecards";

    // Scorecard table column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";

    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_DATE};


    public void addPlayer(Player player) {
        Log.d("addPlayer", player.toString());

        // Get reference to writeable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // Create ContentValues to add key column/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, player.getName());
        values.put(KEY_DATE, player.getDate());

        // Insert into database
        db.insert(TABLE_SCORECARD, null, values);

        // Close database
        db.close();
    }

    public Player getPlayer(int id) {
        // Get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // Build query
        Cursor cursor = db.query(TABLE_SCORECARD,       // table
                COLUMNS,                                // column names
                " id = ?",                              // selections
                new String[] { String.valueOf(id) },    // selections
                null,                                   // group by
                null,                                   // having order
                null,                                   // by
                null);                                  // limit

        // If get results, get the first one
        if(cursor != null){
            cursor.moveToFirst();
        }

        // Build player object
        Player player = new Player();
        player.setId(Integer.parseInt(cursor.getString(0)));
        player.setName(cursor.getString(1));
        player.setDate(cursor.getString(2));

        // Log
        Log.d("getPlayer("+id+")", player.toString());

        // Return the player
        return player;
    }

    public List<Player> getAllPlayers() {
        List<Player> players = new LinkedList<Player>();

        // Build query
        String query = "SELECT * FROM " + TABLE_SCORECARD;

        // Get reference to writeable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // Iterate over each row, build player info and add to list
        Player player = null;
        if(cursor.moveToFirst()) {
            do {
                player = new Player();
                player.setId(Integer.parseInt(cursor.getString(0)));
                player.setName(cursor.getString(1));
                player.setDate(cursor.getString(2));

                // Add player to list of players
                players.add(player);
            } while(cursor.moveToNext());
        }

        // Log
        Log.d("getAllPlayers()", players.toString());

        // Return players
        return players;
    }

    public int updatePlayer(Player player) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // Create ContentValues to add key column/value
        ContentValues values = new ContentValues();
        values.put("name", player.getName());
        values.put("date", player.getDate());

        // Update row for player
        int i = db.update(TABLE_SCORECARD,                          // table
                values,                                             // column/value
                KEY_ID+" = ?",                                      // selections
                new String[] { String.valueOf(player.getId()) });   // selection args

        // Close database
        db.close();

        // Log
        Log.d("updatePlayer", player.toString());

        return i;
    }

    public void deletePlayer(Player player) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // Delete
        db.delete( TABLE_SCORECARD,                                 // table
                KEY_ID+" = ?",                                      // selections
                new String[] { String.valueOf(player.getId()) });   // selection args

        // Close database
        db.close();

        // Log
        Log.d("deletePlayer", player.toString());
    }

}
