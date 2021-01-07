package aoc2020.day20

import AoCDay
import java.io.File
import java.util.*
import kotlin.math.roundToInt

class JurassicJigsaw: AoCDay {
    private class Tile(val id: Long, var grid: List<List<Boolean>>) {
        val connections = Array<Long?>(4) { null }

        operator fun get(x: Int, y: Int): Boolean = grid[y][x]

        fun removeBorder() {
            grid = grid
                .drop(1)
                .dropLast(1)
                .map {
                    it
                        .drop(1)
                        .dropLast(1)
                }
        }

        fun getBorders(): List<List<Boolean>> {
            val top = grid.first()
            val right = grid.map {
                it.last()
            }
            val bottom = grid.last().reversed()
            val left = grid.map {
                it.first()
            }.reversed()
            return listOf(top, right, bottom, left)
        }

        fun rotateClockWise(times: Int = 1) {
            repeat(times) {
                grid = grid.flatMap { it.withIndex() }.groupBy({ (i, _) -> i }, { (_, v) -> v }).map { (_, v) -> v.reversed() }
            }
        }

        fun flipHorizontal() {
            grid = grid.map { it.reversed() }
        }

        fun flipVertical() {
            grid = grid.reversed()
        }

        fun getConnection(i: Int): Long? {
            return connections[i]
        }

        fun setConnection(i: Int, l: Long) {
            connections[i] = l
        }
    }

    companion object {
        val OPPOSITES = arrayOf(2,3,0,1)
        val MONSTER = listOf("                  # ", "#    ##    ##    ###", " #  #  #  #  #  #   ")
        val MONSTER_LOCATIONS = MONSTER
            .mapIndexed { y, str ->
                str.mapIndexed { x, chr ->
                        if(chr == '#') Pair(x,y) else null
                    }
                    .filterNotNull()
            }.flatten()
        val MONSTER_NUMBLOCKS = MONSTER
            .flatMap {
                it.toList()
            }
            .count {
                it == '#'
            }
    }

    private val tiles = Scanner(File("./src/aoc2020/day20/in.txt"))
        .useDelimiter("\r\n\r\n")
        .asSequence()
        .map {
            it.split("\r\n")
        }
        .map {
            val id = it[0].substring(4).trim().removeSuffix(":").toLong()
            val grid = it.subList(1,it.size).map {gridLine ->
                gridLine.trim().toCharArray().toList().map {chr ->
                    chr == '#'
                }
            }
            Tile(id, grid)
        }
        .toList()

    override fun part1(): String {
        val image = tiles.connect()

        return (image.first().first()!!.id * image.first().last()!!.id * image.last().first()!!.id * image.last().last()!!.id).toString()
    }

    override fun part2(): String {
        val imageTile = combineImage(tiles.connect())

        return (imageTile.grid.sumBy { y ->
            y.count { it }
        } - allSeaMonsters(imageTile)* MONSTER_NUMBLOCKS).toString()
    }

    private fun allSeaMonsters(image: Tile): Int {
        repeat(4) {
            numSeaMonsters(image).let { if(it != 0) return it }

            image.flipVertical()
            numSeaMonsters(image).let { if(it != 0) return it }

            image.flipHorizontal()
            numSeaMonsters(image).let { if(it != 0) return it }

            image.flipVertical()
            numSeaMonsters(image).let { if(it != 0) return it }

            image.flipHorizontal()
            image.rotateClockWise()
        }
        return 0
    }

    private fun numSeaMonsters(image: Tile): Int {
        var count = 0
        (0 until image.grid.size-2).forEach { y ->
            (0 until image.grid.size-19).forEach { x ->
                if(hasSeaMonster(image, x, y)) count++
            }
        }
        return count
    }

    private fun hasSeaMonster(image: Tile, x: Int, y: Int): Boolean {
        return MONSTER_LOCATIONS.all { (sx, sy) ->
            image[x+sx, y+sy]
        }
    }

    private fun combineImage(image: Array<Array<Tile?>>): Tile {
        return Tile(0, image
            .map { row ->
                row
                    .flatMap { tile ->
                        tile!!.removeBorder()
                        tile.grid.withIndex()
                    }
                    .groupBy({ it.index }, { it.value })
                    .map { (_, v) ->
                        v.reduce { acc, l -> acc + l }
                    }
            }
            .reduce { acc, l -> acc + l })
    }

    private fun List<Tile>.connect(): Array<Array<Tile?>> {
        val idToTile = this.associateBy { it.id }
        val remaining = this.toMutableList()

        val candidates = Stack<Tile>()
        candidates.push(this.first())

        // Assign Connections
        while (candidates.isNotEmpty()) {
            val curTile = candidates.pop()
            remaining -= curTile
            val curBorders = curTile.getBorders()

            remaining.forEach { tile ->
                tile.getBorders().forEachIndexed { otherIndex, b ->
                    if (b in curBorders) {
                        val curIndex = curBorders.indexOf(b)
                        val rots = Math.floorMod(curIndex - otherIndex.opposite(), 4)

                        if (curTile.getConnection(curIndex) == null) {
                            val tile = idToTile[tile.id] ?: error("Tile ${tile.id} not found")

                            tile.rotateClockWise(rots)
                            if (curIndex == 0 || curIndex == 2) {
                                tile.flipHorizontal()
                            }
                            else {
                                tile.flipVertical()
                            }

                            connectTiles(curTile, tile, curIndex)

                            candidates.push(tile)
                        }
                    } else if (b.reversed() in curBorders) {
                        val curIndex = curBorders.indexOf(b.reversed())
                        val rots = Math.floorMod(curIndex - otherIndex.opposite(), 4)

                        if (curTile.getConnection(curIndex) == null) {
                            (idToTile[tile.id] ?: error("Tile ${tile.id} not found")).rotateClockWise(rots)

                            connectTiles(curTile, tile, curIndex)

                            candidates.push(tile)
                        }
                    }
                }
            }
        }

        // Build image
        val imageSize = kotlin.math.sqrt(this.size.toDouble()).roundToInt()
        val image = Array(imageSize) { Array<Tile?>(imageSize) { null } }

        var left: Tile? = idToTile.values
            .find {
                it.getConnection(0) == null && it.getConnection(3) == null
            }!!

        image.indices.forEach { y ->
            var right: Tile? = left!!
            image[y].indices.forEach { x ->
                image[y][x] = right
                right = idToTile[right!!.getConnection(1)]
            }
            left = idToTile[left!!.getConnection(2)]
        }

        return image
    }

    private fun connectTiles(a: Tile, b: Tile, direction: Int) {
        a.setConnection(direction, b.id)
        b.setConnection(direction.opposite(), a.id)
    }

    private fun Int.opposite(): Int {
        return OPPOSITES[this]
    }
}