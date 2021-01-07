package aoc2020.day11

import AoCDay
import aoc2020.readFromFile

class SeatingSystem: AoCDay {

    private var seats = readFromFile("day11")
        .map(String::toCharArray)
        .map(CharArray::toList)
        .map {
            listOf('.') + it + listOf('.')
        }
        .toList()

    init {
        val dots = mutableListOf<Char>()
        repeat(seats.first().size) {
            dots.add('.')
        }

        seats = (listOf(dots) + seats + listOf(dots))
    }

    override fun part1(): String {
        var changed: Boolean
        var allSeats: List<List<Char>> = seats
        do {
            val (c, s) = part1Step(allSeats)
            changed = c
            allSeats = s
        } while(changed)
        return allSeats
            .map {
                it.filter {char ->
                    char == '#'
                }.size
            }
            .sum()
            .toString()
    }

    override fun part2(): String {
        var changed: Boolean
        var allSeats: List<List<Char>> = seats
        do {
            val (c, s) = part2Step(allSeats)
            changed = c
            allSeats = s
        } while(changed)
        return allSeats
            .map {
                it.filter {char ->
                    char == '#'
                }.size
            }
            .sum()
            .toString()
    }

    private fun part1Step(input: List<List<Char>>): Pair<Boolean,List<List<Char>>> {
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

                if (input[y][x-1]   == '#') seen++
                if (input[y][x+1]   == '#') seen++
                if (input[y-1][x-1] == '#') seen++
                if (input[y-1][x+1] == '#') seen++
                if (input[y+1][x-1] == '#') seen++
                if (input[y+1][x+1] == '#') seen++
                if (input[y-1][x]   == '#') seen++
                if (input[y+1][x]   == '#') seen++

                if(char == 'L' && seen == 0) {
                    output[y][x] = '#'
                    changed = true
                } else if(char == '#' && seen >= 4) {
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


    private fun part2Step(input: List<List<Char>>): Pair<Boolean,List<List<Char>>> {
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

                if(checkDiagonal(startPos,Pair(0, -1),input)) seen++
                if(checkDiagonal(startPos,Pair(0,  1),input)) seen++
                if(checkDiagonal(startPos,Pair(-1,-1),input)) seen++
                if(checkDiagonal(startPos,Pair(-1, 1),input)) seen++
                if(checkDiagonal(startPos,Pair(-1, 0),input)) seen++
                if(checkDiagonal(startPos,Pair(1,  0),input)) seen++
                if(checkDiagonal(startPos,Pair(1, -1),input)) seen++
                if(checkDiagonal(startPos,Pair(1,  1),input)) seen++

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

    private fun checkDiagonal(start: Pair<Int, Int>, dir: Pair<Int, Int>, arr: List<List<Char>>): Boolean {
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
}