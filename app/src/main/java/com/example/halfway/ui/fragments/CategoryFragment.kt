package com.example.halfway.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.halfway.adapters.CategoryAdapter
import com.example.halfway.databinding.FragmentCategoryBinding
import com.example.halfway.listeners.OnFactClickListener
import com.example.halfway.model.Result
import com.example.halfway.util.NetworkResult
import com.example.halfway.util.observeOnce
import com.example.halfway.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment : Fragment(), OnFactClickListener {

    private val mainViewModel by viewModels<MainViewModel>()
    private var _binding: FragmentCategoryBinding? = null
    private lateinit var mAdapter: CategoryAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater)
        binding.pbLoading.show()
        init()
        return binding.root
    }

    private fun init() {
        readFromDatabase()
        binding.apply {
            pullToRefresh.setOnRefreshListener {
                readFromDatabase()
                pullToRefresh.isRefreshing = false
            }
        }
        initRecyclerView()
        subscribeToFactsObservers()
    }

    private fun initRecyclerView() {

        mAdapter = CategoryAdapter(this, 3)
        binding.apply {
            rcvDatalist.setHasFixedSize(true)
            rcvDatalist.adapter = mAdapter
        }
    }

    private fun subscribeToFactsObservers() {

        lifecycleScope.launch {
            mainViewModel.factsResponse.observe(viewLifecycleOwner, { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        binding.pbLoading.hide()
                        result.data?.let { response ->
                            mAdapter.setIngredients(response.results)
                        }
                    }
                    is NetworkResult.Error -> {
                        binding.pbLoading.hide()
                        Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                    }
                    is NetworkResult.Loading -> {
                        binding.pbLoading.visibility = View.VISIBLE
                    }
                }
            })
        }
    }

    private fun readFromDatabase() {

        lifecycleScope.launch {
            delay(5000)
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, {
                if (it.isNotEmpty()) {
                    binding.pbLoading.hide()
                    mAdapter.setIngredients(it)
                } else {
                    mainViewModel.getFactsFromServer()
                }
            })
        }
    }

    override fun onFactClick(position: Int, view: View) {

        val action = CategoryFragmentDirections.categoryToRecipeDetail(
            mAdapter.getSelectedItem(position) as Result
        )
        view.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

