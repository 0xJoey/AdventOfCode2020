package day11

import readFromFile

fun day11a(): String {

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
            var adjacent = 0

            if (input[y][x-1]   == '#') adjacent++
            if (input[y][x+1]   == '#') adjacent++
            if (input[y-1][x-1] == '#') adjacent++
            if (input[y-1][x+1] == '#') adjacent++
            if (input[y+1][x-1] == '#') adjacent++
            if (input[y+1][x+1] == '#') adjacent++
            if (input[y-1][x]   == '#') adjacent++
            if (input[y+1][x]   == '#') adjacent++

            if(char == 'L' && adjacent == 0) {
                output[y][x] = '#'
                changed = true
            } else if(char == '#' && adjacent >= 4) {
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