package com.disha.quickride.userpropertiesupdate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UserProfileData extends  ProfileData {

    private String companyname;
    private boolean verificationstatus;
    private float rating;
    private int noOfReviews;
}
