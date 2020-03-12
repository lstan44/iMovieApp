package com.stanleylalanne.imovieapp.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "iso_639_1",
        "name"
})
public class GetLanguage {

    @JsonProperty("iso_639_1")
    public String iso6391;
    @JsonProperty("name")
    public String name;

}
