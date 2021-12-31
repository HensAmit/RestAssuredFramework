package com.spotify.apiautomation.api;

import io.restassured.response.Response;

import static com.spotify.apiautomation.api.SpecBuilder.getRequestSpec;
import static com.spotify.apiautomation.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class RestResource {

    public static Response post(String token, String path, Object requestBodyObject){
        return given(getRequestSpec())
                    .header("Authorization", "Bearer "+token)
                    .body(requestBodyObject).
               when()
                    .post(path).
               then()
                    .spec(getResponseSpec())
                    .extract().response();
    }

    public static Response get(String token, String path){
        return given(getRequestSpec())
                    .header("Authorization", "Bearer "+token).
               when()
                    .get(path).
               then()
                    .spec(getResponseSpec())
                    .extract().response();
    }

    public static Response update(String token, String path, Object requestBodyObject){
        return given(getRequestSpec())
                    .header("Authorization", "Bearer "+token)
                    .body(requestBodyObject).
               when()
                    .put(path).
               then()
                    .spec(getResponseSpec())
                    .extract().response();
    }
}
