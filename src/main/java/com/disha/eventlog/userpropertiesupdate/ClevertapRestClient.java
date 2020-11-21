package com.disha.quickride.userpropertiesupdate;

import com.disha.quickride.domain.model.*;
import com.disha.quickride.userpropertiesupdate.model.*;
import com.disha.quickride.util.DateUtils;
import com.disha.quickride.util.GsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;


@Component
public class ClevertapRestClient {

    private static String baseUrl = "https://api.clevertap.com/1/upload";
    private static final Logger log = LogManager.getLogger(ClevertapRestClient.class);

    public String getPassCode() {
        return passCode;
    }

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    private String passCode;
    private String accountId;

    public static void main(String[] args) throws Throwable {
        createUserProfileData();
    }

    private static String prepareClevertapDataFromProfileData(ProfileData profileData) {
        D[] d = new D[1];
        D d1 = new D();

        d1.setProfileData(profileData);
        d1.setType("profile");
        d1.setIdentity(profileData.getIdentity());

        d[0] = d1;

        UserInfo userInfo = new UserInfo();
        userInfo.setD(d);

        String json = GsonUtils.getJSONTextFromObject(userInfo);
        return json;
    }

    private static String prepareUserStatisticData(UserStatistics userStatistics) {

        UserStatisticsProfileData profileData = new UserStatisticsProfileData();
        profileData.setFirstTxnTim(userStatistics.getFirstTxnTime());
        profileData.setLastTransactedTime(userStatistics.getLastTransactedTime());
        profileData.setInviteResponseRate(userStatistics.getInviteResponseRate());
        profileData.setInstallationstatus(userStatistics.getInstallationStatus());
        profileData.setLastloggedintime(userStatistics.getLastLoggedInTime());
        profileData.setIdentity(String.valueOf(userStatistics.getAccountId()));
        return prepareClevertapDataFromProfileData(profileData);
    }

    private static String prepareUserLocationInfo(UserPrimaryAreaInformation userPrimaryAreaInformation) {
        UserLocationProfileData profileData = new UserLocationProfileData();

        profileData.setRegisteredState(userPrimaryAreaInformation.getRegisteredState());
        profileData.setRegisteredCity(userPrimaryAreaInformation.getRegisteredCity());

        profileData.setRecentCity(userPrimaryAreaInformation.getRecentCity());
        profileData.setRecentState(userPrimaryAreaInformation.getRecentState());
        profileData.setIdentity(String.valueOf(userPrimaryAreaInformation.getUserId()));

        return prepareClevertapDataFromProfileData(profileData);

    }

    private static String prepareEcoMeter(RideSharingCommunityContribution rideSharingCommunityContribution) {
        EcometerProfileData profileData = new EcometerProfileData();
        profileData.setNumberOfRidesShared(rideSharingCommunityContribution.getNumberOfRidesShared());
        profileData.setNumberOfRidesSharedAsRider(rideSharingCommunityContribution.getNumberOfRidesSharedAsRider());
        profileData.setNumberOfRidesSharedAsPassenger(rideSharingCommunityContribution.getNumberOfRidesSharedAsPassenger());
        profileData.setNumberOfCarsRemovedFromRoad(rideSharingCommunityContribution.getNumberOfCarsRemovedFromRoad());
        profileData.setNewFriendsMade(rideSharingCommunityContribution.getNewFriendsMade());
        profileData.setCo2Reduced(rideSharingCommunityContribution.getCo2Reduced());
        profileData.setTotalDistanceShared(rideSharingCommunityContribution.getTotalDistanceShared());
        profileData.setFuelPointsEarned(rideSharingCommunityContribution.getFuelPointsEarned());
        profileData.setFuelPointsRedeemed(rideSharingCommunityContribution.getFuelPointsRedeemed());
        profileData.setAmountSaved(rideSharingCommunityContribution.getAmountSaved());
        profileData.setAmountSaved(rideSharingCommunityContribution.getAmountSaved());
        profileData.setAmountSavedForOthers(rideSharingCommunityContribution.getAmountSavedForOthers());
        profileData.setIdentity(String.valueOf(rideSharingCommunityContribution.getUserId()));


        return prepareClevertapDataFromProfileData(profileData);

    }

