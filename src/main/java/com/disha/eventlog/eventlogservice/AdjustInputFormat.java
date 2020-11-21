package com.disha.quickride.eventlogservice;

import org.springframework.stereotype.Component;

@Component
public class AdjustInputFormat {

    public String adjustEventName(String eventName)
    {
        switch (eventName)
        {
            case "Applypromosuccess":
                return "1h8men";
            case "Fullsignupcomplete":
                return "vv9t6b";
            case "OrgEmailVerified":
                return "29lr6n";
            case "Aadharverified":
                return "x4r2ck";
            case "DLVerfied":
                return "c0ak2g";
            case "UserRideCreated":
                return "2y2kw7";
            case "RiderFirstRideCompleted":
                return "27e2id";
            case "PassengerFirstRideCompleted":
                return "wfznqe";
            case "OfferRideCreated":
                return "syv3w3";
            case "FindRideCreated":
                return "32eto1";
            case "RiderRideMatchNotFound":
                return "cfoovn";
            case "PassengerRideMatchNotFound":
                return "vz5lyw";
            case "RiderRideMatchFound":
                return "km98m7";
            case "PassengerRideMatchFound":
                return "8m9sx7";
            case "RiderRecurringRideCreated":
                return "jz3dgg";
            case "RiderRecurringRideCancelled":
                return "1knxhg";
            case "PassengerRecurringRideCreated":
                return "ulhez3";
            case "PassengerRecurringRideCancelled":
                return "yij2c7";
            case "RiderRideCompleted":
                return "cy68wd";
            case "PassengerRideCompleted":
                return "vvlofz";
            case "RidePromoSuccess":
                return "z0pnxl";

        }
        return "Event Name Not Found";
    }
}
