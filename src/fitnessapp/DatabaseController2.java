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
    
    public static boolean addUserRequest(String username, String password){
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
    public static boolean updatePassword(String username, String oldPassword, 
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
    public static boolean removeUser(String username, String password){
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
    public static boolean deleteRequest(String username, String password, String rType, Object record){
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
    public static void postRequest(String username, String password, String rType, Object record){
        try{
            String urlStr = EC2_HOST+"/add/";

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
            
            int responseCode = con.getResponseCode();
            System.out.println(responseCode);
            System.out.println(con.getResponseMessage());
            con.disconnect();
        }catch(MalformedURLException ex){
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    public static void putRequest(String username, String password, String recordType, Object o){
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
            
            System.out.println(con.getResponseCode());
            System.out.println(con.getResponseMessage());
            con.disconnect();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static Object[] getListRequest(String username, String password, String recordType){
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

                con.disconnect();
                return list;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static int generateID(String username, String password,String recordType){
        try{
            String urlStr = EC2_HOST + "/" + username 
                    + "/" + password + "/generate/" + recordType + "/id";
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
    
    private static JSONObject recordToJson(Object o){
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
    private static Object[] jsonArrayToObjectList(JSONArray arr, String listType){
        //iterate through json
        Object[] objArr = new Object[arr.size()];
        System.out.println(arr.size());
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
        //deleteRequest("user1","pass123","Exercise",new Exercise());
        try{
//            Object[] list = getListRequest("user1","pass123",BODYWEIGHT_RECORD_LIST);
//            Exercise e = new Exercise(7,"Bicep Curl",new Date());
//            postRequest("user1","pass123",EXERCISE, e);
//            for (Object o : list){
//                System.out.println(o);
//            }
//            generateID("user1","pass123",EXERCISE_LIST);
//            addUserRequest("user3","wombo");
//            updatePassword("user1","pass123","pass","pass");
            //addUserRequest("test5","pw101");
            removeUser("test5","pw101");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
