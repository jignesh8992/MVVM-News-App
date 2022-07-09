package com.jdroid.newsapp.repository

import com.jdroid.newsapp.api.RetrofitInstance
import com.jdroid.newsapp.db.NewsDatabase

class NewsRepository(db: NewsDatabase) {

    suspend fun getBreakingNews(countryCode: String, page: Int) = RetrofitInstance.api.getHeadlines(countryCode, page)

}