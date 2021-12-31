package com.spotify.apiautomation.api.applicationAPI;

import com.spotify.apiautomation.pojo.Playlist;
import io.restassured.response.Response;

import static com.spotify.apiautomation.api.SpecBuilder.getRequestSpec;
import static com.spotify.apiautomation.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class PlaylistAPI {
    static String accessToken = "BQBx2QopMfS9WKP2VDrF32mgMqAk509KqFUEh2ZYH5NdS486HSSDdwj9b9H2256qlJoazjRjBMcRl0_pVC4raAXCf32ZE8mfN-lNKEHgSNZYsBEv9fS5swd15cIVF87tqkZNOmmkt2DjaOKRRZaufzHHUbELkiOFdHbaRQOGcHIU-Z8LOdANTFTcIw7_0GBbfYQ7xYxfJoyNmAAVDDx9K_KjbevEs-f2w--LpaltWh-DxSIo";

    public static Response post(Playlist requestPlaylist){
        return given(getRequestSpec())
                    .header("Authorization", "Bearer "+accessToken)
                    .body(requestPlaylist).
               when()
                    .post("/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists").
               then()
                    .spec(getResponseSpec())
                    .extract().response();
    }

    //Overloaded to test invalid token scenario
    public static Response post(Playlist requestPlaylist, String invalidAccessToken){
        return given(getRequestSpec())
                    .header("Authorization", "Bearer "+invalidAccessToken)
                    .body(requestPlaylist).
               when()
                    .post("/users/3133u3fxpnaisnp6inrt3t6fxrvm/playlists").
               then()
                    .spec(getResponseSpec())
                    .extract().response();
    }

    public static Response get(String playlistId){
        return given(getRequestSpec())
                    .header("Authorization", "Bearer "+accessToken).
               when()
                    .get("/playlists/"+playlistId).
               then()
                    .spec(getResponseSpec())
                    .extract().response();
    }

    public static Response update(Playlist requestPlaylist, String playlistId){
        return given(getRequestSpec())
                    .header("Authorization", "Bearer "+accessToken)
                    .body(requestPlaylist).
               when()
                    .put("/playlists/"+playlistId).
               then()
                    .spec(getResponseSpec())
                    .extract().response();
    }
}