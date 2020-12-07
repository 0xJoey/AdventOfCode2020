package day2

fun main() {
    var lineIn: String?

    var verified = 0;

    while(true) {
        lineIn = readLine()
        if(lineIn.isNullOrEmpty()) {
            break
        } else {
            if(verify(lineIn))
                verified++
        }
    }

    println(verified);
}

private fun verify(input: String): Boolean {
    var step = input.split("-")

    val min = step[0].toInt()
    step = step[1].split(" ")
    val max = step[0].toInt()
    val key = step[1][0]
    val password = step[2]

    val repeats = password.filter { it == key }.count()
    return repeats in (min..max)
}