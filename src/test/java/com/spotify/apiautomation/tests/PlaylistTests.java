package com.spotify.apiautomation.tests;

import com.spotify.apiautomation.api.applicationAPI.PlaylistAPI;
import com.spotify.apiautomation.pojo.Error;
import com.spotify.apiautomation.pojo.Playlist;
import com.spotify.apiautomation.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.apiautomation.api.StatusCode.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@Epic("Spotify OAuth 2.0")
@Feature("Playlist API")
public class PlaylistTests extends BaseTest{

    @Description("Some test descriptions in detail")
    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @Issue("123")
    @TmsLink("test-1")
    @Story("Create playlist story")
    @Test (description = "Create a Playlist")
    public void shouldBeAbleToCreateAPlaylist(){
        Playlist requestPlaylist = playListBuilder("Playlist 4", "my 4th playlist", false);

        Response response = PlaylistAPI.post(requestPlaylist);
        assertStatusCode(response.statusCode(), CODE_201.code);

        Playlist responsePlaylist = response.as(Playlist.class);
        assertPlaylistEqual(responsePlaylist, requestPlaylist);
    }

    @Description("Getting a playlist to validate")
    @Test (description = "Get a Playlist")
    public void shouldBeAbleToGetAPlaylist(){
        Playlist requestPlaylist = playListBuilder("My Playlist 1", "Amits 1st playlist updated", false);

        Response response = PlaylistAPI.get(DataLoader.getInstance().getGetPlaylistId());
        assertStatusCode(response.statusCode(), CODE_200.code);

        Playlist responsePlaylist = response.as(Playlist.class);
        assertPlaylistEqual(responsePlaylist, requestPlaylist);
    }

    @Description("Updating a playlist and validate")
    @Test (description = "Update a Playlist")
    public void shouldBeAbleToUpdateAPlaylist(){
        Playlist requestPlaylist = playListBuilder("My Playlist 2", "Amits 2nd playlist", false);

        Response response = PlaylistAPI.update(requestPlaylist, DataLoader.getInstance().getUpdatePlaylistId());
        assertStatusCode(response.statusCode(), CODE_200.code);
    }

    @Description("Cannot create Playlist without name")
    @Story("Create playlist story")
    @Test (description = "Cannot create Playlist without name")
    public void shouldNotBeAbleToCreateAPlaylistWithoutName(){
        Playlist requestPlaylist = playListBuilder("", "Playlist without name", false);

        Response response = PlaylistAPI.post(requestPlaylist);
        assertStatusCode(response.statusCode(), CODE_400.code);

        Error error = response.as(Error.class);
        assertError(error, CODE_400.code, CODE_400.msg);
    }

    @Description("Cannot create Playlist with expired token")
    @Test (description = "Cannot create Playlist without valid token")
    public void shouldNotBeAbleToCreateAPlaylistWithExpiredToken(){
        String invalidToken = "12345";
        Playlist requestPlaylist = playListBuilder("dummy", "Playlist without token", false);

        Response response = PlaylistAPI.post(requestPlaylist, invalidToken);
        assertStatusCode(response.statusCode(), CODE_401.code);

        Error error = response.as(Error.class);
        assertError(error, CODE_401.code, CODE_401.msg);
    }

    @Step("Building the Playlist object")
    public Playlist playListBuilder(String name, String description, boolean _public){
        return Playlist.builder()
                .name(name)
                .description(description)
                ._public(_public)
                .build();
    }

    @Step("Comparing Request and Response Playlists")
    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(), equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(), equalTo(requestPlaylist.get_public()));
    }

    @Step("Asserting status code")
    public void assertStatusCode(int actualStatusCode, int expectedStatusCode){
        assertThat(actualStatusCode, equalTo(expectedStatusCode));
    }

    @Step("Asserting expected error code")
    public void assertError(Error responseError, int expectedStatusCode, String expectedErrorMsg){
        assertThat(responseError.getErrorInner().getStatus(), equalTo(expectedStatusCode));
        assertThat(responseError.getErrorInner().getMessage(), equalTo(expectedErrorMsg));
    }
}
