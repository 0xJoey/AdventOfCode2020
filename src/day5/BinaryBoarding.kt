package day5

import readFromFile

fun day5a(): String {
    return readFromFile("day5")
        .map{calcId(it)}
        .max()
        .toString()
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
    for(char in str.toCharArray()) {
        value = value.shl(1)
        if(char == symbols.second) {
            value += 1
        }
    }
    return value
}