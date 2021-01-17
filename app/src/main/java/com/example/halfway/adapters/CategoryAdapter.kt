package com.example.halfway.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.halfway.databinding.CategoryViewItemBinding
import com.example.halfway.databinding.FactItemViewBinding
import com.example.halfway.databinding.IngredientItemViewBinding
import com.example.halfway.databinding.InstructionItemViewBinding
import com.example.halfway.listeners.OnFactClickListener
import com.example.halfway.model.ExtendedIngredient
import com.example.halfway.model.RecipeCategory
import com.example.halfway.model.Result
import com.example.halfway.model.Step
import com.example.halfway.util.FactsDiffUtil

class CategoryAdapter(
    private val listener: OnFactClickListener?,
    private val itemType: Int
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listItem: List<Any> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val attachToParent = false
        return when (itemType) {
            0 -> CategoryViewHolder(
                CategoryViewItemBinding.inflate(layoutInflater, parent, attachToParent), listener
            )
            1 -> IngredientViewHolder(
                IngredientItemViewBinding.inflate(layoutInflater, parent, attachToParent)
            )
            2 -> InstructionsViewHolder(
                InstructionItemViewBinding.inflate(layoutInflater, parent, attachToParent)
            )
            3 -> FactsViewHolder(
                FactItemViewBinding.inflate(layoutInflater, parent, attachToParent), listener
            )
            else -> null
        } as RecyclerView.ViewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CategoryViewHolder -> holder.onBind(listItem[position] as RecipeCategory)
            is IngredientViewHolder -> holder.onBind(listItem[position] as ExtendedIngredient)
            is InstructionsViewHolder -> holder.onBind(listItem[position] as Step)
            is FactsViewHolder -> holder.onBind(listItem[position] as Result)
        }
    }

    override fun getItemCount(): Int = listItem.size

    fun setIngredients(ingredients: List<Any>) {
        val factsDiffUtil = FactsDiffUtil(listItem, ingredients)
        val diffUtilFact = DiffUtil.calculateDiff(factsDiffUtil)
        listItem = ingredients
        diffUtilFact.dispatchUpdatesTo(this)
    }

    fun getSelectedItem(position: Int) = listItem[position]

}

