package com.spotify.apiautomation.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

    public static RequestSpecification getRequestSpec(){
        return new RequestSpecBuilder()
//                .setBaseUri("https://api.spotify.com")
                .setBaseUri(System.getProperty("BASE_URI"))
                .setBasePath("/v1")
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getAccountRequestSpec(){
        return new RequestSpecBuilder()
//                .setBaseUri("https://accounts.spotify.com")
                .setBaseUri(System.getProperty("ACCOUNT_BASE_URI"))
                .setContentType(ContentType.URLENC)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec(){
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}
