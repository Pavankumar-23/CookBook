package com.example.halfway.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.halfway.adapters.CategoryAdapter
import com.example.halfway.databinding.FragmentRecipeInstructionBinding
import com.example.halfway.model.Result

class RecipeInstructionsFragment : Fragment() {

    private var _binding: FragmentRecipeInstructionBinding? = null
    private val binding get() = _binding!!
    private lateinit var mAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecipeInstructionBinding.inflate(inflater)
        val args = arguments?.getSerializable("recipeData") as Result
        initRecyclerView(args)
        return binding.root
    }

    private fun initRecyclerView(args: Result) {
        mAdapter = CategoryAdapter(null, 2)
        binding.rcvInstructions.adapter = mAdapter
        if (args.analyzedInstructions.size > 0) {
            mAdapter.setIngredients(args.analyzedInstructions[0].steps)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}