package com.wit.magazine.models;

import java.util.Objects;

public class Bookmark {

    String bookmarkId;
    String title;
    String url;
    String source;
    String tag;
    String catogery;

    public Bookmark(){

    }

    public Bookmark(String bookmarkId, String source, String title, String url, String tag, String catogery) {
        this.bookmarkId=bookmarkId;
        this.title = title;
        this.url = url;
        this.source = source;
        this.tag = tag;
        this.catogery = catogery;
    }

    public String getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(String bookmarkId) {
        this.bookmarkId = bookmarkId;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCatogery() {
        return catogery;
    }

    public void setCatogery(String catogery) {
        this.catogery = catogery;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bookmark bookmark = (Bookmark) o;
        return
                Objects.equals(title, bookmark.title) &&
                Objects.equals(url, bookmark.url) &&
                Objects.equals(source, bookmark.source);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookmarkId, title, url, source);
    }
}
