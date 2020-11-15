package com.example.halfway.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.halfway.R
import com.example.halfway.viewmodel.MainViewModel
import com.google.android.material.appbar.AppBarLayout

class MainActivity : AppCompatActivity() {

    lateinit var appBarLayout: AppBarLayout
    lateinit var rcv_datalist: RecyclerView
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        appBarLayout = findViewById(R.id.appBarLayout)
        rcv_datalist = findViewById(R.id.rcv_datalist)
    }
}