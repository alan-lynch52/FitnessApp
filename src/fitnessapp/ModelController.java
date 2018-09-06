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
    private DatabaseController2 dc;
    private LinkedList<Exercise> exList;
    private LinkedList<ExerciseRecord> exrList;
    private LinkedList<CalorieRecord> crList;
    private LinkedList<BodyWeightRecord> bwrList;
    private String username;
    private String password;
    public ModelController(){
        this.dc = new DatabaseController2();
    }
    /*
     Set of methods that instantiate the various classes
     These methods will first request a suitable id from the databasecontroller
     Then instantiate the object and then pass this object to the 
     databasecontroller to be added to the db
    */
    public boolean userSignIn(String username, String password){
        if (dc.login(username, password)){
            this.username = username;
            this.password = password;
            return true;
        }
        return false;
    }
    public void userSignOut(){
        this.username = null;
        this.password = null;
    }
    
    public boolean createExerciseRecord(String exerciseName, double weight){
        int id = dc.generateID(DatabaseController2.EXERCISE_RECORD_LIST);
        int exId = dc.getID(exerciseName, DatabaseController2.EXERCISE_LIST);
        if (exId == -1){
            return false;
        }
        ExerciseRecord er = new ExerciseRecord(id, exId, weight, new Date());
        //check with DatabaseController to see if exId exists
        //check with DatabaseController to find next unique id
        //if both exist - create ExerciseRecord and pass to DatabaseController
        return dc.addRecord(DatabaseController2.EXERCISE_RECORD, er);
    };
    public boolean createExercise(String name){
        //check with DatabaseController to find next unique id
        int id = dc.generateID(DatabaseController2.EXERCISE_LIST);
        if (id == -1) return false;
        Exercise e = new Exercise(id,name,new Date());
        return dc.addRecord(DatabaseController2.EXERCISE, e);
    };
    public boolean createCalorieRecord(int calories){
        
        //check with DatabaseController to find next unique id
        int id = dc.generateID(DatabaseController2.CALORIE_RECORD_LIST);
        if (id == -1){
            return false;
        }
        CalorieRecord cr = new CalorieRecord(id,calories,new Date());
        return dc.addRecord(DatabaseController2.CALORIE_RECORD, cr);
    };
    public boolean createBodyWeightRecord(double weight){
        int id = dc.generateID(DatabaseController2.BODYWEIGHT_RECORD_LIST);
        if (id == -1){
            return false;
        }
        BodyWeightRecord bwr =  new BodyWeightRecord(id,weight,new Date());
        return dc.addRecord(DatabaseController2.BODYWEIGHT_RECORD,bwr);
    };
    
    public String[] getExerciseList(){
        Exercise[] list = (Exercise[])dc.getExerciseList();
        
        int size = list.length;
        String[] eNames = new String[size];
        for(int i = 0; i < size; i++){
            eNames[i] = list[i].getName();
        }
        return eNames;
    }
    
    public double[] getERWeightList(String name){
        //System.out.println("Name: "+name);
        
        //get the index position of an exercise given the name of the exercise
        int index = 0;
        Exercise[] list = (Exercise[])dc.getExerciseList();
        for (int i = 0; i < list.length; i++){
            String curExName = list[i].getName();
            if (curExName.matches(name)){
                index = i;
            }
        }
        //use this index position to grab the id of the exercise
        try{
        int curExId = list[index].getId();
        //System.out.println(curExId);
        //add all weight values to the list where the exercise id matches
        ExerciseRecord[] exerciseList = (ExerciseRecord[])
                dc.getExerciseRecordList(curExId);
        if (exerciseList == null) return new double[0];
        double[] weights = new double[exerciseList.length];
        
        for (int i = 0; i < exerciseList.length; i++){
            weights[i] = exerciseList[i].getWeight();
            //System.out.println(weights[i]);
        }
        return weights;
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Index out of bounds");
        }
        return null;
    };
    public double[] getBWRWeightList(){
        BodyWeightRecord[] bwrList = (BodyWeightRecord[])
                dc.getBodyWeightRecordList();
        if (bwrList == null) return new double[0];
        double[] list = new double[bwrList.length];
        //get up to date version of bodyweight record list
        for (int i = 0; i < list.length; i++){
            list[i] = bwrList[i].getWeight();
            //System.out.println(list[i]);
        }
        return list;
    }
    public int getDailyCalories(Date d){
        CalorieRecord[] dailyCals = (CalorieRecord[])dc.getCalorieRecordList(d);
        if (dailyCals==null)return 0;
        int totalCals = 0;
        for(CalorieRecord cr : dailyCals){
            totalCals += cr.getCalories();
        }
        return totalCals;
    }
    public Object[] getExerciseRecordList(String name){
        //get the index position of an exercise given the name of the exercise
        int index = 0;
        Exercise[] exList = (Exercise[])dc.getExerciseList();
        if (exList == null) return null;
        for (int i = 0; i < exList.length; i++){
            String curExName = exList[i].getName();
            if (curExName.matches(name)){
                index = i;
            }
        }
        //use this index position to grab the id of the exercise
        int curExId = exList[index].getId();
        //System.out.println(curExId);
        //add all weight values to the list where the exercise id matches
        ExerciseRecord[] exerciseList = (ExerciseRecord[])
                dc.getExerciseRecordList(curExId);
        if (exerciseList == null) return null;
        Object[] list = new Object[exerciseList.length];
        
        for (int i = 0; i < exerciseList.length; i++){
            list[i] = exerciseList[i];
            //System.out.println(list[i]);
        }
        return list;
    }
    public Object[] getBodyweightRecordList(){
        BodyWeightRecord[] bwrList = (BodyWeightRecord[])
                dc.getBodyWeightRecordList();
        Object[] list = new Object[bwrList.length];
        for (int i = 0; i < list.length; i++){
            list[i] = bwrList[i];
        }
        return list;
    }
    public Object[] getCalorieRecordList(){
        CalorieRecord[] dailyCalList = (CalorieRecord[])dc.getCalorieRecordList(new Date());
        return dailyCalList;
    }
    /*
        Set of methods that:
        Given an id, delete the object from the database if it exists
    */
    public boolean deleteExercise(String exName){
        for (Exercise e : exList){
            if (e.getName().matches(exName)){
                if (dc.deleteRecord(DatabaseController2.EXERCISE,e)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean deleteExerciseRecord(Object o){
        ExerciseRecord er  = (ExerciseRecord)o;
        if (dc.deleteRecord(DatabaseController2.EXERCISE_RECORD,er)){
            return true;
        }
        return false;
    }
    public boolean deleteCalorieRecord(Object o){
        CalorieRecord cr = (CalorieRecord)o;
        if (dc.deleteRecord(DatabaseController2.CALORIE_RECORD,cr)){
            return true;
        }
        return false;
    }
    public boolean deleteBodyWeightRecord(Object o){
        BodyWeightRecord bwr = (BodyWeightRecord)o;
        if (dc.deleteRecord(DatabaseController2.BODYWEIGHT_RECORD,bwr)){
            return true;
        }
        return false;
    }
    
    public static void main(String[] args){
        ModelController mc = new ModelController();
        boolean loggedIn = mc.userSignIn("user1", "p123");
        System.out.println(loggedIn);
        if (loggedIn == true){
            System.out.println("Login Successful");
            double[] weight = mc.getBWRWeightList();
            System.out.println(weight[0]);
            
            String[] exList = mc.getExerciseList();
            System.out.println(exList[0]);
            
        }
        else{
            System.out.println("Login Failed");
        }
    }
}
