/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnessapp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static java.lang.Math.toIntExact;
import java.text.SimpleDateFormat;

/**
 *
 * @author Alan
 */
public class DatabaseController {
    
    //String Constants
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String EXERCISES = "exercises";
    private static final String EXERCISE_RECORDS = "exerciseRecords";
    private static final String CALORIE_RECORDS = "calorieRecords";
    private static final String BODYWEIGHT_RECORDS = "bodyweightRecords";
    
    
    private static final String ID = "id";
    private static final String EXERCISE_ID = "exerciseID";
    private static final String NAME = "name";
    private static final String WEIGHT = "weight";
    private static final String DATE = "date";
    private static final String CALORIES = "calories";

    private JSONObject data;

    DatabaseController() {
        data = readDB();
    }

    public boolean checkExerciseId(int id) {
        JSONArray exerciseList = (JSONArray) data.get(EXERCISES);
        String strID = String.valueOf(id);
        for (Object item : exerciseList) {
            JSONObject jsonItem = (JSONObject) item;
            Long l = (Long) jsonItem.get(ID);
            int itemID = l.intValue();
            if (itemID == id) {
                System.out.println("Found ID");
                System.out.println(jsonItem.toJSONString());
                return true;
            }
        }
        System.out.println("ID Not Found");
        return false;
    }
    public boolean checkExerciseName(String name){
        JSONArray exerciseList = (JSONArray)data.get(EXERCISES);
        for (Object o : exerciseList){
            JSONObject oJSON = (JSONObject) o;
            String oName = (String)oJSON.get(NAME);
            System.out.println(oName);
            if (oName.matches(name)){
                System.out.println("Name already exists!");
                return true;
            }
        }
        return false;
    }

    ;
    public boolean checkExerciseRecordId(int id) {
        //JSONObject data = readDB();
        JSONArray exerciseList = (JSONArray) data.get(EXERCISE_RECORDS);
        String strID = String.valueOf(id);
        for (Object item : exerciseList) {
            System.out.println(item.toString());
            JSONObject jsonItem = (JSONObject) item;
            Object itemID = jsonItem.get(ID);
            if (itemID.getClass() == Long.class){
                Long idVal =  (Long)itemID;
                if (idVal.intValue() == id){
                    return true;
                }
            }
            else if (itemID.getClass() == Integer.class){
                Integer idVal = (Integer)itemID;
                if (idVal.intValue() == id){
                    return true;
                }
            }
        }
        System.out.println("ID Not Found");
        return false;
    }

    ;
    
    public boolean checkCalorieRecordId(int id) {
        //JSONObject data = readDB();
        JSONArray exerciseList = (JSONArray) data.get(CALORIE_RECORDS);
        String strID = String.valueOf(id);
        for (Object item : exerciseList) {
            System.out.println(item.toString());
            JSONObject jsonItem = (JSONObject) item;
            System.out.println(jsonItem.get(ID).getClass());
            Object itemID = jsonItem.get(ID);
            if (itemID.getClass() == Long.class){
                Long idVal = (Long)itemID;
                if(idVal.intValue() == id){
                    return true;
                }
            }else if (itemID.getClass() == Integer.class){
                Integer idVal = (Integer)itemID;
                if(idVal.intValue() == id){
                    return true;
                }
            }
        }
        System.out.println("ID Not Found");
        
        return false;
    }

    ;
    
    public boolean checkBodyweightRecordId(int id) {
        //JSONObject data = readDB();
        JSONArray exerciseList = (JSONArray) data.get(BODYWEIGHT_RECORDS);
        String strID = String.valueOf(id);
        for (Object item : exerciseList) {
            System.out.println(item.toString());
            JSONObject jsonItem = (JSONObject) item;
            Object itemID = jsonItem.get(ID);
            int idVal = 0;
            if (itemID.getClass() == Integer.class){
                Integer itemIDVal = (Integer)itemID;
                idVal = itemIDVal.intValue();
            }
            else if (itemID.getClass() == Long.class){
                Long itemIDVal = (Long)itemID;
                idVal = itemIDVal.intValue();
            }
            if (idVal == id) {
                System.out.println("Found ID");
                return true;
            }
        }
        System.out.println("ID Not Found");
        return false;
    }

