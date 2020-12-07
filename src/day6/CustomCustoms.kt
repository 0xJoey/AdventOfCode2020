package day6

import java.io.File
import java.util.*

fun main() {
    println(Scanner(File("./src/day6/in.txt"))
        .useDelimiter("\n\n")
        .asSequence()
        .map { it.split("\n") }
        .map{ findNumAnswers(it) }
        .sum())
}

private fun findNumAnswers(group: List<String>): Int {
    val answers = HashSet<Char>()
    for(form in group) {
        for(char in form.toCharArray()) {
            answers.add(char)
        }
    }
    return answers.size
}