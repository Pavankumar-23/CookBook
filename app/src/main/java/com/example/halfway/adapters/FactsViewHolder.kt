package com.example.halfway.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.halfway.R
import com.example.halfway.databinding.FactItemViewBinding
import com.example.halfway.listeners.OnFactClickListener
import com.example.halfway.model.Result
import com.example.halfway.util.setDrawable
import com.example.halfway.util.setImage
import java.util.*

class FactsViewHolder(
    private val view: FactItemViewBinding,
    listener: OnFactClickListener?,
) : RecyclerView.ViewHolder(view.root), View.OnClickListener {

    private val itemClickListener = listener!!

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.bt_recipe_fav -> itemClickListener.onFactClick(absoluteAdapterPosition, p0)
            else -> itemClickListener.onFactClick(absoluteAdapterPosition, p0)
        }
    }

    fun onBind(recipe: Result) {
        view.apply {

            root.setOnClickListener(this@FactsViewHolder)
            btFav.setOnClickListener(this@FactsViewHolder)
            ivFactImage.setImage(recipe.image, itemView)
            tvTitle.text = recipe.title
            tvDesc.text = recipe.diets.joinToString(", ") { it.capitalize(Locale.ROOT) }
            tvHealthScore.text = String.format("%d%s", recipe.healthScore.toInt(), "%")
            val hours: Int = recipe.readyInMinutes / 60
            val minutes: Int = recipe.readyInMinutes % 60
            tvDuration.text = if (hours > 0)
                String.format("%d%s %02d%s", hours, "h", minutes, "min") else
                String.format("%02d%s", minutes, "min")
            val imgResource = if (recipe.vegetarian) R.drawable.ic_veg else R.drawable.ic_non_veg
            icFoodType.setDrawable(imgResource)
            tvType.text = if (recipe.vegetarian) "Veg" else "Non-Veg"

        }
    }
}