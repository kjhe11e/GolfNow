package com.example.khelle.golf;


/**
 * Created by khelle on 6/8/16.
 */
public class Player {
    // Private vars
    private int id;
    private String name;
    private String date;

    // Empty constructor
    public Player() {

    }

    // Constructor
    public Player(String name, String date) {
        super();
        this.name = name;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Player [id=" + id + ", name=" + name + ", date=" + date + "]";
    }

    // Getters and setters
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
