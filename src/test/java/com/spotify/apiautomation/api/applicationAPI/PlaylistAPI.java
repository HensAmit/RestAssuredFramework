package com.spotify.apiautomation.api.applicationAPI;

import com.spotify.apiautomation.api.RestResource;
import com.spotify.apiautomation.pojo.Playlist;
import com.spotify.apiautomation.utils.ConfigLoader;
import io.restassured.response.Response;

import static com.spotify.apiautomation.api.TokenManager.getToken;

public class PlaylistAPI {

    public static Response post(Playlist requestPlaylist){
        return RestResource.post(getToken(), "/users/"+ ConfigLoader.getInstance().getUserId() +"/playlists", requestPlaylist);
    }

    //Overloaded to test invalid token scenario
    public static Response post(Playlist requestPlaylist, String invalidAccessToken){
        return RestResource.post(invalidAccessToken, "/users/"+ ConfigLoader.getInstance().getUserId() +"/playlists", requestPlaylist);
    }

    public static Response get(String playlistId){
        return RestResource.get(getToken(), "/playlists/"+playlistId);
    }

    public static Response update(Playlist requestPlaylist, String playlistId){
        return RestResource.update(getToken(), "/playlists/"+playlistId, requestPlaylist);
    }
}