package com.disha.quickride.userpropertiesupdate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UserStatisticsProfileData extends ProfileData {
    Date firstTxnTim;
    Date lastTransactedTime;
    int inviteResponseRate;
    Date lastloggedintime;
    boolean installationstatus;

}
