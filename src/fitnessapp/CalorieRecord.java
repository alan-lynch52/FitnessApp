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
public class CalorieRecord {
    private int id;
    private int calories;
    private Date recordDate;
    public CalorieRecord(){}
    public CalorieRecord(int id, int calories, Date recordDate) {
        this.id = id;
        this.calories = calories;
        this.recordDate = recordDate;
    }

    public int getId() {
        return id;
    }

    public int getCalories() {
        return calories;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    @Override
    public String toString() {
        return "id=" + id + ", calories=" + calories + ", date=" + recordDate;
    }
    
}
