package com.jdroid.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jdroid.newsapp.R
import com.jdroid.newsapp.databinding.ActivityNewsBinding
import com.jdroid.newsapp.db.NewsDatabase
import com.jdroid.newsapp.repository.NewsRepository
import com.jdroid.newsapp.ui.viewmodel.NewsViewModel
import com.jdroid.newsapp.ui.viewmodel.NewsViewModelProvideFactory

class NewsActivity : AppCompatActivity() {


    lateinit var mBinding: ActivityNewsBinding
    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val newsRepository = NewsRepository(NewsDatabase(this))
        val viewModelProvideFactory = NewsViewModelProvideFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProvideFactory)[NewsViewModel::class.java]


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        mBinding.bottomNavigationView.setupWithNavController(navController)


    }
}