    ;
    
    public int genCalorieRecordID() {
        JSONArray calorieList = (JSONArray) data.get(CALORIE_RECORDS);
        //get last object
        if (calorieList.isEmpty()) {
            return 0;
        }
        int index = calorieList.size() - 1;
        JSONObject o = (JSONObject) calorieList.get(index);
        Object oID = o.get(ID);
        if (oID.getClass() == Long.class){
            Long idVal = (Long)oID;
            return idVal.intValue() + 1;
        }
        else if (oID.getClass() == Integer.class){
            Integer idVal = (Integer)oID;
            return idVal.intValue() + 1;
        }
        return -1;
    }

    public int genExerciseID() {
        JSONArray exerciseList = (JSONArray) data.get(EXERCISES);
        if (exerciseList.isEmpty()) {
            return 0;
        }
        int index = exerciseList.size() - 1;
        JSONObject o = (JSONObject) exerciseList.get(index);
        Long id = (Long) o.get("id");

        return id.intValue() + 1;
    }

    public int genExerciseRecordID() {
        JSONArray exerciseList = (JSONArray) data.get(EXERCISE_RECORDS);
        if (exerciseList.isEmpty()) {
            return 0;
        }
        int index = exerciseList.size() - 1;
        JSONObject o = (JSONObject) exerciseList.get(index);
        Object id = o.get(ID);
        if (id.getClass() == Long.class){
            Long idVal = (Long) o.get(ID);
            return idVal.intValue() + 1;
        }
        else if(id.getClass() == Integer.class){
            Integer idVal = (Integer)o.get(ID);
            return idVal.intValue() + 1;
        }
        return -1;
    }

    public int genBodyweightRecordID() {
        JSONArray exerciseList = (JSONArray) data.get(BODYWEIGHT_RECORDS);
        if (exerciseList.isEmpty()) {
            return 0;
        }
        int index = exerciseList.size() - 1;
        JSONObject o = (JSONObject) exerciseList.get(index);
        System.out.println(o.get(ID).getClass());
        
        Object id = o.get(ID);
        if(id.getClass() == Integer.class){
            Integer idVal = (Integer)id;
            return idVal.intValue() + 1;
        }
        else if (id.getClass() == Long.class){
            Long idVal = (Long)id;
            return idVal.intValue() + 1;
        }
        
        return -1;
    }

    public int findExerciseID(String name) {
        JSONArray exerciseList = (JSONArray) data.get(EXERCISES);
        for (Object o : exerciseList) {
            JSONObject oJSON = (JSONObject) o;
            String oName = (String) oJSON.get(NAME);
            if (oName.matches(name)) {
                Long id = (Long) oJSON.get(ID);
                return id.intValue();
            }
        }
        return -1;
    }

