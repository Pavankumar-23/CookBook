package com.example.halfway.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.halfway.databinding.ActivityFactBinding
import com.example.halfway.model.Facts
import com.example.halfway.util.setImage

class FactActivity : AppCompatActivity() {
    private val TAG = "FactActivity"
    private lateinit var binding: ActivityFactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFactBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }

    private fun init() {
        val intent = intent.extras
        val fact: Facts = intent?.get("fact") as Facts
        supportActionBar?.let {
            title = fact.title
        }
        binding.tvFactTitle.text = fact.title
        binding.tvFactDesc.text = fact.description
        binding.imageView.setImage(fact.imageUrl)
    }

}