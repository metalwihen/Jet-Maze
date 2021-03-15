package co.mewi.maze

import android.content.Context
import co.mewi.maze.parser.MazeJsonParser

data class Maze(
    val countX: Int,
    val countY: Int,
    val start: Position,
    val finish: Position,
    val blocks: List<Block>
)

data class Block(
    val x: Int,
    val y: Int,
    val left: Boolean,
    val right: Boolean,
    val down: Boolean,
    val up: Boolean
)

data class Position(
    val x: Int,
    val y: Int
)

fun getDefault(context: Context): Maze? {
    return MazeJsonParser.fetchJsonStringFromAssetsFolder(context, "sample-maze1.json")
        ?.let { MazeJsonParser.parseJsonString(it) }
}
