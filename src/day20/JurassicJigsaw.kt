package day20

import java.io.File
import java.lang.Math.floorMod
import java.util.*
import kotlin.math.roundToInt

fun main() {
    println(day20a())
}

fun day20a(): String {
    val lines = Scanner(File("./src/day20/in.txt"))
        .useDelimiter("${System.lineSeparator()}${System.lineSeparator()}")
        .asSequence()
        .toList()

    val segments = lines.map {
        it.split("\n")
    }.toList()

    val tiles = segments.map {
        val id = it[0].substring(4).trim().removeSuffix(":").toLong()
        val grid = it.subList(1,it.size).map {gridLine ->
            gridLine.trim().toCharArray().toList().map {chr ->
                chr == '#'
            }
        }
        Tile(id, grid)
    }

    val image = tiles.connect()

    return (image.first().first()!!.id * image.first().last()!!.id * image.last().first()!!.id * image.last().last()!!.id).toString()
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

                        connect(curTile, tile, curIndex)

                        candidates.push(tile)
                    }
                } else if (b.reversed() in curBorders) {
                    val curIndex = curBorders.indexOf(b.reversed())
                    val rots = Math.floorMod(curIndex - otherIndex.opposite(), 4)

                    if (curTile.getConnection(curIndex) == null) {
                        (idToTile[tile.id] ?: error("Tile ${tile.id} not found")).rotateClockWise(rots)

                        connect(curTile, tile, curIndex)

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

private fun connect(a: Tile, b: Tile, direction: Int) {
    a.setConnection(direction, b.id)
    b.setConnection(direction.opposite(), a.id)
}

private fun Int.opposite(): Int {
    return arrayOf(2,3,0,1)[this]
}