package day10

import readFromFile


fun day10a(): String {

    val jolts = readFromFile("day10")
        .map(String::toInt)
        .sorted()

    val diffs = ArrayList<Int>()
    for(i in 0..(jolts.lastIndex-1)) {
        val diff = jolts[i+1] - jolts[i];
        diffs.add(diff)
    }

    val ones = diffs.filter { it==1 }.count()+1
    val threes = diffs.filter { it==3 }.count()+1
    return (ones*threes).toString()
}