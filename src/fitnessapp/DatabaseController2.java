/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fitnessapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Alan
 */
public class DatabaseController2 {
    private static final String EC2_HOST = 
            "http://ec2-user@ec2-18-221-180-80.us-east-2.compute.amazonaws.com:3000";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT);
    private static final String USER_AGENT = "User-Agent";
    private static final String MOZILLA = "Mozilla/5.0";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_RECORD_TYPE = "recordType";
    
    public static final String EXERCISE = "Exercise";
    public static final String EXERCISE_RECORD = "ExerciseRecord";
    public static final String CALORIE_RECORD = "CalorieRecord";
    public static final String BODYWEIGHT_RECORD = "BodyweightRecord";
    
    public static final String EXERCISE_LIST = "exercises";
    public static final String EXERCISE_RECORD_LIST = "exerciseRecords";
    public static final String CALORIE_RECORD_LIST = "calorieRecords";
    public static final String BODYWEIGHT_RECORD_LIST = "bodyweightRecords";
    
    private String username;
    private String password;
    /**
     * @param username  -   the user to be created
     * @param password  -   the users password
     */
    public boolean addUser(String username, String password){
        try{
            String urlStr = EC2_HOST + "/add/user";
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty(USER_AGENT, MOZILLA);
            con.setDoOutput(true);
            
            OutputStream os = con.getOutputStream();
            JSONObject user = new JSONObject();
            user.put("username", username);
            user.put("password", password);
            
            byte[] userBytes = user.toString().getBytes(StandardCharsets.UTF_8);
            os.write(userBytes);
            os.flush();
            os.close();
            System.out.println(con.getResponseMessage());
            if (con.getResponseCode() == 200){
                
                return true;
            }
            
            con.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * @param username  the user
     * @param password  the user's password
     */
    public boolean login(String username,String password){
        try{
            String urlStr = EC2_HOST+"/login";
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty(USER_AGENT, MOZILLA);
            con.setDoOutput(true);
            
            OutputStream os = con.getOutputStream();
            JSONObject user = new JSONObject();
            user.put(KEY_USERNAME, username);
            user.put(KEY_PASSWORD, password);
            System.out.println(user);
            os.write(user.toJSONString().getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
            System.out.println(con.getResponseMessage());
            System.out.println(con.getResponseCode());
            if (con.getResponseCode() == 400 || con.getResponseCode() == 404){
                
                con.disconnect();
                return false;
            }
            con.disconnect();
            this.username = username;
            this.password = password;
            return true;
        } catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * @param username  -   the user
     * @param oldPassword - the users original password
     * @param newPassword   - the users new password
     * @param confirmedPassword - the users new password confirmation
     */
    public boolean updatePassword(String username, String oldPassword, 
            String newPassword, String confirmedPassword){
        //make connection
        //make output stream
        try{
            if (!newPassword.matches(confirmedPassword))return false;
            if (newPassword.matches(oldPassword))return false;
            String urlStr = EC2_HOST + "/update/password";
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty(USER_AGENT, MOZILLA);
            con.setDoOutput(true);
            
            OutputStream os = con.getOutputStream();
            JSONObject obj = new JSONObject();
            obj.put(KEY_USERNAME,username);
            obj.put("oldPassword",oldPassword);
            obj.put("newPassword",newPassword);
            obj.put("confirmedPassword",confirmedPassword);
            
            os.write(obj.toJSONString().getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
            
            System.out.println(con.getResponseMessage());
            System.out.println(con.getResponseCode());
            if (con.getResponseCode() == 400 || con.getResponseCode() == 404){
                con.disconnect();
                return false;
            }
            else{
                con.disconnect();
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * @param username  -   the user to be removed
     * @param password  -   the password of the user
     */
    public boolean removeUser(String username, String password){
        try{
            String urlStr = EC2_HOST+"/remove/user/";
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty(USER_AGENT, MOZILLA);
            con.setDoOutput(true);
            
            OutputStream os = con.getOutputStream();
            
            JSONObject obj = new JSONObject();
            obj.put(KEY_USERNAME,username);
            obj.put(KEY_PASSWORD,password);
            
            os.write(obj.toJSONString().getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
            
            System.out.println(con.getResponseMessage());
            System.out.println(con.getResponseCode());
            if (con.getResponseCode() == 400 || con.getResponseCode() == 404){
                con.disconnect();
                return false;
            }
            else {
                con.disconnect();
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * @param username  -   the user
     * @param password  -   the users password
     * @param rType     -   the record type to be deleted
     * @param record    -   the record to be deleted
     */
    public boolean deleteRecord(String rType, Object record){
        try{
            String urlStr = EC2_HOST+"/remove/";
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("DELETE");
            con.setRequestProperty(USER_AGENT, MOZILLA);
            con.setDoOutput(true);
            
            OutputStream os = con.getOutputStream();
            
            JSONObject object = recordToJson(record);
            if (object == null){
                System.out.println("Object null");
                return false;
            }
            object.put(KEY_USERNAME, username);
            object.put(KEY_PASSWORD, password);
            object.put(KEY_RECORD_TYPE, rType);
            
            os.write(object.toJSONString().getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
            
            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());
            con.disconnect();
        }
        catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    /**
    * @param username   -   the user
    * @param password   -   the users password
    * @param rType      -   the record type to be added
    * @param record     -   the record to be added
    */
    public boolean addRecord(String rType, Object record){
        System.out.println("addRecord");
        try{
            String urlStr = EC2_HOST+"/add/";
            System.out.println(urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty(USER_AGENT, MOZILLA);
            con.setDoOutput(true);
            
            OutputStream os = con.getOutputStream();
            
            JSONObject json = recordToJson(record);
            json.put(KEY_USERNAME, username);
            json.put(KEY_PASSWORD, password);
            json.put(KEY_RECORD_TYPE, rType);
            

            //send byte version of jsonobject through output stream
            //use utf8 encoding
            os.write(json.toJSONString().getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
            
            System.out.println(con.getResponseMessage());
            
            if (con.getResponseCode() == 400 || con.getResponseCode() == 404){
                System.out.println(con.getResponseMessage());
                con.disconnect();
                return false;
            }
            con.disconnect();
            return true;
        }catch(MalformedURLException ex){
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    /**
     * @param username  -   String - the user 
     * @param password  -   String - users password
     * @param recordType-   String - the type of record
     * @param o         -   The object to be added
     */
    public boolean editRecord(String recordType, Object o){
        try {
            String urlStr = EC2_HOST+"/update/";
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("PUT");
            con.setRequestProperty(USER_AGENT, MOZILLA);
            con.setDoOutput(true);
            
            OutputStream os = con.getOutputStream();
            
            JSONObject json = recordToJson(o);
            json.put(KEY_USERNAME, username);
            json.put(KEY_PASSWORD, password);
            json.put(KEY_RECORD_TYPE, recordType);
            
            os.write(json.toJSONString().getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
            
            if (con.getResponseCode() == 400 || con.getResponseCode()== 404){
                con.disconnect();
                System.out.println(con.getResponseMessage());
                return false;
            }
            con.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * @param username  -   the user
     * @param password  -   the users password
     * @param recordType-   the requested record type
     */
    private Object[] getRecordList(String recordType){
        try{
            String urlStr = EC2_HOST+"/users/"+username+"/"+password+"/"+recordType;
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty(USER_AGENT,MOZILLA);
            con.setDoInput(true);
            
            String line = "";
            String data = "";
            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());
            if (con.getResponseCode() == 400 || con.getResponseCode() == 404){
                return null;
            }
            else{
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while((line = br.readLine()) != null){
                    data += line;
                }
                JSONParser parser = new JSONParser();
                JSONArray o = (JSONArray)parser.parse(data);
                Object[] list = jsonArrayToObjectList(o, recordType);
                for (Object obj : list){
                    System.out.println(obj);
                }
                con.disconnect();
                return list;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private Object[] getRecordList(String recordType, int exID){
                try{
            String urlStr = EC2_HOST+"/users/"+username+"/"+password+"/"+recordType+"/exID/"+exID;
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty(USER_AGENT,MOZILLA);
            con.setDoInput(true);
            
            String line = "";
            String data = "";
            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());
            if (con.getResponseCode() == 400 || con.getResponseCode() == 404){
                return null;
            }
            else{
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while((line = br.readLine()) != null){
                    data += line;
                }
                JSONParser parser = new JSONParser();
                JSONArray o = (JSONArray)parser.parse(data);
                Object[] list = jsonArrayToObjectList(o, recordType);

                con.disconnect();
                return list;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private Object[] getRecordList(String recordType, Date d){
                try{
            String urlStr = EC2_HOST+"/users/"+username+"/"+password+"/"+recordType+"/"+SDF.format(d);
            System.out.println(urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty(USER_AGENT,MOZILLA);
            con.setDoInput(true);
            
            String line = "";
            String data = "";
            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());
            if (con.getResponseCode() == 400 || con.getResponseCode() == 404){
                return null;
            }
            else{
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while((line = br.readLine()) != null){
                    data += line;
                }
                JSONParser parser = new JSONParser();
                JSONArray o = (JSONArray)parser.parse(data);
                Object[] list = jsonArrayToObjectList(o, recordType);

                con.disconnect();
                return list;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @param username  -   the user
     * @param password  -   the user's password
     * @param recordListType-   the record type to generate for
     */
    public int generateID(String recordListType){
        try{
            String urlStr = EC2_HOST + "/" + username 
                    + "/" + password + "/generate/" + recordListType + "/id";
            System.out.println(urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty(USER_AGENT, MOZILLA);
            con.setDoInput(true);
            if (con.getResponseCode() == 400 || con.getResponseCode() == 404){
                System.out.println(con.getResponseCode());
                System.out.println(con.getResponseMessage());
                return -1;
            }
            else {
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = "";
                String data = "";
                while((line = br.readLine())!=null){
                    data+=line;
                }
                System.out.println(data);
                int newID = Integer.parseInt(data);
                return newID;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * @param username the user
     * @param password the user's password
     * @param id the id to be checked
     * @param recordList the list to lookup the id
     */
    public boolean checkID(int id, String recordList){
        try{
            String urlStr = EC2_HOST+"/checkID/"+id+"/"+recordList;
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty(USER_AGENT, MOZILLA);
            con.setDoInput(true);
            System.out.println(con.getResponseCode());
            if (con.getResponseCode() == 400 || con.getResponseCode() == 404){
                con.disconnect();
                return false;
            }
            con.disconnect();
            return true;
        }catch(Exception e){e.printStackTrace();}
        return false;
    }
    public int getID(String name, String recordList){
        try{
            name = name.replace(" ", "%20");
            String urlStr = EC2_HOST+"/getID/"+username+"/"+password+"/"+name+ "/"+recordList;
            System.out.println(urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty(USER_AGENT, MOZILLA);
            con.setDoInput(true);
            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());
            if (con.getResponseCode()==200){
                InputStream is = con.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
                String line = "";
                String data = "";
                while((line = br.readLine())!=null){
                    data += line;
                }
                return Integer.parseInt(data);
            }
            
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }
    private JSONObject recordToJson(Object o){
        JSONObject obj = new JSONObject();
        if (o instanceof Exercise){
            obj.put("id", ((Exercise) o).getId());
            obj.put("name", ((Exercise) o).getName());
            obj.put("date", SDF.format(((Exercise) o).getDateAdded()));
            return obj;
        }
        else if (o instanceof ExerciseRecord){
            obj.put("id", ((ExerciseRecord) o).getId());
            obj.put("exerciseID", ((ExerciseRecord) o).getExerciseId());
            obj.put("weight", ((ExerciseRecord) o).getWeight());
            obj.put("date", SDF.format(((ExerciseRecord) o).getRecordDate()));
            return obj;
        }
        else if (o instanceof CalorieRecord){
            obj.put("id", ((CalorieRecord) o).getId());
            obj.put("calories", ((CalorieRecord) o).getCalories());
            obj.put("date", SDF.format(((CalorieRecord) o).getRecordDate()));
            return obj;
        }
        else if (o instanceof BodyWeightRecord){
            obj.put("id", ((BodyWeightRecord) o).getId());
            obj.put("weight", ((BodyWeightRecord) o).getWeight());
            obj.put("date", SDF.format(((BodyWeightRecord) o).getRecordDate()));
            return obj;
        }
        return null;
    }
    public Exercise[] getExerciseList(){
        System.out.println("getExerciseList");
        Object[] list = getRecordList(EXERCISE_LIST);
        if (list == null) return null;
        Exercise[] exList = new Exercise[list.length];
        for (int i = 0; i < list.length;i++){
            if (list[i] instanceof Exercise){
                exList[i] = (Exercise)list[i];
            }
        }
        return exList;
    }
    public ExerciseRecord[] getExerciseRecordList(){
        System.out.println("getExerciseRecordList");
        Object[] list = getRecordList(EXERCISE_RECORD_LIST);
        if (list == null) return null;
        ExerciseRecord[] exrList = new ExerciseRecord[list.length];
        for (int i = 0; i < list.length;i++){
            if (list[i] instanceof ExerciseRecord){
                exrList[i] = (ExerciseRecord)list[i];
            }
        }
        return exrList;
    }
    public ExerciseRecord[] getExerciseRecordList(int exID){
        System.out.println("getExerciseRecordList");
        Object[] list = getRecordList(EXERCISE_RECORD_LIST, exID);
        if (list == null)   return new ExerciseRecord[0];
        ExerciseRecord[] exrList = new ExerciseRecord[list.length];
        for (int i = 0; i < list.length;i++){
            if (list[i] instanceof ExerciseRecord){
                exrList[i] = (ExerciseRecord)list[i];
            }
        }
        return exrList;
    }
    public CalorieRecord[] getCalorieRecordList(){
        System.out.println("getCalorieRecordList");
        Object[] list = getRecordList(CALORIE_RECORD_LIST);
        if (list == null) return new CalorieRecord[0];
        CalorieRecord[] crList = new CalorieRecord[list.length];
        for (int i = 0; i < list.length;i++){
            if (list[i] instanceof CalorieRecord){
                crList[i] = (CalorieRecord)list[i];
            }
        }
        return crList;
    }
    public CalorieRecord[] getCalorieRecordList(Date d){
        System.out.println("getCalorieRecordList");
        Object[] list = getRecordList(CALORIE_RECORD_LIST, d);
        if (list == null) return new CalorieRecord[0];
        CalorieRecord[] crList = new CalorieRecord[list.length];
        for (int i = 0; i < list.length;i++){
            if (list[i] instanceof CalorieRecord){
                crList[i] = (CalorieRecord)list[i];
            }
        }
        return crList;
    }
    public BodyWeightRecord[] getBodyWeightRecordList(){
        System.out.println("getBodyWeightRecordList");
        Object[] list = getRecordList(BODYWEIGHT_RECORD_LIST);
        if (list == null) return new BodyWeightRecord[0];
        BodyWeightRecord[] bwrList = new BodyWeightRecord[list.length];
        for (int i = 0; i < list.length;i++){
            if (list[i] instanceof BodyWeightRecord){
                bwrList[i] = (BodyWeightRecord)list[i];
            }
        }
        return bwrList;
    }
    private Object[] jsonArrayToObjectList(JSONArray arr, String listType){
        //iterate through json
        Object[] objArr = new Object[arr.size()];
        //System.out.println(arr.size());
        try{
        for (int i = 0; i < arr.size(); i++){
            JSONObject oJson = (JSONObject)arr.get(i);
            int id = Integer.parseInt((oJson.get("id")).toString());
            String dateStr = (oJson.get("date")).toString();
            Date date = SDF.parse(dateStr);
            switch(listType){
                case EXERCISE_LIST:
                    String name = (oJson.get("name")).toString();
                    Exercise e = new Exercise(id,name,date);
                    objArr[i] = e;
                    break;
                case EXERCISE_RECORD_LIST:
                    int exerciseID = Integer.parseInt((oJson.get("exerciseID")).toString());
                    double weight = Double.parseDouble((oJson.get("weight")).toString());
                    ExerciseRecord er = new ExerciseRecord(id,exerciseID,weight,date);
                    objArr[i] = er;
                    break;
                case CALORIE_RECORD_LIST:
                    int calories = Integer.parseInt((oJson.get("calories")).toString());
                    CalorieRecord cr = new CalorieRecord(id, calories, date);
                    objArr[i] = cr;
                    break;
                case BODYWEIGHT_RECORD_LIST:
                    double bWeight = Double.parseDouble((oJson.get("weight")).toString());
                    BodyWeightRecord bwr = new BodyWeightRecord(id, bWeight, date);
                    objArr[i] = bwr;
                    break;
            }
        }
        return objArr;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    public static void main(String[] args){
        try{
            DatabaseController2 dc2 = new DatabaseController2();
//            Object[] list = dc2.getRecordList("user1", "p123", CALORIE_RECORD_LIST, new Date());
//            for (Object o : list){
//                System.out.println(o);
//            }
            dc2.login("user1", "p123");
            dc2.getExerciseRecordList(10);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
