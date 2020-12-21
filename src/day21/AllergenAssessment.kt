package day21

import readFromFile

fun main() {
    println(day21a())
}

fun day21a(): String {
    val ingredientsAllergens = readFromFile("day21")
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

    val allergensToIngredients = ingredientsAllergens
        .flatMap { (ingredients, allergens) ->
            allergens.map { allergen ->
                allergen to ingredients
            }
        }
        .groupBy { it.first }
        .map { (allergen, possibleIngredients) ->
            val p = possibleIngredients
                .map { (_, ingredients) ->
                    ingredients.toSet()
                }
                .reduceRight { v, acc ->
                    acc.intersect(v)
                }
            allergen to p
        }
        .toMap()

    val allergenic = allergensToIngredients
        .flatMap { (_, ingredients) ->
            ingredients
        }
        .toSet()

    val allIngredients = ingredientsAllergens
        .flatMap { (ingredients, _) -> ingredients }
        .toSet()

    val nonAllergenic = (allIngredients - allergenic)

    return ingredientsAllergens
        .flatMap { (ingredients, _) -> ingredients }
        .count { it in nonAllergenic }
        .toString()
}