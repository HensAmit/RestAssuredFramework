package com.spotify.apiautomation.api;

import com.spotify.apiautomation.utils.ConfigLoader;
import io.restassured.response.Response;
import java.time.Instant;
import java.util.HashMap;

public class TokenManager {
    private static String accessToken;
    private static Instant expiryTime;

    public static String getToken(){
        try{
            if(accessToken==null || Instant.now().isAfter(expiryTime)){
                System.out.println("Renewing token...");
                Response response = renewToken();
                accessToken = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiryTime = Instant.now().plusSeconds(expiryDurationInSeconds - 300);//300 sec = 5 mins buffer time
            } else{
                System.out.println("Token is valid and good to use");
            }
        }
        catch(Exception e){
            throw new RuntimeException("ABORT!! Failed to get the token!!");
        }
        return accessToken;
    }

    private static Response renewToken(){
        HashMap<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParams.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());
        formParams.put("client_id", ConfigLoader.getInstance().getClientId());
        formParams.put("client_secret", ConfigLoader.getInstance().getClientSecret());

        Response response = RestResource.postAccount(formParams, "/api/token");

        if(response.statusCode() != 200){
            throw new RuntimeException("ABORT!!! Renew token failed!!");
        }

        return response;
    }
}
