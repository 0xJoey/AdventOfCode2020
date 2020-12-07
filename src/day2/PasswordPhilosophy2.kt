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

    val pos1 = step[0].toInt()-1
    step = step[1].split(" ")
    val pos2 = step[0].toInt()-1
    val key = step[1][0]
    val password = step[2]


    return (password[pos1] == key).xor((password[pos2] == key))
}