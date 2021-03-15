package co.mewi.maze

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import co.mewi.maze.view.BlockViewItem
import co.mewi.maze.view.MazeGridAdapter
import kotlinx.android.synthetic.main.layout_maze.view.*

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
        getDefault(context)?.let {
            rootView.grid.layoutManager = GridLayoutManager(context, it.countY)
            rootView.grid.adapter = listAdapter
            setup(it)
        }
    }

    private fun calculateListPosition(countX: Int, x: Int, y: Int) = countX * x + y

    // VALIDATION
    // check 1: Is map setup?
    // check 2: Arguments valid? - non-negative, within map constraint

    fun setup(maze: Maze) {
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
            current = calculateListPosition(maze.countX, 2, 1)
        )
    }
}
