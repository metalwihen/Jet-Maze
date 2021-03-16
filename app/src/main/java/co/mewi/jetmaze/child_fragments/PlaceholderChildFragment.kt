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

/**
 * Hack:
 * Since the starting fragment in the nav_graph is required,
 * this placeholder is loaded first and discarded later
 */
class PlaceholderChildFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.child_fragment_placeholder, container, false)
    }
}