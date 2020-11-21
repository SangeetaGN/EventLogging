package com.disha.quickride.userpropertiesupdate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class D {
    private String type;

    private ProfileData profileData;

    private String objectId;

    private String identity;

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public ProfileData getProfileData ()
    {
        return profileData;
    }

    public void setProfileData (ProfileData profileData)
    {
        this.profileData = profileData;
    }

    public String getObjectId ()
    {
        return objectId;
    }

    public void setObjectId (String objectId)
    {
        this.objectId = objectId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [type = "+type+", profileData = "+profileData+", objectId = "+objectId+"]";
    }
}
