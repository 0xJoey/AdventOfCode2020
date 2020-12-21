package day20

class Tile(val id: Long, var grid: List<List<Boolean>>) {
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