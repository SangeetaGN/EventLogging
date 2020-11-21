package com.disha.quickride.userpropertiesupdate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UserLocationProfileData extends ProfileData {

    private String registeredState;
    private String registeredCity;
    private String recentState;
    private String recentCity;

}
