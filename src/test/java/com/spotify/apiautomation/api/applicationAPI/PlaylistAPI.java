package com.spotify.apiautomation.api.applicationAPI;

import com.spotify.apiautomation.api.RestResource;
import com.spotify.apiautomation.pojo.Playlist;
import io.restassured.response.Response;

public class PlaylistAPI {
    static String accessToken = "BQDUXiG5WX8LNmky-hqc_iDjcR5lZZ1uhUpXbFdmwQtRl_uZ6zdwx8myqJ11Eyvi9wX_U5KLux-Qgm0bpfxsviPlmKGiLeaCzCn7Gc-VfPZsdK1kzBETnXbwT30mygCYjDs-0uSQcTlLccY2xL1BmQv19N9SayWDrxzVLg74COesx5i2ZdTLrqjlAvTXvIK4gYJWyRN5v7YnIYy9V95my-LP1PDZu1gFWGQnEkVNK-Aob-k0";

    public static Response post(Playlist requestPlaylist){
        return RestResource.post(accessToken, "/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists", requestPlaylist);
    }

    //Overloaded to test invalid token scenario
    public static Response post(Playlist requestPlaylist, String invalidAccessToken){
        return RestResource.post(invalidAccessToken, "/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists", requestPlaylist);
    }

    public static Response get(String playlistId){
        return RestResource.get(accessToken, "/playlists/"+playlistId);
    }

    public static Response update(Playlist requestPlaylist, String playlistId){
        return RestResource.update(accessToken, "/playlists/"+playlistId, requestPlaylist);
    }
}