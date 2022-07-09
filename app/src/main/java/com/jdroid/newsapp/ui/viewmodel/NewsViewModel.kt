package com.jdroid.newsapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdroid.newsapp.api.ResponseNews
import com.jdroid.newsapp.repository.NewsRepository
import com.jdroid.newsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {


    val breakingNews: MutableLiveData<Resource<ResponseNews>> = MutableLiveData()
    var pageNumber = 1

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val responseNews = newsRepository.getBreakingNews(countryCode, pageNumber)
        breakingNews.postValue(handleBreakingNew(responseNews))
    }

    private fun handleBreakingNew(responseNews: Response<ResponseNews>): Resource<ResponseNews> {
        if (responseNews.isSuccessful) {
            responseNews.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(responseNews.message())
    }
}