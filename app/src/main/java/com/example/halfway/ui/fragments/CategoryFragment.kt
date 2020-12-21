package com.example.halfway.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.recyclerview.RecyclerViewPreloader
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.example.halfway.R
import com.example.halfway.adapters.FactsAdapter
import com.example.halfway.databinding.FragmentCategoryBinding
import com.example.halfway.listeners.OnFactClickListener
import com.example.halfway.ui.FactActivity
import com.example.halfway.util.NetworkResult
import com.example.halfway.util.VerticalSpacingItemDecorator
import com.example.halfway.util.observeOnce
import com.example.halfway.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment(), OnFactClickListener {

    private val TAG = "MainActivity"
    private lateinit var mainViewModel: MainViewModel
    private lateinit var mAdapter: FactsAdapter
    private lateinit var binding: FragmentCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        init()
        initRecyclerView()
        subscribeToFactsObservers()
        return binding.root
    }

    private fun init() {
        readFromDatabase()
        binding.pullToRefresh.setOnRefreshListener {
            readFromDatabase()
            binding.pullToRefresh.isRefreshing = false
        }
    }

    private fun initRecyclerView() {
        try {
            val viewPreloader = ViewPreloadSizeProvider<String>()
            mAdapter = FactsAdapter(this, initGlide(), viewPreloader)
            val itemDecorator = VerticalSpacingItemDecorator(5)
            val rcv = binding.rcvDatalist

            rcv.addItemDecoration(itemDecorator)
            rcv.layoutManager = LinearLayoutManager(context)
            val preloader: RecyclerViewPreloader<String> = RecyclerViewPreloader(
                Glide.with(this),
                mAdapter,
                viewPreloader,
                10
            )
            rcv.addOnScrollListener(preloader)
            rcv.adapter = mAdapter
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
        }
    }

    private fun subscribeToFactsObservers() {
        try {
            lifecycleScope.launch {
                mainViewModel.factsResponse.observe(viewLifecycleOwner, { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            binding.pbLoading.visibility = View.GONE
                            result.data?.let { response ->
                                mAdapter.setFacts(response.rows)
                            }
                        }
                        is NetworkResult.Error -> {
                            binding.pbLoading.visibility = View.GONE
                            Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                        }
                        is NetworkResult.Loading -> {
                            binding.pbLoading.visibility = View.VISIBLE
                        }
                    }
                })
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
        }
    }

    private fun readFromDatabase() {
        lifecycleScope.launch {
            mainViewModel.readFacts.observeOnce(viewLifecycleOwner, { facts ->
                binding.pbLoading.visibility = View.GONE
                if (facts.isNotEmpty()) {
                    mAdapter.setFacts(facts)
                } else {
                    mainViewModel.getFactsFromServer()
                }
            })
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
            Log.e(TAG, e.message!!)
        }
        return requestManager
    }

    override fun onFactClick(position: Int) {
        val intent = Intent(context, FactActivity::class.java)
        intent.putExtra("fact", mAdapter.getSelectedRecipe(position))
        startActivity(intent)
    }
}