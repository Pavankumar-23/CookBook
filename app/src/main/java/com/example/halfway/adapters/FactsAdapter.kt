package com.example.halfway.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.example.halfway.R
import com.example.halfway.listeners.OnFactClickListener
import com.example.halfway.model.Facts
import com.example.halfway.util.FactsDiffUtil
import com.example.halfway.util.Util.Companion.nullToEmpty
import java.util.*

class FactsAdapter(
    private val listener: OnFactClickListener,
    private val requestManager: RequestManager,
    private val preloadSizeProvider: ViewPreloadSizeProvider<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ListPreloader.PreloadModelProvider<String> {


    private var factList: List<Facts> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.fact_item_view, parent, false)
        return FactsViewHolder(view, listener, preloadSizeProvider)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FactsViewHolder).onBind(factList[position])
    }

    override fun getItemCount(): Int {
        return factList.size
    }

    override fun getPreloadItems(position: Int): MutableList<String> {
        var url = ""
        if (factList.isNotEmpty()) {
            url = nullToEmpty(factList[position].imageUrl)
            if (TextUtils.isEmpty(url)) {
                return Collections.emptyList()
            }
        }
        return Collections.singletonList(url)
    }

    override fun getPreloadRequestBuilder(item: String): RequestBuilder<*> {
        return requestManager.load(item)
    }

    fun getSelectedRecipe(position: Int): Facts? {
        if (factList.isNotEmpty()) {
            return factList[position]
        }
        return null
    }

    fun setFacts(recipes: List<Facts>) {
        val factsDiffUtil = FactsDiffUtil(factList, recipes)
        val diffUtilFact = DiffUtil.calculateDiff(factsDiffUtil)
        factList = recipes
        diffUtilFact.dispatchUpdatesTo(this)
    }
}