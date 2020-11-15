package com.example.halfway.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.halfway.R
import com.example.halfway.model.Facts
import kotlinx.android.synthetic.main.activity_fact.*

class FactActivity : AppCompatActivity() {
    private val TAG = "FactActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fact)
        init()
    }

    private fun init() {
        var intent = intent.extras
        var fact : Facts = intent?.get("fact") as Facts
        tv_fact_title.text = fact.title
        tv_fact_desc.text = fact.description
        initGlide().load(fact.imageUrl)
            .into(imageView)
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
}