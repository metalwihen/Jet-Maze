package co.mewi.maze.parser

import android.content.Context
import co.mewi.maze.Block
import co.mewi.maze.Maze
import co.mewi.maze.Position
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


object MazeJsonParser {

    fun parseJsonString(json: String): Maze? {
        val obj = JSONObject(json)
        val countX = obj.getInt("count_x")
        val countY = obj.getInt("count_y")
        val startPosition = obj.getJSONObject("start").let {
            Position(
                x = it.getInt("x"),
                y = it.getInt("y")
            )
        }
        val finishPosition = obj.getJSONObject("finish").let {
            Position(
                x = it.getInt("x"),
                y = it.getInt("y")
            )
        }
        val blocks = mutableListOf<Block>()
        obj.getJSONArray("blocks").let {
            for (pos in 0 until it.length()) {
                val block = it.getJSONObject(pos)
                val x = block.getInt("x")
                val y = block.getInt("y")
                blocks.add(
                    Block(
                        x = x,
                        y = y,
                        left = block.getBoolean("left"),
                        right = block.getBoolean("right"),
                        up = block.getBoolean("up"),
                        down = block.getBoolean("down")
                    )
                )
            }
        }
        return Maze(
            countX = countX,
            countY = countY,
            start = startPosition,
            finish = finishPosition,
            blocks = blocks
        )
    }

    fun fetchJsonStringFromAssetsFolder(context: Context, fileName: String): String? {
        try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            return String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}