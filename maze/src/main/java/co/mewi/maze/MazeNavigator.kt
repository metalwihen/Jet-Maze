package co.mewi.maze

import java.util.*

class MazeNavigator(val maze: Maze) {

    private val map = mutableMapOf<String, Block>()
    private var history = Stack<Position>()

    init {
        maze.blocks.forEach {
            val x = it.x
            val y = it.y
            map["$x,$y"] = it
        }
        history.add(getCurrentPosition())
    }

    fun getCurrentPosition() = if (history.size > 0) history.peek() else maze.start

    fun countMoves() = history.size  // including first default move

    fun isAtStart() = getCurrentPosition() == maze.start

    fun isAtFinish() = getCurrentPosition() == maze.finish

    fun moveBack(): Position = if (countMoves() > 1) history.pop() else getCurrentPosition()

    fun moveUp(): Position =
        getCurrentPosition().let { current ->
            if (allowMoveUp())
                current
                    .copy(y = current.y - 1)
                    .also { pos ->
                        history.add(pos)
                    }
            else
                current
        }

    fun moveDown(): Position =
        getCurrentPosition().let { current ->
            if (allowMoveDown())
                current
                    .copy(y = current.y + 1)
                    .also { pos ->
                        history.add(pos)
                    }
            else
                current
        }

    fun moveLeft(): Position =
        getCurrentPosition().let { current ->
            if (allowMoveLeft())
                current
                    .copy(x = current.x - 1)
                    .also { pos ->
                        history.add(pos)
                    }
            else
                current
        }

    fun moveRight(): Position =
        getCurrentPosition().let { current ->
            if (allowMoveRight())
                current
                    .copy(x = current.x + 1)
                    .also { pos ->
                        history.add(pos)
                    }
            else
                current
        }

    fun allowMoveUp(): Boolean = getCurrentPosition()?.let { current ->
        map["${current.x},${current.y}"]?.let { it.up }
    } ?: false

    fun allowMoveDown(): Boolean = getCurrentPosition()?.let { current ->
        map["${current.x},${current.y}"]?.let { it.down }
    } ?: false

    fun allowMoveLeft(): Boolean = getCurrentPosition()?.let { current ->
        map["${current.x},${current.y}"]?.let { it.left }
    } ?: false

    fun allowMoveRight(): Boolean = getCurrentPosition()?.let { current ->
        map["${current.x},${current.y}"]?.let { it.right }
    } ?: false
}