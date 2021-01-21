package com.example.halfway.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.halfway.R
import com.example.halfway.adapters.PagerAdapter
import com.example.halfway.databinding.ActivityRecipeDetailBinding
import com.example.halfway.ui.fragments.RecipeIngredientsFragment
import com.example.halfway.ui.fragments.RecipeInstructionsFragment
import com.example.halfway.ui.fragments.RecipesOverviewFragment

class RecipeDetailsActivity : AppCompatActivity() {
    private val TAG = "FactActivity"

    private lateinit var binding: ActivityRecipeDetailBinding
    private val args by navArgs<RecipeDetailsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            setSupportActionBar(toolbar)
            toolbar.title = "Recipe Details"
            toolbar.setTitleTextColor(
                ContextCompat.getColor(
                    this@RecipeDetailsActivity,
                    R.color.colorOnPrimaryDark
                )
            )
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            viewPager.adapter = initRecipeActivity()
            tabLayout.setupWithViewPager(viewPager)
        }

    }

    private fun initRecipeActivity(): PagerAdapter {
        val fragments = ArrayList<Fragment>()
        fragments.add(RecipesOverviewFragment())
        fragments.add(RecipeIngredientsFragment())
        fragments.add(RecipeInstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("OverView")
        titles.add("Ingredients")
        titles.add("Instructions")

        val bundle = Bundle()
        bundle.putSerializable("recipeData", args.result)

        return PagerAdapter(
            bundle,
            fragments,
            titles,
            supportFragmentManager
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}