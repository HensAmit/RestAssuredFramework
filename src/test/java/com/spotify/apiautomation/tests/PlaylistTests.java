package com.spotify.apiautomation.tests;

import com.spotify.apiautomation.api.applicationAPI.PlaylistAPI;
import com.spotify.apiautomation.pojo.Error;
import com.spotify.apiautomation.pojo.Playlist;
import com.spotify.apiautomation.utils.DataLoader;
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
        Playlist requestPlaylist = playListBuilder("Playlist 4", "my 4th playlist", false);

        Response response = PlaylistAPI.post(requestPlaylist);
        assertStatusCode(response.statusCode(), 201);

        Playlist responsePlaylist = response.as(Playlist.class);
        assertPlaylistEqual(responsePlaylist, requestPlaylist);
    }

    @Test
    public void shouldBeAbleToGetAPlaylist(){
        Playlist requestPlaylist = playListBuilder("My Playlist 1", "Amits 1st playlist updated", false);

        Response response = PlaylistAPI.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.statusCode(), 200);

        Playlist responsePlaylist = response.as(Playlist.class);
        assertPlaylistEqual(responsePlaylist, requestPlaylist);
    }

    @Test
    public void shouldBeAbleToUpdateAPlaylist(){
        Playlist requestPlaylist = playListBuilder("My Playlist 2", "Amits 2nd playlist", false);

        Response response = PlaylistAPI.update(requestPlaylist, DataLoader.getInstance().getUpdatePlaylistId());
        assertStatusCode(response.statusCode(), 200);
    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithoutName(){
        Playlist requestPlaylist = playListBuilder("", "Playlist without name", false);

        Response response = PlaylistAPI.post(requestPlaylist);
        assertStatusCode(response.statusCode(), 400);

        Error error = response.as(Error.class);
        assertError(error, 400, "Missing required field: name");
    }

    @Test
    public void shouldNotBeAbleToCreateAPlaylistWithExpiredToken(){
        String invalidToken = "12345";
        Playlist requestPlaylist = playListBuilder("dummy", "Playlist without token", false);

        Response response = PlaylistAPI.post(requestPlaylist, invalidToken);
        assertStatusCode(response.statusCode(), 401);

        Error error = response.as(Error.class);
        assertError(error, 401, "Invalid access token");
    }

    public Playlist playListBuilder(String name, String description, boolean _public){
        return Playlist.builder()
                .name(name)
                .description(description)
                ._public(_public)
                .build();
    }

    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

    public void assertError(Error responseError, int expectedStatusCode, String expectedErrorMsg){
        assertThat(responseError.getErrorInner().getStatus(), equalTo(expectedStatusCode));
        assertThat(responseError.getErrorInner().getMessage(), equalTo(expectedErrorMsg));
    }
}
