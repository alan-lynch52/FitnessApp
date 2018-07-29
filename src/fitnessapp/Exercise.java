/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnessapp;

import java.util.Date;

/**
 *
 * @author Alan
 */
public class Exercise {
    private int id;
    private String name;
    private Date dateAdded;
    public Exercise(){}
    public Exercise(int id, String name, Date dateAdded) {
        this.id = id;
        this.name = name;
        this.dateAdded = dateAdded;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public String toString() {
        return "Exercise{" + "id=" + id + ", name=" + name + ", dateAdded=" + dateAdded + '}';
    }
    
    public String myJSON(){
        return "\""+this.id+"\"" + ":{" + "\""+"name:"+"\"" + this.name + ", date:" + this.dateAdded + "}";
    }
    
}
