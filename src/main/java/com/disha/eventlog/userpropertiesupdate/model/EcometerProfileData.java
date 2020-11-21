package com.disha.quickride.userpropertiesupdate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class EcometerProfileData extends ProfileData {
    private long userId;
    private String category;
    private int newFriendsMade;
    private long numberOfRidesShared;
    private long numberOfRidesSharedAsRider;
    private long numberOfRidesSharedAsPassenger;
    private long numberOfCarsRemovedFromRoad;
    private float co2Reduced;
    private double totalDistanceShared;
    private int fuelPointsEarned;
    private int fuelPointsRedeemed;
    private int amountSaved;
    private int amountSavedForOthers;
}
