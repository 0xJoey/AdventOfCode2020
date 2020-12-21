package day21

import readFromFile

fun main() {
    println(day21b())
}

fun day21b(): String {
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

    val allIngredients = ingredientsAllergens
        .flatMap { (ingredients, _) -> ingredients }
        .toMutableSet()

    val allergens = allergensToIngredients
        .toList()
        .sortedBy { (_, ingredients) ->
            ingredients.size
        }
        .map { (a, i) ->
            val isect = i.intersect(allIngredients)
            allIngredients.remove(isect.first())
            a to isect.first()
        }

    return allergens
        .sortedBy { (allergen, _) -> allergen }
        .joinToString(separator = ",") { (_, ingredient) -> ingredient }
}