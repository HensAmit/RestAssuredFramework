package com.spotify.apiautomation.tests;

import com.spotify.apiautomation.pojo.Error;
import com.spotify.apiautomation.pojo.Playlist;
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
import static org.hamcrest.MatcherAssert.*;

public class PlaylistTests {
    String accessToken = "BQDVGUmUhG6D7OTDd_GgjvKwBXiHeApc2EeTtb8mX8yJnbIOgACbLp8sjxJlYst5ok1K-f6jwtilmXWSmY-XXqTUv6thN9Nr5Nt9BKAdiXTE8rKBQ7aNiUphKflWK_qsuDza4SFPCMKvbgxFjWvM95CSuEoQthihCjlUiOpc0Y5j81AzYo2thFyPLKF1q-POXMHc-DeMdNkotMkNBqIcpvBlh_RK2Iq5WCTRh69Yy9b0vhHb";
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
        Playlist requestPlaylist = new Playlist()
                        .setName("Playlist 3")
                        .setDescription("my 3rd playlist")
                        .setPublic(false);

        Playlist responsePlaylist = given(requestSpecification)
                .body(requestPlaylist).
        when()
                .post("/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists").
        then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(201)
                .extract().response()
                .as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void shouldBeAbleToGetAPlaylist(){
        Playlist requestPlaylist = new Playlist()
                        .setName("My Playlist 1")
                        .setDescription("Amits 1st playlist updated")
                        .setPublic(false);

        Playlist responsePlaylist = given(requestSpecification).
        when()
                .get("/playlists/0fYUt3c4IfZ52WkKGPTa2z").
        then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200)
                .extract().response()
                .as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.getPublic(), equalTo(requestPlaylist.getPublic()));
    }

    @Test
    public void shouldBeAbleToUpdateAPlaylist(){
        Playlist requestPlaylist = new Playlist()
                        .setName("My Playlist 2")
                        .setDescription("Amits 2nd playlist")
                        .setPublic(false);

        given(requestSpecification)
                .body(requestPlaylist).
        when()
                .put("/playlists/1IxlpwMnTLzSJICji9OG1r").
        then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithoutName(){
        Playlist requestPlaylist = new Playlist()
                        .setName("")
                        .setDescription("Playlist without name")
                        .setPublic(false);

        Error error = given(requestSpecification)
                .body(requestPlaylist).
        when()
                .post("/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists").
        then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(400)
                .extract().response()
                .as(Error.class);

        assertThat(error.getErrorInner().getStatus(), equalTo(400));
        assertThat(error.getErrorInner().getMessage(), equalTo("Missing required field: name"));
    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithExpiredToken(){
        Playlist requestPlaylist = new Playlist()
                        .setName("dummy")
                        .setDescription("Playlist without token")
                        .setPublic(false);

        Error error = given()
                .baseUri("https://api.spotify.com")
                .basePath("/v1")
                .header("Authorization", "Bearer 12345")
                .contentType(ContentType.JSON)
                .log().all()
                .body(requestPlaylist).
        when()
                .post("/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists").
        then()
                .spec(responseSpecification)
                .assertThat()
                .statusCode(401)
                .extract().response()
                .as(Error.class);

        assertThat(error.getErrorInner().getStatus(), equalTo(401));
        assertThat(error.getErrorInner().getMessage(), equalTo("Invalid access token"));
    }
}
