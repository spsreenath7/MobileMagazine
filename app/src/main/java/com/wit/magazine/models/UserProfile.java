package com.wit.magazine.models;

public class UserProfile {
    String userid;
    String email;
    String profilephotoURL;
    String username;

    public UserProfile(){

    }

    public UserProfile(String userid, String username, String email, String profilephotoURL) {
        this.userid = userid;
        this.email = email;
        this.profilephotoURL = profilephotoURL;
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilephotoURL() {
        return profilephotoURL;
    }

    public void setProfilephotoURL(String profilephotoURL) {
        this.profilephotoURL = profilephotoURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userid='" + userid + '\'' +
                ", email='" + email + '\'' +
                ", profilephotoURL='" + profilephotoURL + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