    private static String prepareUserProfile(UserProfile userProfile) {

        UserProfileData profileData = new UserProfileData();
        profileData.setCompanyname(userProfile.getCompanyname());
        profileData.setVerificationstatus(userProfile.getVerificationstatus());
        profileData.setRating(userProfile.getRating());
        profileData.setNoOfReviews(userProfile.getNoOfReviews());
        profileData.setIdentity(String.valueOf(userProfile.getId()));


        return prepareClevertapDataFromProfileData(profileData);

    }

    private static String prepareUser(User user) {


        UserGeneralProfileData profileData = new UserGeneralProfileData();
        profileData.setName(user.getName());
        profileData.setGender(user.getGender());
        profileData.setCreationDate(user.getCreationDate());
        profileData.setIdentity(String.valueOf(user.getPhone()));
        profileData.setStatus(user.getStatus());

        return prepareClevertapDataFromProfileData(profileData);

    }

    public String createLinkedWallet(LinkedWallet linkedWallet) {
        UserProfileLinkedWalletData profileData = new UserProfileLinkedWalletData();
        profileData.setType(linkedWallet.getType());
        profileData.setIdentity(String.valueOf(linkedWallet.getUserId()));

        return prepareClevertapDataFromProfileData(profileData);

    }

    // TODO This is written for testing only
    public static void createUserProfileData() throws Throwable {

        UserLocationProfileData profileDataNew = new UserLocationProfileData();
        UserGeneralProfileData userGeneralProfileData = new UserGeneralProfileData();
        //profileDataNew.setName("Hardik");
        // profileDataNew.setRecentCity("Bengaluru");
        // profileDataNew.setRecentState("Karnataka");
        profileDataNew.setRegisteredCity("kolkata");
        userGeneralProfileData.setGender("Male-1");
        userGeneralProfileData.setName("hardik test-2");

        D[] d = new D[2];
        D d1 = new D();
        D d2 = new D();


        d1.setProfileData(profileDataNew);
        d1.setType("profile");
        d1.setIdentity("1234567");

        d2.setProfileData(userGeneralProfileData);
        d2.setType("profile");
        d2.setIdentity("1234567");

        d[0] = d1;
        d[1] = d2;

        UserInfo userInfo = new UserInfo();
        userInfo.setD(d);

        String json = GsonUtils.getJSONTextFromObject(userInfo);

        System.out.println(json);

        // String s = callClevertapApi(baseUrl, json);

        //System.out.println(s);
        //callSodexoServer(baseUrl, json, "POST");
    }