    private static JSONObject readDB() {
        try {
            String fp = "data.json";
            FileReader r = new FileReader(fp);
            JSONParser p = new JSONParser();
            JSONObject data = (JSONObject) p.parse(r);
            return data;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return initDB();
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    //Method that will initialise a database when the app is first loaded
    private static JSONObject initDB() {
        try {
            String fp = "data.json";
            FileWriter fw = new FileWriter(fp);
            JSONObject data = new JSONObject();
            JSONArray emptyList = new JSONArray();

            data.put(EXERCISES, emptyList);
            data.put(EXERCISE_RECORDS, emptyList);
            data.put(CALORIE_RECORDS, emptyList);
            data.put(BODYWEIGHT_RECORDS, emptyList);

            fw.write(data.toJSONString());
            fw.flush();
            System.out.println("DB Initialized");
            return data;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean addExercise(Exercise e) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        //read db
        //JSONObject data = readDB();
        //find exercise list
        JSONArray exercises = (JSONArray) data.get(EXERCISES);
        //add exercise
        JSONObject ex = new JSONObject();
        if (!checkExerciseId(e.getId()) && !checkExerciseName(e.getName())) {
            ex.put(ID, e.getId());
            ex.put(NAME, e.getName());
            ex.put(DATE,
                    sdf.format(e.getDateAdded()));
            exercises.add(ex);
            //write to file
            writeJSON(data);
            return true;
        } else {
            System.out.println("ID Already Exists");
        }
        return false;
    }

    public boolean addExerciseRecord(ExerciseRecord er) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        //read db
        //JSONObject data = readDB();
        //find exercise record list
        JSONArray exRecords = (JSONArray) data.get(EXERCISE_RECORDS);
        //add exercise record
        if (!checkExerciseRecordId(er.getId())
                && checkExerciseId(er.getExerciseId())) {
            JSONObject erJSON = new JSONObject();
            erJSON.put(EXERCISE_ID, er.getExerciseId());
            erJSON.put(WEIGHT, er.getWeight());
            erJSON.put(DATE,
                    sdf.format(er.getRecordDate()));
            erJSON.put(ID, er.getId());
            exRecords.add(erJSON);
            System.out.println("Exercise Record");
            System.out.println(data);
            //write to file
            writeJSON(data);
            return true;
        }
        return false;
    }

    ;
    public boolean addCalorieRecord(CalorieRecord cr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        //read db
        //JSONObject data = readDB();
        //find calorie record list
        JSONArray crList = (JSONArray) data.get(CALORIE_RECORDS);
        //add calorie record to list
        if (!checkCalorieRecordId(cr.getId())) {
            JSONObject crJSON = new JSONObject();
            crJSON.put(CALORIES, cr.getCalories());
            crJSON.put(DATE,
                    sdf.format(cr.getRecordDate()));
            crJSON.put(ID, cr.getId());
            crList.add(crJSON);
            //write to file
            writeJSON(data);
            return true;
        }
        return false;
    }

    public boolean addBodyweightRecord(BodyWeightRecord br) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        //read db
        //JSONObject data = readDB();
        //find bodyweight record list
        JSONArray bwList = (JSONArray) data.get(BODYWEIGHT_RECORDS);
        //add bodyweight record to list
        if (!checkBodyweightRecordId(br.getId())) {
            JSONObject brJSON = new JSONObject();
            brJSON.put(WEIGHT, br.getWeight());
            brJSON.put(DATE,
                    sdf.format(br.getRecordDate()));
            brJSON.put(ID, br.getId());
            bwList.add(brJSON);
            //write to file
            writeJSON(data);
            return true;
        }
        return false;
    }

    public boolean editExercise(Exercise e) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        //read db
        //JSONObject data = readDB();
        JSONArray exerciseList = (JSONArray) data.get(EXERCISES);
        String id = String.valueOf(e.getId());
        //find existing exercise
        for (Object o : exerciseList) {
            JSONObject oJSON = (JSONObject) o;
            //Long oId = (Long) oJSON.get(ID);
            int oID = getIntFromObject(oJSON.get(ID));
            if (e.getId() == oID) {
                oJSON.put(NAME, e.getName());
                oJSON.put(DATE,
                        sdf.format(e.getDateAdded()));
                writeJSON(data);
                return true;
            }
        }
        //edit fields
        //write to file
        return false;
    }

