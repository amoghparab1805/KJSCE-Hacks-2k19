package com.redhatpirates.kjsce_hacks_2k19;

public class UserDetails {
    private String displayName,email, phoneNumber, uid, providerId,photoURL,token,age,gender;

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUid() {
        return uid;
    }

    public String getProviderId() {
        return providerId;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public UserDetails(String token) {
        this.token = token;
    }

    public UserDetails(String displayName, String email, String phoneNumber, String uid, String providerId, String photoURL,String age,String gender) {
        this.displayName =displayName;
        this.phoneNumber = phoneNumber;
        this.uid = uid;
        this.providerId = providerId;
        this.photoURL = photoURL;
        this.email=email;
        this.age=age;
        this.gender=gender;
    }
}
