package aoc2020.day21

import aoc2020.readFromFile
import AoCDay

class AllergenAssessment: AoCDay {
    private val ingredientsAllergens = readFromFile("day21")
        .map { line ->
            val (ingredientStr, allergenStr) = line
                .removeSuffix(")")
                .split("(contains")
                .map(String::trim)
            val ingredients = ingredientStr.split(" ")
            val allergens = allergenStr.split(", ")
            ingredients to allergens
        }
        .toMap()

    private val allergensToIngredients = ingredientsAllergens
        .flatMap { (ingredients, allergens) ->
            allergens.map { allergen ->
                allergen to ingredients
            }
        }
        .groupBy { it.first }
        .map { (allergen, possibleIngredients) ->
            val ingredients = possibleIngredients
                .map { (_, ingredients) ->
                    ingredients.toSet()
                }
                .reduceRight { v, acc ->
                    acc.intersect(v)
                }
            allergen to ingredients
        }
        .toMap()

    private val allIngredients = ingredientsAllergens
        .flatMap { (ingredients, _) -> ingredients }
        .toSet()

    override fun part1(): String {
        val allergenic = allergensToIngredients
            .flatMap { (_, ingredients) ->
                ingredients
            }
            .toSet()

        val nonAllergenic = (allIngredients - allergenic)

        return ingredientsAllergens
            .flatMap { (ingredients, _) -> ingredients }
            .count { it in nonAllergenic }
            .toString()
    }

    override fun part2(): String {
        val ingredientSet = allIngredients.toMutableSet()

        val allergens = allergensToIngredients
            .toList()
            .sortedBy { (_, ingredients) ->
                ingredients.size
            }
            .map { (a, i) ->
                val isect = i.intersect(ingredientSet)
                ingredientSet.remove(isect.first())
                a to isect.first()
            }

        return allergens
            .sortedBy { (allergen, _) -> allergen }
            .joinToString(",") { (_, ingredient) -> ingredient }
    }

}
