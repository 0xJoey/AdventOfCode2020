package aoc2020.day17

import AoCDay
import aoc2020.readFromFile

class ConwayCubes: AoCDay {
    private data class Quad<T1, T2, T3, T4>(val t1: T1, val t2: T2, val t3: T3, val t4: T4)

    private val adjacencies3D: List<Triple<Int,Int,Int>> by lazy(this::create3DAdjacencies)
    private val adjacencies4D: List<Quad<Int,Int,Int,Int>> by lazy(this::create4DAdjacencies)

    private val initialState = readFromFile("day17")
        .map(String::toCharArray)
        .map(CharArray::toList)

    override fun part1(): String {
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

        findCharsInGrid(initialState, '#') { x, y ->
            space[0]!!.putIfAbsent(y, HashMap())
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

        repeat(6) {
            val newSpace = HashMap<Int, HashMap<Int, HashMap<Int, Int>>>()
            for (z in expand(rangeZ)) {
                newSpace[z] = HashMap()
                for (y in expand(rangeY)) {
                    newSpace[z]!![y] = HashMap()
                    for (x in expand(rangeX)) {
                        var nbs = 0
                        for((adjX,adjY,adjZ) in adjacencies3D) {
                            nbs += space[z+adjZ]?.get(y+adjY)?.get(x+adjX) ?: 0
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
                        }
                    }
                }
            }
            rangeX = expand(rangeX)
            rangeY = expand(rangeY)
            rangeZ = expand(rangeZ)
            space = newSpace
        }

        var totalCubes = 0

        for(z in rangeZ) {
            for(y in rangeY) {
                for(x in rangeX) {
                    if((space[z]?.get(y)?.get(x) ?: 0) == 1) {
                        totalCubes++
                    }
                }
            }
        }

        return totalCubes.toString()
    }

    override fun part2(): String {
        var space = HashMap<Int, HashMap<Int, HashMap<Int, HashMap<Int, Int>>>>()

        var rangeX = (0..0)
        var rangeY = (0..0)
        var rangeZ = (0..0)
        var rangeW = (0..0)

        space[0] = HashMap()

        findCharsInGrid(initialState, '#') { x, y ->
            space[0]!!.putIfAbsent(y, HashMap())
            space[0]!![y]!!.putIfAbsent(x, HashMap())
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
                            for((adjX, adjY, adjZ, adjW) in adjacencies4D) {
                                nbs += space[z+adjZ]?.get(y+adjY)?.get(x+adjX)?.get(w+adjW) ?: 0
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
                            }
                        }
                    }
                }
            }
            rangeX = expand(rangeX)
            rangeY = expand(rangeY)
            rangeZ = expand(rangeZ)
            rangeW = expand(rangeW)
            space = newSpace
        }

        var totalCubes = 0
        for(w in rangeW) {
            for(z in rangeZ) {
                for(y in rangeY) {
                    for(x in rangeX) {
                        if((space[z]?.get(y)?.get(x)?.get(w) ?: 0) == 1) {
                            totalCubes++
                        }
                    }
                }
            }
        }
        return totalCubes.toString()
    }

    private fun create3DAdjacencies(): List<Triple<Int,Int,Int>> {
        val permuts = ArrayList<Triple<Int,Int,Int>>()
        for(x in (-1..1)) {
            for(y in (-1..1)) {
                for(z in (-1..1)) {
                    if(z == 0 && y == 0 && x == 0) {
                        continue
                    }
                    permuts.add(Triple(x,y,z))
                }
            }
        }
        return permuts
    }

    private fun create4DAdjacencies(): List<Quad<Int,Int,Int,Int>> {
        val permuts = ArrayList<Quad<Int,Int,Int,Int>>()
        for(x in (-1..1)) {
            for(y in (-1..1)) {
                for(z in (-1..1)) {
                    for(w in (-1..1)) {
                        if(z == 0 && y == 0 && x == 0 && w == 0) {
                            continue
                        }
                        permuts.add(Quad(x,y,z,w))
                    }
                }
            }
        }
        return permuts
    }

    private fun expand(a: IntRange): IntRange {
        return (a.first-1..a.last+1)
    }

    private fun findCharsInGrid(grid: List<List<Char>>, toFind: Char, callback: (x: Int, y: Int) -> Unit) {
        for((y, row) in grid.withIndex()) {
            for((x, char) in row.withIndex()) {
                if(char == toFind) {
                    callback(x, y)
                }
            }
        }

    }
}