    ;
    public boolean editExerciseRecord(ExerciseRecord er) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        //read db
        //JSONObject data = readDB();
        JSONArray erList = (JSONArray) data.get(EXERCISE_RECORDS);
        String erId = String.valueOf(er.getId());
        //find existing exercise
        for (Object o : erList) {
            JSONObject oJSON = (JSONObject) o;
            //Long oID = (Long) oJSON.get(ID);
            int oID = getIntFromObject(oJSON.get(ID));
            if (oID == er.getId()) {
                if (checkExerciseId(er.getExerciseId())) {
                    oJSON.put(EXERCISE_ID, er.getExerciseId());
                    oJSON.put(WEIGHT, er.getWeight());
                    oJSON.put(DATE,
                            sdf.format(er.getRecordDate()));
                    writeJSON(data);
                    return true;
                }
            }
        }
        //edit fields
        //write to file
        return false;
    }

    ;
    public boolean editCalorieRecord(CalorieRecord cr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        //read db
        //JSONObject data = readDB();
        JSONArray crList = (JSONArray) data.get(CALORIE_RECORDS);
        String crId = String.valueOf(cr.getId());
        //find existing exercise
        for (Object o : crList) {
            JSONObject oJSON = (JSONObject) o;
            //Long oID = (Long) oJSON.get(ID);
            int oID = getIntFromObject(oJSON.get(ID));
            if (oID == cr.getId()) {
                oJSON.put(CALORIES, cr.getCalories());
                oJSON.put(DATE,
                        sdf.format(cr.getRecordDate()));
                writeJSON(data);
                return true;
            }
        }
        //edit fields
        //write to file
        return false;
    }

    ;
    public boolean editBodyweightRecord(BodyWeightRecord bwr) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        //read db
        //JSONObject data = readDB();
        JSONArray bwrList = (JSONArray) data.get(BODYWEIGHT_RECORDS);
        String bwrId = String.valueOf(bwr.getId());
        //find existing exercise
        for (Object o : bwrList) {
            JSONObject oJSON = (JSONObject) o;
            //Long oID = (Long) oJSON.get(ID);
            int oID = getIntFromObject(oJSON.get(ID));
            if (oID == bwr.getId()) {
                oJSON.put(WEIGHT, bwr.getWeight());
                oJSON.put(DATE, sdf.format(bwr.getRecordDate()));
                writeJSON(data);
                return true;
            }
        }
        //edit fields
        //write to file
        return false;
    }

    ;
    
    public boolean deleteExercise(Exercise e) {
        //read db
        //JSONObject data = readDB();
        //find exercise list
        JSONArray exerciseList = (JSONArray) data.get(EXERCISES);
        //find exercise with given id
        if (checkExerciseId(e.getId())) {
            for (Object o : exerciseList) {
                JSONObject obj = (JSONObject) o;
                //Long objId = (Long) obj.get(ID);
                int objID = getIntFromObject(obj.get(ID));
                if (e.getId() == objID) {
                    exerciseList.remove(obj);
                    writeJSON(data);
                    System.out.println("Removed");
                    return true;
                }
            }
        }
        return false;
    }

    ;
    public boolean deleteExerciseRecord(ExerciseRecord er) {
        //read db
        //JSONObject data = readDB();
        //find exercise list
        JSONArray erList = (JSONArray) data.get(EXERCISE_RECORDS);
        //find exercise with given id
        if (checkExerciseId(er.getExerciseId())
                && checkExerciseRecordId(er.getId())) {
            for (Object o : erList) {
                JSONObject obj = (JSONObject) o;
                //Long objID = (Long) obj.get(ID);
                int objID = getIntFromObject(obj.get(ID));
                if (objID == er.getId()) {
                    erList.remove(obj);
                    writeJSON(data);
                    System.out.println("Removed");
                    return true;
                }
            }
        }
        return false;
    }

    ;
    public boolean deleteCalorieRecord(CalorieRecord cr) {
        //read db
        //JSONObject data = readDB();
        //find exercise list
        JSONArray crList = (JSONArray) data.get(CALORIE_RECORDS);
        //find exercise with given id
        if (checkCalorieRecordId(cr.getId())) {
            for (Object o : crList) {
                JSONObject obj = (JSONObject) o;
                //Long objID = (Long) obj.get(ID);
                int objID = getIntFromObject(obj.get(ID));
                if (objID == cr.getId()) {
                    crList.remove(obj);
                    writeJSON(data);
                    System.out.println("Removed");
                    return true;
                }
            }
        }
        return false;
    }

    ;
    public boolean deleteBodyweightRecord(BodyWeightRecord br) {
        //read db
        //JSONObject data = readDB();
        //find exercise list
        JSONArray bwList = (JSONArray) data.get(BODYWEIGHT_RECORDS);
        //find exercise with given id
        if (checkBodyweightRecordId(br.getId())) {
            for (Object o : bwList) {
                JSONObject obj = (JSONObject) o;
                //Long objID = (Long) obj.get(ID);
                int objID = getIntFromObject(obj.get(ID));
                if (objID == br.getId()) {
                    bwList.remove(obj);
                    writeJSON(data);
                    System.out.println("Removed");
                    return true;
                }
            }
        }
        return false;
    }

    ;
    public LinkedList<Exercise> getExerciseList() {
        //JSONObject data = readDB();
        JSONArray exercises = (JSONArray) data.get(EXERCISES);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        LinkedList<Exercise> list = new LinkedList<>();
        for (Object o : exercises) {
            JSONObject oJSON = (JSONObject) o;
            //Long oID = (Long) oJSON.get(ID);
            int oID = getIntFromObject(oJSON.get(ID));
            String oName = (String) oJSON.get(NAME);
            Date oDate;
            try {
                oDate = sdf.parse((String) oJSON.get(DATE));
                Exercise ex = new Exercise(oID, oName, oDate);
                list.add(ex);
            } catch (java.text.ParseException ex) {
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    public LinkedList<ExerciseRecord> getExerciseRecordList() {
        //JSONObject data = readDB();
        JSONArray exercises = (JSONArray) data.get(EXERCISE_RECORDS);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        LinkedList<ExerciseRecord> list = new LinkedList<>();
        for (Object o : exercises) {
            try {
                JSONObject oJSON = (JSONObject) o;
                Long oID = (Long) oJSON.get(ID);
                Long oExID = (Long) oJSON.get(EXERCISE_ID);
                double oWeight = (double) oJSON.get(WEIGHT);
                Date oDate = sdf.parse((String) oJSON.get(DATE));
                ExerciseRecord er
                        = new ExerciseRecord(
                                oID.intValue(),
                                oExID.intValue(),
                                oWeight,
                                oDate
                        );
                list.add(er);
            } catch (java.text.ParseException ex) {
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    //return a subset of exercise records with a given exercise id
    public LinkedList<ExerciseRecord> getExerciseRecordList(int exID){
                //JSONObject data = readDB();
        JSONArray exercises = (JSONArray) data.get(EXERCISE_RECORDS);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        LinkedList<ExerciseRecord> list = new LinkedList<>();
        for (Object o : exercises) {
            try {
                JSONObject oJSON = (JSONObject) o;
                //Long oID = (Long) oJSON.get(ID);
                int oID = DatabaseController.getIntFromObject(oJSON.get(ID));
                int oExID = DatabaseController.getIntFromObject(oJSON.get(EXERCISE_ID));
                double oWeight = (double) oJSON.get(WEIGHT);
                Date oDate = sdf.parse((String) oJSON.get(DATE));
                if (oExID == exID){
                    ExerciseRecord er
                        = new ExerciseRecord(
                                oID,
                                oExID,
                                oWeight,
                                oDate
                        );
                    list.add(er);
                }
            } catch (java.text.ParseException ex) {
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }

    public LinkedList<CalorieRecord> getCalorieRecordList() {
        //JSONObject data = readDB();
        JSONArray exercises = (JSONArray) data.get(CALORIE_RECORDS);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        LinkedList<CalorieRecord> list = new LinkedList<>();
        for (Object o : exercises) {
            try {
                JSONObject oJSON = (JSONObject) o;
                Long oID = (Long) oJSON.get(ID);
                Long oCalories = (Long) oJSON.get(CALORIES);
                Date oDate = sdf.parse((String) oJSON.get(DATE));
                CalorieRecord cr
                        = new CalorieRecord(
                                oID.intValue(),
                                oCalories.intValue(),
                                oDate
                        );
                list.add(cr);
            } catch (java.text.ParseException ex) {
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    public LinkedList<CalorieRecord> getCalorieRecordList(Date d) {
        //JSONObject data = readDB();
        JSONArray exercises = (JSONArray) data.get(CALORIE_RECORDS);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String dStr = sdf.format(d);
        try {
            Date dFmt = sdf.parse(dStr);
            LinkedList<CalorieRecord> list = new LinkedList<>();
            for (Object o : exercises) {
                    JSONObject oJSON = (JSONObject) o;
                    int oID = getIntFromObject(oJSON.get(ID));
                    int oCalories = getIntFromObject(oJSON.get(CALORIES));
                    Date oDate = sdf.parse((String) oJSON.get(DATE));
                    if(dFmt.compareTo(oDate) == 0){
                        CalorieRecord cr
                            = new CalorieRecord(
                                    oID,
                                    oCalories,
                                    oDate
                            );
                        list.add(cr);
                    }


            }
            return list;
        } catch (java.text.ParseException ex) {
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public LinkedList<BodyWeightRecord> getBodyweightRecordList() {
        //JSONObject data = readDB();
        JSONArray exercises = (JSONArray) data.get(BODYWEIGHT_RECORDS);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        LinkedList<BodyWeightRecord> list = new LinkedList<>();
        for (Object o : exercises) {
            try {
                JSONObject oJSON = (JSONObject) o;
                //Long oID = (Long) oJSON.get(ID);
                int oID = getIntFromObject(oJSON.get(ID));
                double oWeight = (double) oJSON.get(WEIGHT);
                Date oDate = sdf.parse((String) oJSON.get(DATE));
                BodyWeightRecord bwr
                        = new BodyWeightRecord(
                                oID,
                                oWeight,
                                oDate
                        );
                list.add(bwr);
            } catch (java.text.ParseException ex) {
                Logger.getLogger(DatabaseController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return list;
    }
    
    //returns an int value from an object, determining whether it is an Integer or Long
    private static int getIntFromObject(Object o){
        if (o.getClass() == Long.class){
            Long idVal = (Long)o;
            return idVal.intValue();
        }
        else if (o.getClass() == Integer.class){
            Integer idVal = (Integer)o;
            return idVal.intValue();
        }
        return -1;
    }

    //For testing purposes
    public static void main(String[] args) {
        //Test objects
        Exercise e = new Exercise(2, "Lat Pulldown", new Date());
        ExerciseRecord er = new ExerciseRecord(2, 2, 75.50, new Date());
        CalorieRecord cr = new CalorieRecord(1, 1050, new Date());
        BodyWeightRecord bwr = new BodyWeightRecord(1, 70.5, new Date());

        //Testing adding objects to db
//        addExercise(e);
//        e.setName("Leg Extension");
//        editExercise(e);
//        deleteExercise(e);
//        addExerciseRecord(er);
//        er.setWeight(100.50);
//        editExerciseRecord(er);
//        deleteExerciseRecord(er);
//        addCalorieRecord(cr);
//        cr.setCalories(975);
//        editCalorieRecord(cr);
//        deleteCalorieRecord(cr);
//        addBodyweightRecord(bwr);
//        bwr.setWeight(150);
//        editBodyweightRecord(bwr);
//        deleteBodyweightRecord(bwr);
        //Testing the Class Lists
        DatabaseController dc = new DatabaseController();
        LinkedList<Exercise> exList = dc.getExerciseList();
        LinkedList<ExerciseRecord> exrList = dc.getExerciseRecordList();
        LinkedList<CalorieRecord> crList = dc.getCalorieRecordList();
        LinkedList<BodyWeightRecord> bwrList = dc.getBodyweightRecordList();
        System.out.println(exList);
        System.out.println(exrList);
        System.out.println(crList);
        System.out.println(bwrList);
    }

    //Given a JSON database write to file
    private static void writeJSON(JSONObject data) {
        String fp = "data.json";
        try {
            FileWriter writer = new FileWriter(fp);
            writer.write(data.toJSONString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
