package com.jdroid.newsapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jdroid.newsapp.AppController
import com.jdroid.newsapp.repository.NewsRepository

class NewsViewModelProvideFactory(val app: Application, private val newsRepository: NewsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(app, newsRepository) as T
    }
}