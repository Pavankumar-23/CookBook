package com.example.halfway.ui.fragments

import android.os.Bundle
import android.view.*
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.halfway.R
import com.example.halfway.databinding.FragmentSearchRecipeBinding


class SearchRecipeFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentSearchRecipeBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchView: SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchRecipeBinding.inflate(inflater)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        searchView = menu.findItem(R.id.bt_serach).actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = "Search.."
        searchView.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchView.clearFocus()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}