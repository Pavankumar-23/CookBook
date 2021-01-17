package com.example.halfway.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.halfway.databinding.FactItemViewBinding
import com.example.halfway.listeners.OnFactClickListener
import com.example.halfway.model.Result

class RecipeAdapter(private val listener: OnFactClickListener) :
    PagingDataAdapter<Result, FactsViewHolder>(DATA_COMPARATOR) {

    override fun onBindViewHolder(holder: FactsViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.onBind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactsViewHolder {
        val view: FactItemViewBinding =
            FactItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FactsViewHolder(view, listener)
    }

    fun getSelectedRecipe(position: Int): Result? {
        val clickedItem = getItem(position)
        if (clickedItem != null) {
            return clickedItem
        }
        return null
    }

    companion object {
        private val DATA_COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem == newItem

        }
    }
}