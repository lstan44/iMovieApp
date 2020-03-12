package com.stanleylalanne.imovieapp.api.model;



import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Configs {

    @JsonProperty("images")
    public Images images;
    @JsonProperty("change_keys")
    public List<String> changeKeys = null;

}
