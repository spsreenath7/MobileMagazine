package com.wit.magazine.models;

public class UserPreference {
    boolean all;
    boolean business;
    boolean entertainment;
    boolean science;
    boolean health;
    boolean sports;
    boolean technology;
    String countrycode;
    String countryname;
    int articlecount;

    public UserPreference(){

    }

    public UserPreference(boolean all, boolean business, boolean entertainment, boolean science, boolean health, boolean sports, boolean technology, String countrycode, String countryname, int articlecount) {
        this.all = all;
        this.business = business;
        this.entertainment = entertainment;
        this.science = science;
        this.health = health;
        this.sports = sports;
        this.technology = technology;
        this.countrycode = countrycode;
        this.countryname = countryname;
        this.articlecount = articlecount;
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public boolean isBusiness() {
        return business;
    }

    public void setBusiness(boolean business) {
        this.business = business;
    }

    public boolean isEntertainment() {
        return entertainment;
    }

    public void setEntertainment(boolean entertainment) {
        this.entertainment = entertainment;
    }

    public boolean isScience() {
        return science;
    }

    public void setScience(boolean science) {
        this.science = science;
    }

    public boolean isHealth() {
        return health;
    }

    public void setHealth(boolean health) {
        this.health = health;
    }

    public boolean isSports() {
        return sports;
    }

    public void setSports(boolean sports) {
        this.sports = sports;
    }

    public boolean isTechnology() {
        return technology;
    }

    public void setTechnology(boolean technology) {
        this.technology = technology;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public int getArticlecount() {
        return articlecount;
    }

    public void setArticlecount(int articlecount) {
        this.articlecount = articlecount;
    }
}
