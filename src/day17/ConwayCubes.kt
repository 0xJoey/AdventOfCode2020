package day17

import readFromFile

fun main() {
    println(day17a())
}

fun day17a(): String {
    val PERMUTS = ArrayList<Triple<Int,Int,Int>>()
    PERMUTS.add(Triple(-1,-1,-1))
    PERMUTS.add(Triple(-1,-1, 0))
    PERMUTS.add(Triple(-1,-1, 1))
    PERMUTS.add(Triple(-1, 0,-1))
    PERMUTS.add(Triple(-1, 0, 0))
    PERMUTS.add(Triple(-1, 0, 1))
    PERMUTS.add(Triple(-1, 1,-1))
    PERMUTS.add(Triple(-1, 1, 0))
    PERMUTS.add(Triple(-1, 1, 1))

    PERMUTS.add(Triple(0,-1,-1))
    PERMUTS.add(Triple(0,-1, 0))
    PERMUTS.add(Triple(0,-1, 1))
    PERMUTS.add(Triple(0, 0,-1))

    PERMUTS.add(Triple(0, 0, 1))
    PERMUTS.add(Triple(0, 1,-1))
    PERMUTS.add(Triple(0, 1, 0))
    PERMUTS.add(Triple(0, 1, 1))

    PERMUTS.add(Triple( 1,-1,-1))
    PERMUTS.add(Triple( 1,-1, 0))
    PERMUTS.add(Triple( 1,-1, 1))
    PERMUTS.add(Triple( 1, 0,-1))
    PERMUTS.add(Triple( 1, 0, 0))
    PERMUTS.add(Triple( 1, 0, 1))
    PERMUTS.add(Triple( 1, 1,-1))
    PERMUTS.add(Triple( 1, 1, 0))
    PERMUTS.add(Triple( 1, 1, 1))

    val initialState = readFromFile("day17")
        .map(String::toCharArray)
        .map(CharArray::toList)


    var space = HashMap<Int, HashMap<Int, HashMap<Int, Int>>>()

    var rangeX = (0..0)
    var rangeY = (0..0)
    var rangeZ = (0..0)

    space[0] = HashMap()
    for((y, row) in initialState.withIndex()) {
        space[0]!![y] = HashMap()
        for((x, char) in row.withIndex()) {
            if(char == '#') {
                space[0]!![y]!![x] = 1
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
        val newSpace = HashMap<Int, HashMap<Int, HashMap<Int, Int>>>()
        for (z in expand(rangeZ)) {
            newSpace[z] = HashMap()
            for (y in expand(rangeY)) {
                newSpace[z]!![y] = HashMap()
                for (x in expand(rangeX)) {
                    var nbs = 0
                    for((a,b,c) in PERMUTS) {
                        nbs += space[z+a]?.get(y+b)?.get(x+c) ?: 0
                    }

                    val curCube = space[z]?.get(y)?.get(x) ?: 0

                    if(curCube == 1) {
                        if(nbs < 2 || nbs > 3) {
                            newSpace[z]!![y]!!.remove(x)
                        } else {
                            newSpace[z]!![y]!![x] = 1
                        }
                    } else if(curCube == 0 && nbs == 3) {
                        newSpace[z]!![y]!![x] = 1

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

                        if(z < rangeZ.first) {
                            rangeZ = (z..rangeZ.last)
                        } else if(z > rangeZ.last) {
                            rangeZ = (rangeZ.first..z)
                        }
                    }
                }
            }
        }
        //oldSpace = space
        space = newSpace
    }

    var totalCubes = 0

    for(z in expand(rangeZ)) {
        //println("z=$z")
        for(y in expand(rangeY)) {
            for(x in expand(rangeX)) {
                //val oldSpaceCube = (oldSpace[z]?.get(y)?.get(x) ?: 0) == 1
                val newSpaceCube = (space[z]?.get(y)?.get(x) ?: 0) == 1
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

    return totalCubes.toString()
}

private fun expand(a: IntRange): IntRange {
    return (a.first-1..a.last+1)
}