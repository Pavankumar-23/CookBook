package com.example.halfway.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.halfway.R
import com.example.halfway.databinding.FragmentRecipeOverviewBinding
import com.example.halfway.model.Result
import com.example.halfway.util.setDrawable
import com.example.halfway.util.setImage
import org.jsoup.Jsoup
import java.util.*

class RecipesOverviewFragment : Fragment() {

    private var _binding: FragmentRecipeOverviewBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeOverviewBinding.inflate(inflater)

        val args: Result = arguments?.getSerializable("recipeData") as Result
        bindView(args)

        return binding.root
    }

    private fun bindView(recipe: Result) {

        recipe.let {
            binding.apply {
                ivRecipeImage.setImage(recipe.image, root)

                tvRecipeTitle.text = recipe.title

                tvCuisineType.text =
                    recipe.dishTypes.joinToString(",", transform = { it.capitalize(Locale.ROOT) })

                tvRecipeScore.text = String.format("%d%s", recipe.healthScore.toInt(), "%")

                val hours: Int = recipe.readyInMinutes / 60 //since both are ints, you get an int
                val minutes: Int = recipe.readyInMinutes % 60
                if (hours > 0) {
                    tvRecipeDuration.text = String.format("%d%s %02d%s", hours, "h", minutes, "min")
                } else {
                    tvRecipeDuration.text = String.format("%02d%s", minutes, "min")
                }

                icRecipeFoodType.setDrawable(if (recipe.vegetarian) R.drawable.ic_veg else R.drawable.ic_non_veg)

                tvType.text = if (recipe.vegetarian) "Veg" else "Non-Veg"

                tvRecipeServings.text = String.format("%d %s", recipe.servings, "Persons")

                if (recipe.dairyFree) {
                    ivIsDiaryFree.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.holo_green_light
                        )
                    )
                    tvDiaryFree.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.holo_green_light
                        )
                    )
                }
                if (recipe.glutenFree) {
                    ivIsGluten.setColorFilter(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.holo_green_light
                        )
                    )
                    tvGluten.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            android.R.color.holo_green_light
                        )
                    )
                }
                if (recipe.vegan) {
                    ivIsVegan.setColorFilter(
                        ContextCompat.getColor(requireContext(), android.R.color.holo_green_light)
                    )
                    tvVegan.setTextColor(
                        ContextCompat.getColor(requireContext(), android.R.color.holo_green_light)
                    )
                }

                tvRecipeDescription.text = Jsoup.parse(recipe.summary).text()

                recipe.nutrition.nutrients.let { nutrient ->
                    val cal = nutrient[0]
                    val fat = nutrient[1]
                    val carbs = nutrient[3]
                    tvTotKcal.text = String.format("%s %s", cal.amount.toString(), cal.unit)
                    tvFat.text = String.format("%s %s", fat.amount.toString(), fat.unit)
                    tvCarbs.text = String.format("%s %s", carbs.amount.toString(), carbs.unit)
                }

                tvSourceName.text = recipe.sourceName
            }
        }
    }

}