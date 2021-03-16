package co.mewi.maze

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import co.mewi.maze.view.BlockViewItem
import co.mewi.maze.view.MazeGridAdapter
import kotlinx.android.synthetic.main.layout_maze.view.*
import java.lang.IllegalStateException

class MazeComponent(
    context: Context,
    attributes: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attributes, defStyle, defStyleRes) {

    constructor(context: Context) : this(context, null, 0, 0)

    constructor(context: Context, attributes: AttributeSet) : this(context, attributes, 0, 0)

    private val listAdapter = MazeGridAdapter()

    init {
        setupView()
        // TODO: add attribute customisation later
        // TODO: LCE
    }

    private fun setupView() {
        val rootView = LayoutInflater.from(context)
            .inflate(R.layout.layout_maze, this, true)
    }

    private fun calculateListPosition(countX: Int, x: Int, y: Int) = countX * y + x

    fun setup(maze: Maze, current_x: Int, current_y: Int) {
        maze.let {
            rootView.grid.layoutManager = GridLayoutManager(context, it.countY)
            rootView.grid.adapter = listAdapter
        }

        val isXValid = current_x >= 0 && current_x < maze.countX
        val isYValid = current_y >= 0 && current_y < maze.countY
        if (!isXValid || !isYValid) {
            throw IllegalStateException("Invalid Coordinates for Maze Component $current_x , $current_y")
        }

        val list = maze.blocks.map {
            BlockViewItem(
                hideLeft = it.left,
                hideRight = it.right,
                hideTop = it.up,
                hideBottom = it.down
            )
        }

        listAdapter.updateList(
            list = list,
            start = calculateListPosition(maze.countX, maze.start.x, maze.start.y),
            finish = calculateListPosition(maze.countX, maze.finish.x, maze.finish.y),
            current = calculateListPosition(maze.countX, current_x, current_y)
        )
    }
}
