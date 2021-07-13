package ru.netology.web.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import lombok.val;
import net.minidev.json.JSONObject;

import java.sql.SQLException;
import java.util.*;

import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.hasItems;
import static ru.netology.web.SQL.SQLQuery.getCode;


public class TestRestQuery {
    public static void loginUser(String user, String pass) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("login", user);
        jsonObject.put("password", pass);
        RequestSpecification request = RestAssured.given();
        request.contentType(ContentType.JSON);
        request.body(jsonObject.toJSONString());
        request.baseUri("http://localhost:9999/");
        Response response = request.post("api/auth");
        response.then().statusCode(200);
    }

    public static String verificationUser(String user) throws SQLException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("login", user);
        jsonObject.put("code", getCode(user));
        RequestSpecification request = RestAssured.given();
        request.baseUri("http://localhost:9999/");
        request.contentType(ContentType.JSON);
        request.body(jsonObject.toJSONString());
        Response response = request.post("api/auth/verification");
        response.then().statusCode(200);
        String token =  response.then().extract().path("token");
        return token;
    }

    public static String getCardBalance(String token, String card) {
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token);
        request.baseUri("http://localhost:9999/");
        request.contentType(ContentType.JSON);
        Response response = request.get("api/cards");
        response.then().statusCode(200);
        String responseString = response.asString();
        List<String> balance = from(responseString).getList("findAll {it.number.equalsIgnoreCase(\"**** **** **** " + card +"\")}.balance");
        return balance.toString().replace("[", "").replace("]", "");
    }
    public static void transferMany(String token, String amount, String fromCard, String toCard) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("from", fromCard);
        jsonObject.put("to", toCard);
        jsonObject.put("amount", amount);
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer " + token);
        request.baseUri("http://localhost:9999/");
        request.contentType(ContentType.JSON);
        request.body(jsonObject.toJSONString());
        Response response = request.post("api/transfer");
        response.then().statusCode(200);
    }
}
