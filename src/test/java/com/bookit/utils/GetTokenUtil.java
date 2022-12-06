package com.bookit.utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


public class GetTokenUtil {

    public static String getToken(String email, String password){
        String token = RestAssured.given().accept(ContentType.JSON)
                .and().queryParam("email", email)
                .and().queryParam("password", password)
                .and().get(ConfigurationReader.get("base_url") + "/sign")
                .then().extract().jsonPath().getString("accessToken");

        String realToken = "Bearer "+token;

        return realToken;

    }
}
