package com.spotify.apiautomation.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {
    static String accessToken = "BQAyQIb_rjRUDfYEs0asvp1bxnCanQVIn40G36P3SIGlIaBKz5dxKvBQ9dsN3sy98jBH8G-owg1_HYk3EE2g_BSyBTl5UImpxn8OwtR6m-BlSEykwR5tpNXUdA2vGBszn1phZ7IwJVPLHSIrK2uF7tQI8cPBMFymkh-LV3YxRCdS01x3Swr6eHVXrEJvrhYJv-c_ydX_2dRjz8CXLUn68uQ9mG6t9T43a1W2BOlZmJs3zt8k";
    public static RequestSpecification getRequestSpec(){
        return new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .addHeader("Authorization", "Bearer "+accessToken)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getResponseSpec(){
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}
