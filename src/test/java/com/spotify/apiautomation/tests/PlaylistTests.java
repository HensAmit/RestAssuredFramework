package com.spotify.apiautomation.tests;

import com.spotify.apiautomation.api.applicationAPI.PlaylistAPI;
import com.spotify.apiautomation.pojo.Error;
import com.spotify.apiautomation.pojo.Playlist;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
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

        Response response = PlaylistAPI.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(201));

        Playlist responsePlaylist = response.as(Playlist.class);

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

        Response response = PlaylistAPI.get("0fYUt3c4IfZ52WkKGPTa2z");
        assertThat(response.statusCode(), equalTo(200));

        Playlist responsePlaylist = response.as(Playlist.class);

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

        Response response = PlaylistAPI.update(requestPlaylist, "1IxlpwMnTLzSJICji9OG1r");
        assertThat(response.statusCode(), equalTo(200));
    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithoutName(){
        Playlist requestPlaylist = new Playlist()
                        .setName("")
                        .setDescription("Playlist without name")
                        .setPublic(false);

        Response response = PlaylistAPI.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(400));

        Error error = response.as(Error.class);

        assertThat(error.getErrorInner().getStatus(), equalTo(400));
        assertThat(error.getErrorInner().getMessage(), equalTo("Missing required field: name"));
    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithExpiredToken(){
        String invalidToken = "12345";
        Playlist requestPlaylist = new Playlist()
                        .setName("dummy")
                        .setDescription("Playlist without token")
                        .setPublic(false);

        Response response = PlaylistAPI.post(requestPlaylist, invalidToken);
        assertThat(response.statusCode(), equalTo(401));

        Error error = response.as(Error.class);

        assertThat(error.getErrorInner().getStatus(), equalTo(401));
        assertThat(error.getErrorInner().getMessage(), equalTo("Invalid access token"));
    }
}
