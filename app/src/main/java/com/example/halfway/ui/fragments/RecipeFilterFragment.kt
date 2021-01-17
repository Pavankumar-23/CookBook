package com.example.halfway.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.halfway.databinding.FragmentRecipeFilterBinding


class RecipeFilterFragment : Fragment() {

    private lateinit var binding: FragmentRecipeFilterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeFilterBinding.inflate(inflater)
        return binding.root
    }

}