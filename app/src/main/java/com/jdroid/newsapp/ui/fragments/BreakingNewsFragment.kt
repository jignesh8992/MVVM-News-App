package com.jdroid.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.jdroid.newsapp.R
import com.jdroid.newsapp.adapter.NewsAdapter
import com.jdroid.newsapp.ui.NewsActivity
import com.jdroid.newsapp.ui.viewmodel.NewsViewModel
import com.jdroid.newsapp.utils.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*


class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    private val TAG = javaClass.simpleName


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        viewModel.getBreakingNews("in")

        newsAdapter = NewsAdapter()
        rvBreakingNews.adapter = newsAdapter

        viewModel.breakingNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgress()
                }
                is Resource.Success -> {
                    hideProgress()
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }

                }
                is Resource.Error -> {
                    hideProgress()
                    Log.i(TAG, "OnError: ${response.message}")
                }
            }
        }


    }

    fun hideProgress() {
        paginationProgressBar.visibility = View.GONE
    }

    fun showProgress() {
        paginationProgressBar.visibility = View.GONE
    }
}