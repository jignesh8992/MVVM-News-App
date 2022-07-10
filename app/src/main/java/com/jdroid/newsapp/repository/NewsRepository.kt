package com.jdroid.newsapp.repository

import com.jdroid.newsapp.api.Article
import com.jdroid.newsapp.api.RetrofitInstance
import com.jdroid.newsapp.db.NewsDatabase

class NewsRepository(private val db: NewsDatabase) {

    suspend fun getBreakingNews(countryCode: String, page: Int) = RetrofitInstance.api.getHeadlines(countryCode, page)

    suspend fun searchNews(searchQuery: String, page: Int) = RetrofitInstance.api.getSearchNews(searchQuery, page)

    suspend fun upsertNews(article: Article) = db.getArticleDao().upsert(article)

    suspend fun deleteNews(article: Article) = db.getArticleDao().delete(article)

    fun getSavedNews() = db.getArticleDao().getArticles()

}