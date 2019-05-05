package com.wit.magazine.api;

import com.wit.magazine.models.Article;

import java.util.List;

public class ArticleWrapper {
    public int totalResults;
    public String status;
    public List<Article> articles;
}
