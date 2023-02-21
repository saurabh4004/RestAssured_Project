package com.tests;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.*;
public class GetRequest {
    @Test
    public void testGetRequest(){
        // RestAssured support BDD and non BDD way
        // Below method of BDD are Sugarcoated method
        Response response= given ().get ( "http://localhost:3000/employees" );
        System.out.println (response.getContentType () );
        Headers headers=response.headers ();
        for (Header header:headers){
            System.out.println (header.getName () +":"+header.getValue ());
        }
        response.prettyPeek ();
        System.out.println (response.getStatusCode () );
        System.out.println (response.getTime () );
        System.out.println (response.getTime () );
        System.out.println (response.getTimeIn (TimeUnit.SECONDS) );
    }
}
