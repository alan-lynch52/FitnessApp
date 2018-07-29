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
public class ExerciseRecord {
    private int id;
    private int exerciseId;
    private double weight;
    private Date recordDate;
    public ExerciseRecord(){}
    public ExerciseRecord(int id, int exerciseId, double weight, Date recordDate) {
        this.id = id;
        this.exerciseId = exerciseId;
        this.weight = weight;
        this.recordDate = recordDate;
    }

    public int getId() {
        return id;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public double getWeight() {
        return weight;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    @Override
    public String toString() {
        return "ExerciseRecord{" + "id=" + id + ", exerciseId=" + exerciseId + ", weight=" + weight + ", recordDate=" + recordDate + '}';
    }
    
}
