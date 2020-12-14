package day3

import readFromFile

fun day3b(): String {
    val map = readFromFile("day3")

    val slopes = ArrayList<Pair<Int,Int>>()
    slopes.add(Pair(1,1))
    slopes.add(Pair(3,1))
    slopes.add(Pair(5,1))
    slopes.add(Pair(7,1))
    slopes.add(Pair(1,2))

    return slopes.map { calcTrees(map, it) }.reduce{it, acc -> (it*acc)}.toString()
}

fun calcTrees(map: List<String>, slope: Pair<Int,Int>): Long {
    val strLen = map[0].length

    var x = 0
    var y = 0
    var trees = 0L

    while(y < map.size) {
        if(map[y][x] == '#') {
            trees++
        }
        x+=slope.first
        x%=strLen
        y+=slope.second
    }
    return trees
}