package day5

import java.io.File
import java.util.*

fun main() {
    println(File("./src/day5/in.txt")
        .readLines()
        .map{calcId(it)}
        .max())
}

private fun calcId(str: String): Int {
    val row = str.substring((0..6))
    val col = str.substring((7..9))
    val rowNo = strToBin(row, Pair('F', 'B'))
    val colNo = strToBin(col, Pair('L', 'R'))
    return rowNo * 8 + colNo
}

private fun strToBin(str: String, symbols: Pair<Char,Char>): Int {
    var value = 0
    println(str)
    for(char in str.toCharArray()) {
        value = value.shl(1)
        if(char == symbols.second) {
            value += 1
        }
    }
    return value
}