package co.mewi.maze

class MazeNavigator(val maze: Maze) {

    private val map = mutableMapOf<String, Block>()
    private var current = maze.start

    init {
        maze.blocks.forEach {
            val x = it.x
            val y = it.y
            map["$x,$y"] = it
        }
    }

    fun getCurrentPosition() = current

    fun isAtStart() = current == maze.start

    fun isAtFinish() = current == maze.finish

    fun moveUp(current: Position): Position =
        if (allowMoveUp(current))
            current
                .copy(y = current.y - 1)
                .also { pos -> this.current = pos }
        else
            current

    fun moveDown(current: Position): Position =
        if (allowMoveDown(current))
            current
                .copy(y = current.y + 1)
                .also { pos -> this.current = pos }
        else
            current

    fun moveLeft(current: Position): Position =
        if (allowMoveLeft(current))
            current
                .copy(x = current.x - 1)
                .also { pos -> this.current = pos }
        else
            current

    fun moveRight(current: Position): Position =
        if (allowMoveRight(current))
            current
                .copy(x = current.x + 1)
                .also { pos -> this.current = pos }
        else
            current

    private fun allowMoveUp(current: Position): Boolean =
        map["${current.x},${current.y}"]?.let { it.up } ?: false

    private fun allowMoveDown(current: Position): Boolean =
        map["${current.x},${current.y}"]?.let { it.down } ?: false

    private fun allowMoveLeft(current: Position): Boolean =
        map["${current.x},${current.y}"]?.let { it.left} ?: false

    private fun allowMoveRight(current: Position): Boolean =
        map["${current.x},${current.y}"]?.let { it.right} ?: false
}