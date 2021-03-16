package co.mewi.jetmaze.child_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.mewi.jetmaze.R
import co.mewi.maze.Position
import co.mewi.maze.getDefaultMaze
import kotlinx.android.synthetic.main.child_fragment_maze.view.*

class MazeChildFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.child_fragment_maze, container, false)
            .apply {
                getDefaultMaze(context)?.let { maze ->
                    getPosition()?.let { currentPosition ->
                        this.maze_view.setup(
                            maze = maze,
                            current_x = currentPosition.x,
                            current_y = currentPosition.y
                        )
                    }
                }
            }
    }

    fun getPosition() = Position(
        requireArguments().getInt(ARG_CURRENT_X),
        requireArguments().getInt(ARG_CURRENT_Y)
    )

    companion object {

        const val ARG_CURRENT_X = "arg_current_position_x"
        const val ARG_CURRENT_Y = "arg_current_position_y"

        @JvmStatic
        fun newInstance(x: Int, y: Int) =
            MazeChildFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CURRENT_X, x)
                    putInt(ARG_CURRENT_Y, y)
                }
            }
    }
}