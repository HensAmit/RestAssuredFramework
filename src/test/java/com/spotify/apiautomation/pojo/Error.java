package com.spotify.apiautomation.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {

    @JsonProperty("error")
    private ErrorInner errorInner;

    @JsonProperty("error")
    public ErrorInner getErrorInner() {
        return errorInner;
    }

    @JsonProperty("error")
    public void setErrorInner(ErrorInner errorInner) {
        this.errorInner = errorInner;
    }

}
