package com.example.halfway.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.halfway.R
import com.example.halfway.databinding.CategoryViewItemBinding
import com.example.halfway.listeners.OnFactClickListener
import com.example.halfway.model.RecipeCategory
import java.util.*

class CategoryViewHolder(
    private val view: CategoryViewItemBinding,
    private val listener: OnFactClickListener?
) : RecyclerView.ViewHolder(view.root), View.OnClickListener {

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.bt_recipe_fav -> listener?.onFactClick(absoluteAdapterPosition, p0)
            else -> listener?.onFactClick(absoluteAdapterPosition, p0)
        }
    }

    init {
        view.root.setOnClickListener(this)
    }

    fun onBind(category: RecipeCategory) {
        view.apply {
            imageView2.setImageResource(
                when (category.categoryImage.toLowerCase(Locale.ROOT)) {
                    "indian" -> R.drawable.indian_cuisine
                    "mexican" -> R.drawable.mexican_cuisine
                    "thai" -> R.drawable.thai_cuisine
                    "chinese" -> R.drawable.chinese_cuisine
                    "french" -> R.drawable.french_cuisine
                    "german" -> R.drawable.german_cuisine
                    else -> R.drawable.indian_cuisine
                }
            )
            textView.setText(category.categoryName)
        }
    }
}