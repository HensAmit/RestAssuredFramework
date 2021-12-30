package com.spotify.apiautomation.tests;

import com.spotify.apiautomation.pojo.Error;
import com.spotify.apiautomation.pojo.Playlist;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static com.spotify.apiautomation.api.SpecBuilder.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

public class PlaylistTests {

    @Test
    public void shouldBeAbleToCreateAPlaylist(){
        Playlist requestPlaylist = new Playlist()
                        .setName("Playlist 3")
                        .setDescription("my 3rd playlist")
                        .setPublic(false);

        Playlist responsePlaylist = given(getRequestSpec())
                .body(requestPlaylist).
        when()
                .post("/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists").
        then()
                .spec(getResponseSpec())
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

        Playlist responsePlaylist = given(getRequestSpec()).
        when()
                .get("/playlists/0fYUt3c4IfZ52WkKGPTa2z").
        then()
                .spec(getResponseSpec())
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

        given(getRequestSpec())
                .body(requestPlaylist).
        when()
                .put("/playlists/1IxlpwMnTLzSJICji9OG1r").
        then()
                .spec(getResponseSpec())
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithoutName(){
        Playlist requestPlaylist = new Playlist()
                        .setName("")
                        .setDescription("Playlist without name")
                        .setPublic(false);

        Error error = given(getRequestSpec())
                .body(requestPlaylist).
        when()
                .post("/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists").
        then()
                .spec(getResponseSpec())
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
                .spec(getResponseSpec())
                .assertThat()
                .statusCode(401)
                .extract().response()
                .as(Error.class);

        assertThat(error.getErrorInner().getStatus(), equalTo(401));
        assertThat(error.getErrorInner().getMessage(), equalTo("Invalid access token"));
    }
}
