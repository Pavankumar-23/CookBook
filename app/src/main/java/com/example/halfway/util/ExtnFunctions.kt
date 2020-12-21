package com.example.halfway.util

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.halfway.R

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}

fun ImageView.setImage(imageUrl: String?) {
    initGlide(this).load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade(25))
        .into(this)
}

private fun initGlide(imageView: ImageView): RequestManager {
    lateinit var requestManager: RequestManager
    try {
        val options: RequestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_foreground)
        requestManager = Glide.with(imageView.context)
            .setDefaultRequestOptions(options)
    } catch (e: Exception) {
        Log.e("initGlide:", e.message)
    }
    return requestManager
}