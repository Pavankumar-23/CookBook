package com.example.halfway.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.halfway.adapters.CategoryAdapter
import com.example.halfway.databinding.FragmentRecipeIngredientsBinding
import com.example.halfway.model.Result

class RecipeIngredientsFragment : Fragment() {

    private lateinit var binding: FragmentRecipeIngredientsBinding

    private lateinit var mAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeIngredientsBinding.inflate(inflater)

        val args = arguments?.getSerializable("recipeData") as Result
        initIngredients(args)

        return binding.root
    }

    private fun initIngredients(args: Result) {
        mAdapter = CategoryAdapter(null, 1)
        binding.rcvIngrdients.adapter = mAdapter
        mAdapter.setIngredients(args.extendedIngredients)
    }
}