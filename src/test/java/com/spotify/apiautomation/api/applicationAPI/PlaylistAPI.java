package com.spotify.apiautomation.api.applicationAPI;

import com.spotify.apiautomation.api.RestResource;
import com.spotify.apiautomation.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.apiautomation.api.TokenManager.renewToken;

public class PlaylistAPI {

    public static Response post(Playlist requestPlaylist){
        return RestResource.post(renewToken(), "/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists", requestPlaylist);
    }

    //Overloaded to test invalid token scenario
    public static Response post(Playlist requestPlaylist, String invalidAccessToken){
        return RestResource.post(invalidAccessToken, "/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists", requestPlaylist);
    }

    public static Response get(String playlistId){
        return RestResource.get(renewToken(), "/playlists/"+playlistId);
    }

    public static Response update(Playlist requestPlaylist, String playlistId){
        return RestResource.update(renewToken(), "/playlists/"+playlistId, requestPlaylist);
    }
}