    private String callClevertapApi(String connectUrl, String json) throws Throwable {
        HttpURLConnection connection = null;

        URL url = new URL(connectUrl);
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Content-Length", Integer.toString(json.getBytes().length));
            connection.setRequestProperty("X-CleverTap-Account-Id", "677-74Z-765Z");
            connection.setRequestProperty("X-CleverTap-Passcode", "WFQ-BAD-IEKL");
            connection.setUseCaches(false);
            connection.setDoOutput(true);


            if (json != null) {
                DataOutputStream wr = null;
                try {
                    wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes(json);
                    wr.close();
                } catch (Throwable t) {
                    log.error("DataOutputStream failed ", t);
                    if (wr != null)
                        wr.close();
                }
            }

            StringBuilder response = new StringBuilder();
            try (InputStream is = connection.getInputStream()) {
                try (BufferedReader rd = new BufferedReader(new InputStreamReader(is))) {
                    String line = "";
                    while ((line = rd.readLine()) != null) {
                        response.append(line);
                        response.append('\r');
                    }
                }
            }
            log.debug("callClevertapApi response for " + json + " url:" + connectUrl + " response =" + response);
            return response.toString();
        } catch (Throwable e) {
            log.error("callClevertapApi response failed for " + json + " url:" + connectUrl, e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return "";
    }


    public void triggerClevertapEvent(String data) {

        ClevertapEntity clevertapEntity = (ClevertapEntity) GsonUtils.getObjectFromJSONText(ClevertapEntity.class, data);

        if (clevertapEntity.getActionType().equalsIgnoreCase(ClevertapEntity.USER_PROFILE_UPDATE_ACTION)) {
            userProfilepdated(clevertapEntity);
        } else if (clevertapEntity.getActionType().equalsIgnoreCase(ClevertapEntity.USER_PRIMARY_AREA_INFO_UPDATE_ACTION)) {
            userPrimaryAreaUpdated(clevertapEntity);
        } else if (clevertapEntity.getActionType().equalsIgnoreCase(ClevertapEntity.FIRST_TRANSACTION_TIME_UPDATE_ACTION)) {
            firstTransactionTime(clevertapEntity);
        } else if (clevertapEntity.getActionType().equalsIgnoreCase(ClevertapEntity.RIDER_RIDE_COMPLETED_ACTION)) {
            riderRideCompleted(clevertapEntity);
        } else if (clevertapEntity.getActionType().equalsIgnoreCase(ClevertapEntity.PASSENGER_RIDE_COMPLETED_ACTION)) {
            passengerRideCompleted(clevertapEntity);
        } else if (clevertapEntity.getActionType().equalsIgnoreCase(ClevertapEntity.LAST_LOGGED_IN_ACTION)) {
            lastLoggedIn(clevertapEntity);
        } else if (clevertapEntity.getActionType().equalsIgnoreCase(ClevertapEntity.LAST_TRANSACTION_TIME_UPDATE_ACTION)) {
            lastTransactionTime(clevertapEntity);
        } else if (clevertapEntity.getActionType().equalsIgnoreCase(ClevertapEntity.USER_SUBSCRIPTION_ACTION)) {
            trackUserSubscription(clevertapEntity);
        } else if (clevertapEntity.getActionType().equalsIgnoreCase(ClevertapEntity.USER_NAME_UPDATE_ACTION)) {
            trackUserName(clevertapEntity);
        } else if (clevertapEntity.getActionType().equalsIgnoreCase(ClevertapEntity.USER_GENDER_UPDATE_ACTION)) {
            trackUserGender(clevertapEntity);
        } else if (clevertapEntity.getActionType().equalsIgnoreCase(ClevertapEntity.LINKED_WALLET_UPDATE_ACTION)) {
            updateLinkedWalletType(clevertapEntity);
        } else if (clevertapEntity.getActionType().equalsIgnoreCase(ClevertapEntity.USER_CREATION_ACTION)) {
            userCreation(clevertapEntity);
        }  else if (clevertapEntity.getActionType().equalsIgnoreCase(ClevertapEntity.RECENT_ROLE_ACTION)) {
            updateRecentRole(clevertapEntity);
        }
    }

    private void userCreation(ClevertapEntity clevertapEntity) {
        HashMap<String, Object> map = clevertapEntity.getProperty();
        User user =   (User) GsonUtils.getObjectFromJSONText(User.class, map.get(ClevertapEntity.USER_OBJECT).toString()) ;
        String clevertapJson = prepareUser(user);
        try {
            callClevertapApi(baseUrl, clevertapJson);
        } catch (Throwable t) {
            log.error("callClevertapApi  response failed for userCreation()" + t);
        }

    }

    private void updateLinkedWalletType(ClevertapEntity clevertapEntity) {
        HashMap<String, Object> map = clevertapEntity.getProperty();
        Double userId = (Double) map.get(ClevertapEntity.USER_ID);
        String type = (String) map.get(ClevertapEntity.LINKED_WALLET_TYPE_OBJECT);
        LinkedWallet linkedWallet = new LinkedWallet();
        linkedWallet.setType(type);
        linkedWallet.setUserId(userId.longValue());
        String clevertapJson = createLinkedWallet(linkedWallet);
        try {
            callClevertapApi(baseUrl, clevertapJson);
        } catch (Throwable t) {
            log.error("callClevertapApi  response failed for updateLinkedWalletType()" + t);

        }
    }

    private void trackUserGender(ClevertapEntity clevertapEntity) {
        HashMap<String, Object> map = clevertapEntity.getProperty();
        Double userId = (Double) map.get(ClevertapEntity.USER_ID);
        String gender = (String) map.get(ClevertapEntity.USER_GENDER_OBJECT);
        User user = new User();
        user.setGender(gender);
        user.setPhone(userId.longValue());
        String clevertapJson = prepareUser(user);
        try {
            callClevertapApi(baseUrl, clevertapJson);
        } catch (Throwable t) {
            log.error("callClevertapApi  response failed for trackUserGender()" + t);

        }
    }

    private void trackUserName(ClevertapEntity clevertapEntity) {
        HashMap<String, Object> map = clevertapEntity.getProperty();
        Double userId = (Double) map.get(ClevertapEntity.USER_ID);
        String name = (String) map.get(ClevertapEntity.USER_NAME_OBJECT);
        User user = new User();
        user.setName(name);
        user.setPhone(userId.longValue());
        String clevertapJson = prepareUser(user);
        try {
            callClevertapApi(baseUrl, clevertapJson);
        } catch (Throwable t) {
            log.error("callClevertapApi  response failed for trackUserName()" + t);

        }
    }

    private void trackUserSubscription(ClevertapEntity clevertapEntity) {

        HashMap<String, Object> map = clevertapEntity.getProperty();
        Double userId = (Double) map.get(ClevertapEntity.USER_ID);
        String status = (String) map.get(ClevertapEntity.USER_SUBSCRIPTION_OBJECT);
        User user = new User();
        user.setStatus(status);
        user.setPhone(userId.longValue());
        String clevertapJson = prepareUser(user);
        try {
            callClevertapApi(baseUrl, clevertapJson);
        } catch (Throwable t) {
            log.error("callClevertapApi  response failed for trackUserSubscription()" + t);

        }
    }

    private void lastTransactionTime(ClevertapEntity clevertapEntity) {
        HashMap<String, Object> map = clevertapEntity.getProperty();
        Double userId = (Double) map.get(ClevertapEntity.USER_ID);

        Date time = convertStringToDateTime((String) map.get(ClevertapEntity.LAST_TRANSACTION_TIME_OBJECT));
        UserStatistics data = new UserStatistics();
        data.setLastTransactedTime(time);
        data.setAccountId(userId.longValue());
        String clevertapJson = prepareUserStatisticData(data);
        try {
            callClevertapApi(baseUrl, clevertapJson);
        } catch (Throwable t) {
            log.error("callClevertapApi  response failed for lastTransactionTime()" + t);

        }
    }

    private Date convertStringToDateTime(String data) {
        return DateUtils.getDateFromStoragingDBFormatString(data);
    }

    private void lastLoggedIn(ClevertapEntity clevertapEntity) {
        HashMap<String, Object> map = clevertapEntity.getProperty();
        Double userId = (Double) map.get(ClevertapEntity.USER_ID);
        Date time = convertStringToDateTime((String) map.get(ClevertapEntity.LAST_LOGGED_IN_OBJECT));
        UserStatistics data = new UserStatistics();
        data.setLastLoggedInTime(time);
        data.setInstallationStatus(true);
        data.setAccountId(userId.longValue());
        String clevertapJson = prepareUserStatisticData(data);
        try {
            callClevertapApi(baseUrl, clevertapJson);
        } catch (Throwable t) {
            log.error("callClevertapApi  response failed for lastLoggedIn()" + t);
        }

    }


    private void passengerRideCompleted(ClevertapEntity clevertapEntity) {
        HashMap<String, Object> map = clevertapEntity.getProperty();

        PassengerRide riderRide =  (PassengerRide) GsonUtils.getObjectFromJSONText(PassengerRide.class, map.get(ClevertapEntity.PASSENGER_RIDE_OBJECT).toString()) ;

        RideSharingCommunityContribution rideSharingCommunityContribution = (RideSharingCommunityContribution) GsonUtils.getObjectFromJSONText(RideSharingCommunityContribution.class, map.get(ClevertapEntity.ECO_METER_OBJECT).toString()) ;
        String clevertapJson = prepareEcoMeter(rideSharingCommunityContribution);
        try {
            callClevertapApi(baseUrl, clevertapJson);
        } catch (Throwable t) {
            log.error("callClevertapApi  response failed for passengerRideCompleted()" + t);

        }
    }

    private void firstTransactionTime(ClevertapEntity clevertapEntity) {
        HashMap<String, Object> map = clevertapEntity.getProperty();
        Double userId = (Double) map.get(ClevertapEntity.USER_ID);
        Date time = convertStringToDateTime((String) map.get(ClevertapEntity.FIRST_TRANSACTION_TIME_OBJECT));
        UserStatistics data = new UserStatistics();
        data.setFirstTxnTime(time);
        data.setAccountId(userId.longValue());
        String clevertapJson = prepareUserStatisticData(data);
        try {
            callClevertapApi(baseUrl, clevertapJson);
        } catch (Throwable t) {
            log.error("callClevertapApi  response failed for firstTransactionTime()" + t);
        }
    }

    private void updateRecentRole(ClevertapEntity clevertapEntity) {
        HashMap<String, Object> map = clevertapEntity.getProperty();
        Double userId = (Double) map.get(ClevertapEntity.USER_ID);
        String recentRole = (String) map.get(ClevertapEntity.RECENT_ROLE_OBJECT);
        UserStatistics data = new UserStatistics();
        data.setRecentRole(recentRole);
        data.setAccountId(userId.longValue());
        String clevertapJson = prepareUserStatisticData(data);
        try {
            callClevertapApi(baseUrl, clevertapJson);
        } catch (Throwable t) {
            log.error("callClevertapApi  response failed for updateRecentRole()" + t);
        }
    }

    public void riderRideCompleted(ClevertapEntity clevertapEntity) {
        HashMap<String, Object> map = clevertapEntity.getProperty();
        RideSharingCommunityContribution rideSharingCommunityContribution = (RideSharingCommunityContribution) GsonUtils.getObjectFromJSONText(RideSharingCommunityContribution.class, map.get(ClevertapEntity.ECO_METER_OBJECT).toString()) ;;
        String clevertapJson = prepareEcoMeter(rideSharingCommunityContribution);
        try {
            callClevertapApi(baseUrl, clevertapJson);
        } catch (Throwable t) {
            log.error("callClevertapApi  response failed for riderRideCompleted()" + t);

        }
    }


    private void userPrimaryAreaUpdated(ClevertapEntity clevertapEntity) {

        HashMap<String, Object> map = clevertapEntity.getProperty();

        UserPrimaryAreaInformation userPrimaryAreaInformation =(UserPrimaryAreaInformation) GsonUtils.getObjectFromJSONText(UserPrimaryAreaInformation.class, map.get(ClevertapEntity.USER_PRIMARY_AREA_INFO_OBJECT).toString());
        String clevertapJson = prepareUserLocationInfo(userPrimaryAreaInformation);
        try {
            callClevertapApi(baseUrl, clevertapJson);
        } catch (Throwable t) {
            log.error("callClevertapApi  response failed for userPrimaryAreaUpdated()" + t);

        }
    }

    public void userProfilepdated(ClevertapEntity clevertapEntity) {

        HashMap<String, Object> map = clevertapEntity.getProperty();

        UserProfile userProfile =  (UserProfile) GsonUtils.getObjectFromJSONText(UserProfile.class, map.get(ClevertapEntity.USER_PROFILE_OBJECT).toString()) ;
        String clevertapJson = prepareUserProfile(userProfile);
        try {
            callClevertapApi(baseUrl, clevertapJson);
        } catch (Throwable t) {
            log.error("callClevertapApi  response failed for userProfilepdated()" + t);
        }
    }
}
