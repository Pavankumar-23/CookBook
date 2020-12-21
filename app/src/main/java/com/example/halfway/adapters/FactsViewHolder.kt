package com.example.halfway.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.example.halfway.R
import com.example.halfway.databinding.FactItemViewBinding
import com.example.halfway.listeners.OnFactClickListener
import com.example.halfway.model.Facts
import com.example.halfway.util.setImage

class FactsViewHolder(
    private val view: View,
    private val listener: OnFactClickListener,
    private val viewPreloadSizeProvider: ViewPreloadSizeProvider<String>
) : RecyclerView.ViewHolder(view), View.OnClickListener {

    private val binding = FactItemViewBinding.bind(view)

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.bt_fav -> listener.onFactClick(adapterPosition)
            else -> listener.onFactClick(adapterPosition)
        }
    }

    init {
        view.setOnClickListener(this)
    }

    fun onBind(fact: Facts) {
        binding.ivFactImage.setImage(fact.imageUrl)
        binding.btFav.setOnClickListener(this)
        binding.tvTitle.text = fact.title
        binding.tvDesc.text = fact.description
        viewPreloadSizeProvider.setView(binding.ivFactImage)
    }
}