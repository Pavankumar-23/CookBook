package com.example.halfway.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.example.halfway.R
import com.example.halfway.adapters.FactsAdapter
import com.example.halfway.listeners.OnFactClickListener
import com.example.halfway.util.Util
import com.example.halfway.util.VerticalSpacingItemDecorator
import com.example.halfway.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), OnFactClickListener {

    private val TAG = "MainActivity"
    lateinit var rcv_datalist: RecyclerView
    lateinit var mainViewModel: MainViewModel
    lateinit var mAdapter: FactsAdapter
    lateinit var pullToRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        initRecyclerView()
        subscribeToFactsObservers()
    }

    private fun subscribeToFactsObservers() {
        try {
            mainViewModel.getFacts()?.observe(this, {
                if (it != null) {
                    mAdapter.setFacts(it)
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    private fun initRecyclerView() {
        try {
            val viewPreloader = ViewPreloadSizeProvider<String>()
            mAdapter = FactsAdapter(this, initGlide(), viewPreloader)
            val itemDecorator = VerticalSpacingItemDecorator(10)
            rcv_datalist.addItemDecoration(itemDecorator)
            rcv_datalist.setLayoutManager(LinearLayoutManager(this))

            val preloader: RecyclerViewPreloader<String> = RecyclerViewPreloader(
                Glide.with(this),
                mAdapter,
                viewPreloader,
                10
            )

            rcv_datalist.addOnScrollListener(preloader)

            rcv_datalist.adapter = mAdapter
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    private fun init() {

        try {
            mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            rcv_datalist = findViewById(R.id.rcv_datalist)
            pullToRefresh = findViewById(R.id.pullToRefresh)

            pullToRefresh.setOnRefreshListener(OnRefreshListener {
                subscribeToFactsObservers()
                pullToRefresh.setRefreshing(false)
            })
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
    }

    private fun initGlide(): RequestManager {
        lateinit var requestManager: RequestManager
        try {
            val options: RequestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
            requestManager = Glide.with(this)
                .setDefaultRequestOptions(options)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }
        return requestManager
    }

    override fun onFactClick(position: Int) {
        val intent = Intent(this, FactActivity::class.java)
        intent.putExtra("fact", mAdapter.getSelectedRecipe(position))
        startActivity(intent)
    }
}