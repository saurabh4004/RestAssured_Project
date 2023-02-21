package com.tests;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
public class PostRequest {

    //Passing Json body as String is not recommanded
    //easy to copy paste --> Can be used to quickly check behaviour
    //Not recommanded for larger json or dynamic json
    @Test
    public void sendPostRequest(){
        String reqBody="{\n" +
                "      \"id\": 17,\n" +
                "      \"first_name\": \"Sebastian\",\n" +
                "      \"last_name\": \"Eschweiler\",\n" +
                "      \"email\": \"sebastian@codingthesmartway.com\"\n" +
                "    }";
        Response response= given()
                .header("Content-Type","application/json")
                //.header("Content-Type", ContentType.JSON)
        .body(reqBody).log().all()
                .post("http://localhost:3000/employees");
        response.prettyPeek();
        System.out.println(response.getStatusCode());
    }

    @Test
    public void test(){
        //Pass it from external file
        //1.You cannot get the request content from the file and print it on console
        //2. Use this for static data
        Response response=given()
                .header("Content-Type",ContentType.JSON)
                .body(new File(System.getProperty("user.dir")+"/test.json")).log().all()
                .post("http://localhost:3000/employees");
        response.prettyPeek();
    }

    @Test
    public void test1() throws IOException {
        //Read request from an external file and convert to string
        //1.Log the request body into the console
        //2. Change few parameters in the request
        //3. Not suitable for request having lot of dynamic parameters
        String reqBody = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/test.json")));
        String replace = reqBody.replace("20",String.valueOf(new Faker().number().numberBetween(50,1000)))
                .replace("fname",new Faker().name().firstName());
        Response response=given()
                .header("Content-Type",ContentType.JSON)
                .body(replace).log().all()
                .post("http://localhost:3000/employees");
        response.prettyPeek();
    }

    @Test
    public void test2(){
        //using map and list from java
        // {} --> Map Interface
        // [] --> List
        //serialisers --> converts your language objects --> byte stream --> json
        //Linkedhasmap --> Use it for sequential order to print json
        Map<String,Object> obj=new LinkedHashMap<>();
        obj.put("id",22);
        obj.put("fname","Saurabh");
        obj.put("lname","Gabhane");
        obj.put("email","saurabh@gmail.com");

        Response response=given()
        .header("Content-Type",ContentType.JSON)
        .log()
        .all()
        .body(obj)
        .post("http://localhost:3000/employees");
        response.prettyPeek();
    }
}
