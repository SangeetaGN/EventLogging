package com.disha.quickride.userpropertiesupdate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UserGeneralProfileData extends ProfileData {

    // User
    private String name;
    private String gender;
    private Date creationDate = null;
    private String status;
}
