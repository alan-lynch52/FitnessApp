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
public class BodyWeightRecord {
    private int id;
    private double weight;
    private Date recordDate;
    public BodyWeightRecord(){}
    public BodyWeightRecord(int id, double weight, Date recordDate) {
        this.id = id;
        this.weight = weight;
        this.recordDate = recordDate;
    }

    public int getId() {
        return id;
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

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    @Override
    public String toString() {
        return "BodyWeightRecord{" + "id=" + id + ", weight=" + weight + ", recordDate=" + recordDate + '}';
    }
    
    
}
