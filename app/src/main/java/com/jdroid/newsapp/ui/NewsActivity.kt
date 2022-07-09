package com.jdroid.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jdroid.newsapp.R
import com.jdroid.newsapp.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {


    var mBinding: ActivityNewsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)

        mBinding?.let {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
            val navController = navHostFragment.navController
            mBinding?.bottomNavigationView!!.setupWithNavController(navController)
        }


    }
}