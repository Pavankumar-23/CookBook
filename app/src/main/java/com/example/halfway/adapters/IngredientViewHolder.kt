package com.example.halfway.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.halfway.R
import com.example.halfway.databinding.IngredientItemViewBinding
import com.example.halfway.listeners.OnFactClickListener
import com.example.halfway.model.ExtendedIngredient
import com.example.halfway.util.Constants.Companion.IMAGE_BASE_URL
import com.example.halfway.util.setImage
import java.util.*

class IngredientViewHolder(
    private val view: IngredientItemViewBinding
) : RecyclerView.ViewHolder(view.root) {

    fun onBind(category: ExtendedIngredient) {
        view.apply {
            ivIngredient.setImage(IMAGE_BASE_URL + category.image, root)
            tvIngredient.text = category.name.capitalize(Locale.ROOT)
            tvConsistency.text = category.consistency
            tvOriginal.text = category.original
        }
    }
}