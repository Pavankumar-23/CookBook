package com.example.halfway.util

import android.view.View
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.halfway.R


fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}

fun ImageView.setImage(imageUrl: String?, itemView: View, width: Int, height: Int) {
    Glide.with(itemView)
        .load(imageUrl)
        .dontTransform()
        .override(width, height)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(R.drawable.ic_launcher_foreground)
        .into(this)
}

fun ImageView.setImageResource(imageResource: Int, itemView: View) {
    Glide.with(itemView)
        .load(imageResource)
        .centerCrop()
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(R.drawable.ic_launcher_foreground)
        .into(this)
}

fun ImageView.setDrawable(resId: Int) {
    setImageResource(resId)
    scaleType = ImageView.ScaleType.CENTER_CROP
    elevation = 3F
}
