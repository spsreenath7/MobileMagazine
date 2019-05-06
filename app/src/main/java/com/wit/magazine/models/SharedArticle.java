package com.wit.magazine.models;

public class SharedArticle {

    String shareid;
    String caption;
    String title;
    String url;
    String urlToImage;
    String source;
    String userid;
    String username;
    long sharedDate;

    public SharedArticle(){

    }

    public SharedArticle(String shareid,String title,String caption, String url,String urlToImage, String source, String userid, String username, long sharedDate){
        this.shareid=shareid;
        this.title=title;
        this.caption =caption;
        this.url=url;
        this.urlToImage=urlToImage;
        this.source=source;
        this.userid=userid;
        this.username=username;
        this.sharedDate=sharedDate;
    }

    public String getShareid() {
        return shareid;
    }

    public void setShareid(String shareid) {
        this.shareid = shareid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getSharedDate() {
        return sharedDate;
    }

    public void setSharedDate(long sharedDate) {
        this.sharedDate = sharedDate;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}
