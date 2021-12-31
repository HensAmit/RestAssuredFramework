package com.spotify.apiautomation.api;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.apiautomation.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class TokenManager {
    public static String renewToken(){
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "refresh_token");
        formParams.put("refresh_token", "AQB9N2Nlcd8JqSfB-TW-4m9-feqbiGTzXShfdeRHvCJq5FHkHtgm_5byYvhU042lvKzGWIdSig0wZ15cX5wLLA1u_4KKtB7QIojYY-UtvllUdKp5tHVO8WiQd1ccJC_hWB0");
        formParams.put("client_id", "ddd990dd0dd148a0adc82ffc8f5a03fb");
        formParams.put("client_secret", "525e92fb627943158088933965bb3de3");

        Response response = given()
                .baseUri("https://accounts.spotify.com")
                .contentType(ContentType.URLENC)
                .formParams(formParams).
        when()
                .post("/api/token").
        then()
                .spec(getResponseSpec())
                .extract().response();

        if(response.statusCode() != 200){
            throw new RuntimeException("ABORT!!! Renew token failed!!");
        }

        return response.path("access_token");
    }
}
