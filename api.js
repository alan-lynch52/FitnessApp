//filesystem
const fs = require('fs');
const schema = require('./exercise-schema.js');
const Joi = require('joi');
const database = fs.readFile("database.json", "utf8",(err, data) => {
    if(err) throw err;
    console.log("file read successful");
    console.log(Object.prototype.toString.call(data));

    const express = require("express");
    const app = express();
    let jsonData = JSON.parse(data);
    let userList = jsonData["users"];
    
    //get a user
    app.get("/users/:username/:password",(req,res) =>{
        const user = userList.find((obj) => {
            return String(obj.username) === req.params.username 
                    && String(obj.password) === String(req.params.password);
        });
        if(!user) {
            res.statusMessage = "Username or password incorrect";
            return res.status(404).send();
        }
      res.send(user);
    });
    //get a record list of a given type
    app.get("/users/:username/:password/:recordList", (req, res) =>{
        const user = userList.find((obj) => {
            return String(obj.username) === req.params.username 
                    && String(obj.password) === String(req.params.password);
        });
        if(!user) {
            res.statusMessage = "Username or password incorrect";
            return res.status(404).send();
        }
        //find record list
        const listType = req.params.recordList;
        const recordList = user[listType];
        if(!recordList){
            res.statusMessage = req.params.recordList + ", Not found";
            return res.status(404).send();
        }
        res.status(200);
        res.send(recordList);
    });
    app.get("/users/:username/:password/:recordList/:date",(req,res)=>{
        console.log(req.params);
        const user = userList.find((obj) => {
            return String(obj.username) === String(req.params.username)
                    && String(obj.password) === String(req.params.password);
        });
        if(!user) {
            res.statusMessage = "Username or password incorrect";
            return res.status(404).send();
        }
        //find record list
        const listType = req.params.recordList;
        const recordList = user[listType];
        if(!recordList){
            res.statusMessage = req.params.recordList + ", Not found";
            return res.status(404).send();
        }
        let recordDateList = [];
        console.log(req.params.date);
        for (let i = 0; i < recordList.length; i++){
            console.log(recordList[i].date);
            if (String(recordList[i].date) === String(req.params.date)){ 
                console.log(recordList[i]);
                recordDateList.push(recordList[i]);}
        }
        if (!recordDateList){
            res.statusMessage = "No records with given date";
            return res.status(404).send();
        }
        res.statusMessage = "record list with specified date";
        res.status(200);res.send(recordDateList);
    });
    app.get("/users/:username/:password/:recordList/exID/:exID",(req,res)=>{
                console.log(req.params);
        const user = userList.find((obj) => {
            return String(obj.username) === String(req.params.username)
                    && String(obj.password) === String(req.params.password);
        });
        if(!user) {
            res.statusMessage = "Username or password incorrect";
            return res.status(404).send();
        }
        //find record list
        const listType = req.params.recordList;
        const recordList = user[listType];
        if(!recordList){
            res.statusMessage = req.params.recordList + ", Not found";
            return res.status(404).send();
        }
        let recordSubList = [];
        console.log(req.params.exID);
        for (let i = 0; i < recordList.length; i++){
            console.log(recordList[i].date);
            if (String(recordList[i].exerciseID) === String(req.params.exID)){ 
                console.log(recordList[i]);
                recordSubList.push(recordList[i]);}
        }
        if (!recordSubList){
            res.statusMessage = "No records with given date";
            return res.status(404).send();
        }

        res.statusMessage = "exID Request success";
        res.status(200).send(recordSubList);
    });
    app.post("/add/user",(req, res) =>{
        let receivedData = "";
        req.on("data", (chunk) =>{
            receivedData += chunk.toString();
        });
        req.on("end",()=>{
            let receivedJson = JSON.parse(receivedData);
            const username = receivedJson.username;
            const password = receivedJson.password;
            console.log(receivedJson);
            console.log(username);
            console.log(password);
            if (!username || !password){
                res.statusMessage = "username or password is empty";
                return res.status(404).end();
            }
            const exists = userList.find((obj) => {
                return String(username) === String(obj.username);
            });
            if (exists){
                console.log(exists);
                res.statusMessage = "User already exists";
                return res.status(400).end();
            }
            //add record lists to json object
            receivedJson["exercises"] = [];
            receivedJson["exerciseRecords"] = [];
            receivedJson["calorieRecords"] = [];
            receivedJson["bodyweightRecords"] = [];
            
            console.log(receivedJson);
            //add new user to user list
            userList.push(receivedJson);
            
            //write to file
            const options = {"encoding":"utf8","mode":0o666,"flag":"w"};
            fs.writeFile("database.json",JSON.stringify(jsonData),options,(err)=> {
                if(err) throw err;
                console.log("added to database");
            });
            
            res.statusMessage = "Succesfully added user to the database";
            res.status(200);
            res.end();
        });

        
    });
    app.put("/update/password",(req,res)=>{
        let dataReceived = "";
        req.on("data",(chunk)=>{
            dataReceived += chunk.toString();
        });
        req.on("end",()=>{
            let receivedJson = JSON.parse(dataReceived);
            //check username & old password
            let user = userList.find((obj) =>{
                return String(obj.username)===String(receivedJson.username)
                && String(obj.password)===String(receivedJson.oldPassword);
            });
            if (!user){
                res.statusMessage = "Username and password not found";
                return res.status(404).end();
            }
            //check new password and confirmed password
            if (String(dataReceived.newPassword) !== String(dataReceived.confirmedPassword)){
                res.statusMessage = "Old password and new password do not match";
                return res.status(404).end();
            }
            //change password for user
            user.password = receivedJson.newPassword;
            //write to db
                        //write to file
            const options = {"encoding":"utf8","mode":0o666,"flag":"w"};
            fs.writeFile("database.json",JSON.stringify(jsonData),options,(err)=> {
                if(err) throw err;
                console.log("added to database");
            });
            res.statusMessage = "Successfully changed password";
            res.status(200);
            res.end();
        });
    });
    app.delete("/remove/user/",(req,res)=>{
        let dataReceived = "";
        req.on("data",(chunk)=>{
            dataReceived+=chunk.toString();
        });
        req.on("end",()=>{
            let receivedJson = JSON.parse(dataReceived);
            //check username and password
            let userIndex = userList.findIndex((obj)=>{
                return String(obj.username)===String(receivedJson.username)
                        && String(obj.password)===String(receivedJson.password);
            });
            if (userIndex === -1){
                res.statusMessage= "Username or password incorrect";
                return res.status(404).end();
            }
            //remove index of user, 1 item
            userList.splice(userIndex, 1);
            //write to file
            fs.unlink("database.json",(err)=> {
                if (err) throw err;
                const options = {"encoding":"utf8","mode":0o666,"flag":"w"};
                fs.writeFile("database.json",JSON.stringify(jsonData),options,(err)=> {
                    if(err) throw err;
                    console.log("added to database");
                    res.statusMessage="Successfully removed user";
                    res.status(200);
                    return res.end();
                });
            });
            res.statusMessage = "failed to remove user";
            res.status(404);
            res.end();
        });
    });
    app.post("/login",(req,res)=>{
        console.log("Login Request");
        let dataReceived = "";
        req.on("data", (chunk)=>{
            dataReceived += chunk.toString();
        });
        req.on("end",()=>{
            const receivedJson = JSON.parse(dataReceived);
            console.log(receivedJson);
            const user = userList.find((obj)=>{
                return String(receivedJson.username)===String(obj.username) 
                        && String(receivedJson.password)===String(obj.password);
            });
            if (!user){
                res.statusMessage = "Username or password incorrect";
                res.status(404);
                res.end();
                return;
            }
            res.status(200);
            res.end();
        });
    });
    app.post("/add/",(req,res) => {
        let dataReceived = "";
        req.on("data", (chunk) =>{
            dataReceived += chunk.toString();
        });
        req.on("end", () =>{
            let receivedJson = JSON.parse(dataReceived);
            console.log(receivedJson);
            //console.log(dataJson.recordType);
            //find user
            let user = userList.find((obj) => {
                return String(obj.username) === String(receivedJson.username) 
                    && String(obj.password) === String(receivedJson.password);
            });
            if(!user) {
                res.statusMessage = "Username or password incorrect";
                res.status(404);                
                res.end();
                return;
            }
            
            //get list of record type
            let recordList = "";
            let schemaType = "";
            switch(receivedJson.recordType){
                case "Exercise": 
                    recordList = user.exercises;
                    schemaType = schema.exerciseSchema;
                    //const result = Joi.validate(receivedJson, schema.exerciseSchema);
                    break;
                case "ExerciseRecord": 
                    recordList = user.exerciseRecords;
                    schemaType = schema.exerciseRecordSchema;
                    //Joi.validate(receivedJson, schema.exerciseRecordSchema);
                    break;
                case "CalorieRecord": 
                    recordList = user.calorieRecords;
                    schemaType = schema.calorieRecordSchema;
                    //Joi.validate(receivedJson, schema.calorieRecordSchema);
                    break;
                case "BodyweightRecord": 
                    recordList = user.bodyweightRecords;
                    schemaType = schema.bodyweightRecordSchema;
                    //Joi.validate(receivedJson, schema.bodyweightRecordSchema);
                    break;     
            }
            if(!recordList) {
                res.statusMessage = "recordType invalid";
                res.status(404);
                res.end();
                return;
            }
            
            //remove spurious metadata from json object
            delete receivedJson.username;
            delete receivedJson.password;
            delete receivedJson.recordType;
            
            //check received json against schema
            const validJson = schemaType.validate(receivedJson); 
            if(validJson.error) {
                res.statusMessage = "Object does not match the schema";
                res.status(404);
                res.end();
                return;
            }
            //check if id already exists
            console.log(receivedJson.id);
            const exists = recordList.find((obj) => {return String(obj.id) === String(receivedJson.id);});;

            if(exists) { 
                res.statusMessage="ID already exists";
                res.status(404);
                res.end();
                return;
            }
            console.log("Received Object Id");
            console.log("Object from Database");
                        
            //add record to list
            recordList.push(receivedJson);
            
            //write to file
            const options = {"encoding":"utf8","mode":0o666,"flag":"w"};
            fs.writeFile("database.json",JSON.stringify(jsonData),options,(err)=> {
                if(err) throw err;
                console.log("added to database");
            });
            
            res.statusMessage= "successfully added to the database";
            res.end();
        });
    });
    
    app.put("/update/",(req,res) => {
        let dataReceived = "";
        req.on("data", (chunk) =>{
            dataReceived += chunk.toString();
        });
        req.on("end", () =>{
            let receivedJson = JSON.parse(dataReceived);
            console.log(receivedJson);
            //console.log(dataJson.recordType);
            //find user
            let user = userList.find((obj) => {
                return String(obj.username) === String(receivedJson.username)
                    && String(obj.password) === String(receivedJson.password);
            });
            if(!user) {
                res.statusMessage = "Username not found";
                res.status(404);                
                res.end();
                return;
            }
            
            //get list of record type
            let recordList = "";
            let schemaType = "";
            switch(receivedJson.recordType){
                case "Exercise": 
                    recordList = user.exercises;
                    schemaType = schema.exerciseSchema;
                    console.log("Exercise");
                    //const result = Joi.validate(receivedJson, schema.exerciseSchema);
                    break;
                case "ExerciseRecord": 
                    recordList = user.exerciseRecords;
                    schemaType = schema.exerciseRecordSchema;
                    //Joi.validate(receivedJson, schema.exerciseRecordSchema);
                    break;
                case "CalorieRecord": 
                    recordList = user.calorieRecords;
                    schemaType = schema.calorieRecordSchema;
                    //Joi.validate(receivedJson, schema.calorieRecordSchema);
                    break;
                case "BodyweightRecord": 
                    recordList = user.bodyweightRecords;
                    schemaType = schema.bodyweightRecordSchema;
                    //Joi.validate(receivedJson, schema.bodyweightRecordSchema);
                    break;     
            }
            if(!recordList) {
                res.statusMessage = "recordType invalid";
                res.status(404);
                res.end();
                return;
            }
            
            //remove spurious metadata from json object
            delete receivedJson.username;
            delete receivedJson.password;
            delete receivedJson.recordType;
            
            //check received json against schema
            const validJson = schemaType.validate(receivedJson);
            
            if(validJson.error) {
                res.statusMessage = validJson.error;
                res.status(404);
                res.end();
                return;
            }
            //check if id already exists
            console.log(receivedJson.id);
            const recordIndex = recordList.findIndex((obj) => {return obj.id === receivedJson.id;});;
            
            if(recordIndex === -1) { 
                res.statusMessage="Record ID Not found";
                res.status(404);
                res.end();
                return;
            }
            console.log("Received Object Id");
            console.log("Object from Database");
                        
            //add record to list
            //recordList.push(receivedJson);
            
            recordList[recordIndex] = receivedJson;
            
            //write to file
            const options = {"encoding":"utf8","mode":0o666,"flag":"w"};
            fs.writeFile("database.json",JSON.stringify(jsonData),options,(err)=> {
                if(err) throw err;
                console.log("added to database");
            });
            res.status(200);
            res.statusMessage= "successfully added to the database";
            res.end();
        });
    });
    
    app.delete("/remove/", (req, res) =>{
        let receivedData = "";
        req.on("data",(chunk) =>{
            receivedData += chunk;
        });
        req.on("end",() => {
            //make json object of received data
            let receivedJson = JSON.parse(receivedData);
            console.log(receivedJson);
            //check if username and password are correct
            let user = userList.find((obj) => {
                return String(obj.username) === String(receivedJson.username)
                    && String(obj.password) === String(receivedJson.password);
            });
            if(!user){
                res.status(404);
                res.statusMessage = "Username or password incorrect";
                res.end();
                return;
            }
            //find what type of file is to be deleted
            
            let recordList = "";
            let schemaType = "";
            switch(receivedJson.recordType){
                case "Exercise": 
                    recordList = user.exercises;
                    schemaType = schema.exerciseSchema;
                    //const result = Joi.validate(receivedJson, schema.exerciseSchema);
                    break;
                case "ExerciseRecord": 
                    recordList = user.exerciseRecords;
                    schemaType = schema.exerciseRecordSchema;
                    //Joi.validate(receivedJson, schema.exerciseRecordSchema);
                    break;
                case "CalorieRecord": 
                    recordList = user.calorieRecords;
                    schemaType = schema.calorieRecordSchema;
                    //Joi.validate(receivedJson, schema.calorieRecordSchema);
                    break;
                case "BodyweightRecord": 
                    recordList = user.bodyweightRecords;
                    schemaType = schema.bodyweightRecordSchema;
                    //Joi.validate(receivedJson, schema.bodyweightRecordSchema);
                    break;     
            }
            if(!recordList) {
                res.statusMessage = "recordType invalid";
                res.status(404);
                res.end();
                return;
            }
            
            //delete spurious keys
            delete receivedJson.username;
            delete receivedJson.password;
            delete receivedJson.recordType;
            
            //check for valid received object against schema
            const valid = schemaType.validate(receivedJson);
            if (valid.error){
                res.status(404);
                res.statusMessage = "Object does not meet schema requirements";
                res.end();
                return;
            }
            //if requested object exists in list, remove it
            let removed = "";
            for(let i = 0; i < recordList.length; i++){
                if (recordList[i].id === receivedJson.id){
                    //write to file
                    recordList.splice(i, 1);
                    console.log(recordList);
                    fs.unlink("database.json",(err)=> {
                       if (err) throw err;
                    });
                    const options = {"encoding":"utf8","mode":0o666,"flag":"w"};
                    fs.writeFile("database.json",JSON.stringify(jsonData),options,(err)=> {
                        if(err) throw err;
                        console.log("added to database");
                    });
                    
                    res.status(200);
                    res.statusMessage = "Successfully removed";
                    res.end();
                    return;
                }
            }
            
            res.status(404);
            res.statusMessage = "Record not found";
            res.end();
        });
    });
    app.get("/:username/:password/generate/:recordListType/id", (req,res) => {
        console.log(req.params);
        const user = userList.find((obj) =>{
            return String(req.params.username) === String(obj.username)
                && String(req.params.password) === String(obj.password);
        });
        if (!user){
            res.statusMessage = "Username or password incorrect";
            return res.status(404).send();
        }
        //go through list
        console.log(req.params.recordListType);
        console.log(user[req.params.recordListType]);
        let lastIndex = (user[req.params.recordListType]).length;
        const newID = String(user[req.params.recordListType][lastIndex-1].id + 1);
        console.log(newID);
        //find next unique id value
        //send value
        res.status(200);
        res.statusMessage = "Success";
        res.send(newID);
    });
    app.get("/getID/:username/:password/:name/:recordListType",(req,res)=>{
        let name = (req.params.name).replace("%20"," ");
        const user = userList.find((obj)=>{
            return String(req.params.username)===String(obj.username) 
            && String(req.params.password)===String(obj.password);
        });
        if (!user){
            res.statusMessage = "Username or password incorrect";
            res.status(404).end();
            return;
        }
        const recordList = user[req.params.recordListType];
        if (!recordList){
            res.statusMessage = "Record list not found";
            res.status(404).end();
            return;
        }
        console.log(recordList);
        const obj = recordList.find((obj)=>{
            console.log(obj.name);
            console.log(name);
            return String(obj.name)===String(name);
            
        });
        if (!obj){
            res.statusMessage = "Could not find record with given name";
            res.status(404).end();
            return;
        }
        const id = (obj.id).toString();
        res.status(200);
        res.send(id);
    });
    app.listen(3000);
    console.log("Listening on PORT:3000");
});
