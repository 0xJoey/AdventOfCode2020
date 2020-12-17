package day17

import readFromFile

fun main() {
    println(day17b())
}

fun day17b(): String {
    data class NTuple4<T1, T2, T3, T4>(val t1: T1, val t2: T2, val t3: T3, val t4: T4)

    val PERMUTS = ArrayList<NTuple4<Int,Int,Int,Int>>()

    for(x in (-1..1)) {
        for(y in (-1..1)) {
            for(z in (-1..1)) {
                for(w in (-1..1)) {
                    if(z == 0 && y == 0 && x == 0 && w == 0) {
                        continue
                    }
                    PERMUTS.add(NTuple4(z,y,x,w))
                }
            }
        }
    }

    val initialState = readFromFile("day17")
        .map(String::toCharArray)
        .map(CharArray::toList)


    var space = HashMap<Int, HashMap<Int, HashMap<Int, HashMap<Int, Int>>>>()

    var rangeX = (0..0)
    var rangeY = (0..0)
    var rangeZ = (0..0)
    var rangeW = (0..0)

    space[0] = HashMap()
    for((y, row) in initialState.withIndex()) {
        space[0]!![y] = HashMap()
        for((x, char) in row.withIndex()) {
            space[0]!![y]!![x] = HashMap()
            if(char == '#') {
                space[0]!![y]!![x]!![0] = 1
                if(x < rangeX.first) {
                    rangeX = (x..rangeX.last)
                } else if(x > rangeX.last) {
                    rangeX = (rangeX.first..x)
                }

                if(y < rangeY.first) {
                    rangeY = (y..rangeY.last)
                } else if(y > rangeY.last) {
                    rangeY = (rangeY.first..y)
                }
            }
        }
    }

    //var oldSpace = space

    repeat(6) {
        val newSpace = HashMap<Int, HashMap<Int, HashMap<Int, HashMap<Int, Int>>>>()
        for (z in expand(rangeZ)) {
            newSpace[z] = HashMap()
            for (y in expand(rangeY)) {
                newSpace[z]!![y] = HashMap()
                for (x in expand(rangeX)) {
                    newSpace[z]!![y]!![x] = HashMap()
                    for(w in expand(rangeW)) {
                        var nbs = 0
                        for((a,b,c,d) in PERMUTS) {
                            nbs += space[z+a]?.get(y+b)?.get(x+c)?.get(w+d) ?: 0
                        }

                        val curCube = space[z]?.get(y)?.get(x)?.get(w) ?: 0

                        if(curCube == 1) {
                            if(nbs < 2 || nbs > 3) {
                                newSpace[z]!![y]!![x]!!.remove(w)
                            } else {
                                newSpace[z]!![y]!![x]!![w] = 1
                            }
                        } else if(curCube == 0 && nbs == 3) {
                            newSpace[z]!![y]!![x]!![w] = 1
                            /*if(x < rangeX.first) {
                                rangeX = (x..rangeX.last)
                            } else if(x > rangeX.last) {
                                rangeX = (rangeX.first..x)
                            }

                            if(y < rangeY.first) {
                                rangeY = (y..rangeY.last)
                            } else if(y > rangeY.last) {
                                rangeY = (rangeY.first..y)
                            }

                            if(z < rangeZ.first) {
                                rangeZ = (z..rangeZ.last)
                            } else if(z > rangeZ.last) {
                                rangeZ = (rangeZ.first..z)
                            }

                            if(w < rangeW.first) {
                                rangeW = (w..rangeW.last)
                            } else if(w > rangeW.last) {
                                rangeW = (rangeW.first..w)
                            }*/
                        }
                    }
                }
            }
        }
        rangeX = expand(rangeX)
        rangeY = expand(rangeY)
        rangeZ = expand(rangeZ)
        rangeW = expand(rangeW)
        //oldSpace = space
        space = newSpace
    }

    var totalCubes = 0

    for(w in expand(rangeW)) {
        for(z in expand(rangeZ)) {
            //println("z=$z,w=$w")
            for(y in expand(rangeY)) {
                for(x in expand(rangeX)) {
                    //val oldSpaceCube = (oldSpace[z]?.get(y)?.get(x)?.get(w) ?: 0) == 1
                    val newSpaceCube = (space[z]?.get(y)?.get(x)?.get(w) ?: 0) == 1
                    /*val symbol = when {
                        oldSpaceCube&&newSpaceCube -> '#'
                        oldSpaceCube&&!newSpaceCube -> 'X'
                        !oldSpaceCube&&newSpaceCube -> 'O'
                        !oldSpaceCube&&!newSpaceCube -> '.'
                        else -> '!'
                    }
                    print(symbol)
                    */
                    if(newSpaceCube) {
                        totalCubes++
                    }
                }
                //println()
            }
            //println()
        }
    }




    return totalCubes.toString()
}

private fun expand(a: IntRange): IntRange {
    return (a.first-1..a.last+1)
}