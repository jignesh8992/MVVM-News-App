package com.jdroid.newsapp.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jdroid.newsapp.AppController
import com.jdroid.newsapp.api.Article
import com.jdroid.newsapp.api.ResponseNews
import com.jdroid.newsapp.repository.NewsRepository
import com.jdroid.newsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(val app: Application, val newsRepository: NewsRepository) : AndroidViewModel(app) {


    val breakingNews: MutableLiveData<Resource<ResponseNews>> = MutableLiveData()
    var breakingNewsPageNumber = 1
    var responseBreakingNews: ResponseNews? = null

    val searchNews: MutableLiveData<Resource<ResponseNews>> = MutableLiveData()
    var searchPageNumber = 1
    var responseSearchNews: ResponseNews? = null

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val responseNews = newsRepository.getBreakingNews(countryCode, breakingNewsPageNumber)
                breakingNews.postValue(handleBreakingNew(responseNews))
            } else {
                breakingNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> breakingNews.postValue(Resource.Error("Network Error"))
                else -> breakingNews.postValue(Resource.Error("Conversion Error"))
            }

        }

    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val searchResponse = newsRepository.searchNews(searchQuery, searchPageNumber)
                searchNews.postValue(handleSearchNews(searchResponse))
            } else {
                searchNews.postValue(Resource.Error("No Internet Connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> searchNews.postValue(Resource.Error("Network Error"))
                else -> searchNews.postValue(Resource.Error("Conversion Error"))
            }
        }

    }


    private fun handleBreakingNew(responseNews: Response<ResponseNews>): Resource<ResponseNews> {
        if (responseNews.isSuccessful) {
            responseNews.body()?.let { resultResponse ->
                breakingNewsPageNumber++
                if (responseBreakingNews == null) {
                    responseBreakingNews = resultResponse
                } else {
                    val oldArticles = responseBreakingNews?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(responseBreakingNews ?: resultResponse)
            }
        }
        return Resource.Error(responseNews.message())
    }

    private fun handleSearchNews(searchResponse: Response<ResponseNews>): Resource<ResponseNews> {
        if (searchResponse.isSuccessful) {
            searchResponse.body()?.let { resultResponse ->
                searchPageNumber++
                if (responseSearchNews == null) {
                    responseSearchNews = resultResponse
                } else {
                    val oldArticles = responseSearchNews?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(responseSearchNews ?: resultResponse)
            }
        }
        return Resource.Error(searchResponse.message())
    }

    fun upsertNews(article: Article) = viewModelScope.launch {
        newsRepository.upsertNews(article)
    }

    fun deleteNews(article: Article) = viewModelScope.launch {
        newsRepository.deleteNews(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<AppController>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }


}