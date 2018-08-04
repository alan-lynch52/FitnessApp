/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnessapp;

import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @author Alan
 */
public class ModelController {
    private DatabaseController dc;
    private LinkedList<Exercise> exList;
    private LinkedList<ExerciseRecord> exrList;
    private LinkedList<CalorieRecord> crList;
    private LinkedList<BodyWeightRecord> bwrList;
    public ModelController(){
        this.dc = new DatabaseController();
        exList = this.dc.getExerciseList();
        exrList = this.dc.getExerciseRecordList();
        crList = this.dc.getCalorieRecordList();
        bwrList = this.dc.getBodyweightRecordList();
    }
    /*
     Set of methods that instantiate the various classes
     These methods will first request a suitable id from the databasecontroller
     Then instantiate the object and then pass this object to the 
     databasecontroller to be added to the db
    */
    public boolean i_ExerciseRecord(String exerciseName, double weight){
        int id = dc.genExerciseRecordID();
        int exId = dc.findExerciseID(exerciseName);
        if (exId == -1){
            return false;
        }
        ExerciseRecord er = new ExerciseRecord(id, exId, weight, new Date());
        //check with DatabaseController to see if exId exists
        //check with DatabaseController to find next unique id
        //if both exist - create ExerciseRecord and pass to DatabaseController
        return dc.addExerciseRecord(er);
    };
    public boolean i_Exercise(String name){
        //check with DatabaseController to find next unique id
        int id = dc.genExerciseID();
        Exercise e = new Exercise(id,name,new Date());
        return dc.addExercise(e);
    };
    public boolean i_CalorieRecord(int calories){
        
        //check with DatabaseController to find next unique id
        int id = dc.genCalorieRecordID();
        if (id == -1){
            return false;
        }
        CalorieRecord cr = new CalorieRecord(id,calories,new Date());
        return dc.addCalorieRecord(cr);
    };
    public boolean i_BodyWeightRecord(double weight){
        int id = dc.genBodyweightRecordID();
        if (id == -1){
            return false;
        }
        BodyWeightRecord bwr =  new BodyWeightRecord(id,weight,new Date());
        return dc.addBodyweightRecord(bwr);
    };
    
    public String[] g_ExerciseList(){
        Exercise[] list = dc.getExerciseList().toArray(
                new Exercise[dc.getExerciseList().size()]);
        int size = dc.getExerciseList().size();
        String[] eNames = new String[size];
        for(int i = 0; i < size; i++){
            eNames[i] = list[i].getName();
        }
        return eNames;
    }
    
    public double[] g_ERWeight(String name){
        //System.out.println("Name: "+name);
        
        //get the index position of an exercise given the name of the exercise
        int index = 0;
        exList = dc.getExerciseList();
        for (int i = 0; i < exList.size(); i++){
            String curExName = exList.get(i).getName();
            if (curExName.matches(name)){
                index = i;
            }
        }
        //use this index position to grab the id of the exercise
        try{
        int curExId = exList.get(index).getId();
        //System.out.println(curExId);
        //add all weight values to the list where the exercise id matches
        LinkedList<ExerciseRecord> exerciseList = dc.getExerciseRecordList(curExId);
        double[] list = new double[exerciseList.size()];
        
        for (int i = 0; i < exerciseList.size(); i++){
            list[i] = exerciseList.get(i).getWeight();
            System.out.println(list[i]);
        }
        return list;
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Index out of bounds");
        }
        return null;
    };
    public double[] g_BWRWeight(){
        bwrList = dc.getBodyweightRecordList();
        double[] list = new double[bwrList.size()];
        //get up to date version of bodyweight record list
        for (int i = 0; i < list.length; i++){
            list[i] = bwrList.get(i).getWeight();
            System.out.println(list[i]);
        }
        return list;
    }
    public int g_DailyCalories(Date d){
        LinkedList<CalorieRecord> dailyCals = dc.getCalorieRecordList(d);
        int totalCals = 0;
        for(CalorieRecord cr : dailyCals){
            totalCals += cr.getCalories();
        }
        return totalCals;
    }
    public Object[] g_ExerciseRecordList(String name){
                //System.out.println("Name: "+name);
        
        //get the index position of an exercise given the name of the exercise
        int index = 0;
        exList = dc.getExerciseList();
        for (int i = 0; i < exList.size(); i++){
            String curExName = exList.get(i).getName();
            if (curExName.matches(name)){
                index = i;
            }
        }
        //use this index position to grab the id of the exercise
        int curExId = exList.get(index).getId();
        //System.out.println(curExId);
        //add all weight values to the list where the exercise id matches
        LinkedList<ExerciseRecord> exerciseList = dc.getExerciseRecordList(curExId);
        Object[] list = new Object[exerciseList.size()];
        
        for (int i = 0; i < exerciseList.size(); i++){
            list[i] = exerciseList.get(i);
            System.out.println(list[i]);
        }
        return list;
    }
    public Object[] g_BodyweightRecordList(){
        bwrList = dc.getBodyweightRecordList();
        Object[] list = new Object[bwrList.size()];
        for (int i = 0; i < list.length; i++){
            list[i] = bwrList.get(i);
        }
        return list;
    }
    public Object[] g_CalorieRecordList(){
        LinkedList<CalorieRecord> dailyCalList = dc.getCalorieRecordList(new Date());
        Object[] list = new Object[dailyCalList.size()];
        for (int i = 0; i < list.length; i++){
            list[i] = dailyCalList.get(i);
        }
        return list;
    }
    /*
        Set of methods that, given an id, will return an 
        object if it is found within the database
    */
    public ExerciseRecord g_ExerciseRecord(int id){
        return null;
    };
    public Exercise g_Exercise(int id){
        return null;
    };
    public CalorieRecord g_CalorieRecord(int id){
        return null;
    };
    public BodyWeightRecord g_BodyWeightRecord(int id){
        return null;
    };
    /*
        Set of methods that:
        Given an id, delete the object from the database if it exists
    */
    public void d_ExerciseRecord(int id){}
    public boolean d_Exercise(String exName){
        for (Exercise e : exList){
            if (e.getName().matches(exName)){
                if (dc.deleteExercise(e)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean d_ExerciseRecord(Object o){
        ExerciseRecord er  = (ExerciseRecord)o;
        if (dc.deleteExerciseRecord(er)){
            return true;
        }
        return false;
    }
    public boolean d_CalorieRecord(Object o){
        CalorieRecord cr = (CalorieRecord)o;
        if (dc.deleteCalorieRecord(cr)){
            return true;
        }
        return false;
    }
    public boolean d_BodyWeightRecord(Object o){
        BodyWeightRecord bwr = (BodyWeightRecord)o;
        if (dc.deleteBodyweightRecord(bwr)){
            return true;
        }
        return false;
    }
}
