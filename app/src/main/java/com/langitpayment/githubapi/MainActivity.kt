package com.langitpayment.githubapi

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.langitpayment.githubapi.databinding.ActivityMainBinding
import com.langitpayment.githubapi.entity.TrendingRepositories
import com.langitpayment.seller.utils.state.LoaderState
import kotlinx.android.synthetic.main.error_handling_layout.*
import kotlinx.android.synthetic.main.shimmer_layout.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupToolbar()
        initObserver()
        binding.swipeRefreshLayout.setOnRefreshListener {
            initObserver()
        }
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val customToolbar = layoutInflater.inflate(R.layout.custom_toolbar, null)
        supportActionBar?.customView = customToolbar
    }

    private fun initObserver() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                is LoaderState.ShowLoading -> {
                    startShimmer()
                }
                is LoaderState.HideLoading -> {
                    stopShimmer()
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })

        viewModel.result.observe(this, Observer { trendingRepositories ->
            binding.recyclerview.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = MainAdapter(trendingRepositories as ArrayList<TrendingRepositories>)
            }
        })

        viewModel.error.observe(this, Observer { message ->
            Log.e("ERROR", message)
            errorHandlingLayout.visibility = View.VISIBLE
            stopShimmer()
            binding.swipeRefreshLayout.isRefreshing = false
        })
    }

    private fun startShimmer() {
        binding.recyclerview.visibility = View.GONE
        shimmeringLayout.visibility = View.VISIBLE
        shimmeringLayout.startShimmer()
    }

    private fun stopShimmer() {
        binding.recyclerview.visibility = View.VISIBLE
        shimmeringLayout.stopShimmer()
        shimmeringLayout.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        stopShimmer()
    }
}