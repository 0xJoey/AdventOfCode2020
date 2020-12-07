package day3

fun main() {
    var lineIn: String?

    var map = ArrayList<String>();

    while(true) {
        lineIn = readLine()
        if(lineIn.isNullOrEmpty()) {
            break
        } else {
            map.add(lineIn)
        }
    }

    var strLen = map[0].length;

    var x = 0
    var trees = 0

    for(str in map) {
        if(str[x] == '#') {
            trees++
        }
        x += 3
        x %= strLen
    }
    println(trees)
}