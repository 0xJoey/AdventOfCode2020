package day11

import readFromFile

fun day11b(): String {

    val seats = readFromFile("day11")
        .map(String::toCharArray)
        .map(CharArray::toList)
        .map {
            listOf('.') + it + listOf('.')
        }
        .toList()

    val dots = ArrayList<Char>()
    repeat(seats[0].size) {
        dots.add('.')
    }
    val seatArray = (listOf(dots) + seats + listOf(dots))

    return countSeats(seatArray).toString()
}

private fun countSeats(input: List<List<Char>>): Int {
    var changed: Boolean
    var seats: List<List<Char>> = input
    do {
        val (c, s) = step(seats)
        changed = c
        seats = s
        /*for(row in s) {
            for(char in row) {
                print(char)
            }
            println()
        }
        println("---")*/
    } while(changed)
    return seats
        .map {
            it.filter {char ->
                char == '#'
            }.size
        }
        .sum()
}

private fun step(input: List<List<Char>>): Pair<Boolean,List<List<Char>>> {
    val output = ArrayList<ArrayList<Char>>()
    var changed = false
    repeat(input.size) {
        val list = ArrayList<Char>()
        repeat(input[0].size) {
            list.add('.')
        }
        output.add(list)
    }

    for((y, row) in input.withIndex()) {
        for((x, char) in row.withIndex()) {
            if(char == '.') continue
            var seen = 0
            val startPos = Pair(x,y)

            if(checkDir(startPos,Pair(0, -1),input)) seen++
            if(checkDir(startPos,Pair(0,  1),input)) seen++
            if(checkDir(startPos,Pair(-1,-1),input)) seen++
            if(checkDir(startPos,Pair(-1, 1),input)) seen++
            if(checkDir(startPos,Pair(-1, 0),input)) seen++
            if(checkDir(startPos,Pair(1,  0),input)) seen++
            if(checkDir(startPos,Pair(1, -1),input)) seen++
            if(checkDir(startPos,Pair(1,  1),input)) seen++

            if(char == 'L' && seen == 0) {
                output[y][x] = '#'
                changed = true
            } else if(char == '#' && seen >= 5) {
                output[y][x] = 'L'
                changed = true
            } else if(char == 'X') {
                println("Err")
            } else {
                output[y][x] = char
            }
        }
    }
    return Pair(changed, output)
}

private fun checkDir(start: Pair<Int, Int>, dir: Pair<Int, Int>, arr: List<List<Char>>): Boolean {
    var next = Pair(start.first+dir.first, start.second+dir.second)
    while((next.first > 0 && next.first < arr[0].size) && (next.second > 0 && next.second < arr.size)) {
        when {
            arr[next.second][next.first] == '#' -> return true
            arr[next.second][next.first] == 'L' -> return false
            else -> next = Pair(next.first+dir.first, next.second+dir.second)
        }
    }
    return false
}