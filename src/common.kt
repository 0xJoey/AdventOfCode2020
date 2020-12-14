import java.io.File

fun readFromFile(day: String): List<String> {
    return File("./src/$day/in.txt")
        .readLines()
        .map(String::trim)
}

fun readAnswers(day: String): Pair<String, String> {
    val answers = File("./src/$day/out.txt")
        .readLines()
        .map(String::trim)
    return Pair(answers[0], answers[1])
}