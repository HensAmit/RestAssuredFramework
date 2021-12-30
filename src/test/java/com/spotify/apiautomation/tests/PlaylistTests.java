package com.spotify.apiautomation.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PlaylistTests {
    String accessToken = "BQB1ffPE2b0EdjzY6zasAQun3T6Ls5FukwUJiVfzOZ3btkkR_g_uTuqDFYtvkhaEL3UBPWlxRM_JrKFMx6etpAJ7PFO6i2PjIYdCUUDbSb21AVQwY-wsgHxrh3mDxVBE0qH9xtgnNB4w9N0zZEOd4Z1l_J0JRDk-nwfnNYzCZQbphWYcR7aaHgjfRcBJvUx1lFcKuhNBmjXXdj3G9mlP0nr94H0y_pk8Z684Ft4VcKdH_WZH";
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .addHeader("Authorization", "Bearer "+accessToken)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void shouldBeAbleToCreateAPlaylist(){
        String payload = "{\n" +
                "  \"name\": \"My Playlist 2\",\n" +
                "  \"description\": \"Amits 2nd playlist\",\n" +
                "  \"public\": false\n" +
                "}";
        given(requestSpecification)
                .body(payload).
        when()
                .post("/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists").
        then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(201)
                .body("name", equalTo("My Playlist 2"),
                        "description", equalTo("Amits 2nd playlist"),
                        "public", equalTo(false));
    }

    @Test
    public void shouldBeAbleToGetAPlaylist(){
        given(requestSpecification).
        when()
                .get("/playlists/0fYUt3c4IfZ52WkKGPTa2z").
        then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200)
                .body("name", equalTo("My Playlist 1"),
                        "description", equalTo("Amits 1st playlist updated"),
                        "public", equalTo(false));
    }

    @Test
    public void shouldBeAbleToUpdateAPlaylist(){
        String payload = "{\n" +
                "  \"name\": \"My Playlist 2\",\n" +
                "  \"description\": \"Amits 2nd playlist\",\n" +
                "  \"public\": false\n" +
                "}";
        given(requestSpecification)
                .body(payload).
        when()
                .put("/playlists/1IxlpwMnTLzSJICji9OG1r").
        then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithoutName(){
        String payload = "{\n" +
                "  \"name\": \"\",\n" +
                "  \"description\": \"Playlist without name\",\n" +
                "  \"public\": false\n" +
                "}";
        given(requestSpecification)
                .body(payload).
        when()
                .post("/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists").
        then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(400)
                .body("error.status", equalTo(400),
                        "error.message", equalTo("Missing required field: name"));
    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithExpiredToken(){
        String payload = "{\n" +
                "  \"name\": \"new playlist\",\n" +
                "  \"description\": \"Playlist without token\",\n" +
                "  \"public\": false\n" +
                "}";
        given()
                .baseUri("https://api.spotify.com")
                .basePath("/v1")
                .header("Authorization", "Bearer 12345")
                .contentType(ContentType.JSON)
                .log().all()
                .body(payload).
        when()
                .post("/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists").
        then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(401)
                .body("error.status", equalTo(401),
                        "error.message", equalTo("Invalid access token"));
    }
}
