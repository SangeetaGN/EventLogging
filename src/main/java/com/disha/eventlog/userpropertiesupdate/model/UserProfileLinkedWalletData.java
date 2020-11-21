package com.disha.quickride.userpropertiesupdate.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UserProfileLinkedWalletData extends ProfileData {
    private String type;
}
