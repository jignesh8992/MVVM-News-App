package com.jdroid.newsapp.api


import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
data class ResponseNews(
    val articles: List<Article?>? = null,
    val status: String? = null,
    val totalResults: Int? = null
)


@Keep
@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)

@Keep
data class Source(
    val id: String? = null,
    val name: String? = null
)