package co.mewi.jetmaze.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import co.mewi.jetmaze.R
import co.mewi.jetmaze.child_fragments.MazeChildFragment
import co.mewi.maze.Maze
import co.mewi.maze.MazeNavigator
import co.mewi.maze.getDefaultMaze
import kotlinx.android.synthetic.main.footer_navigator.*
import kotlinx.android.synthetic.main.footer_navigator.view.*
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment() {

    private lateinit var mazeNavigator: MazeNavigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_game, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMaze()
        setupHostFragmentControls()
        setupChildFragmentControls()
    }

    private fun setupHostFragmentControls() {
        val navController = Navigation.findNavController(requireActivity(), R.id.main_nav_host)
        requireView().findViewById<View>(R.id.quit)
            .setOnClickListener {
                navController.navigate(R.id.action_quit_game)
            }
        requireView().findViewById<View>(R.id.cheat)
            .cheat.setOnClickListener {
                finishGame(true)
            }
    }

    private fun finishGame(didWin: Boolean) {
        val navController = Navigation.findNavController(requireActivity(), R.id.main_nav_host)
        navController.navigate(
            R.id.action_win_game,
            Bundle().apply { putBoolean(FinishFragment.ARG_IS_WIN, didWin) }
        )
    }

    private fun setupMaze() {
        val maze = getMaze()!!
        mazeNavigator = MazeNavigator(maze)
        addMazeChildFragment(mazeNavigator.maze.start.x, mazeNavigator.maze.start.y)
    }

    private fun setupChildFragmentControls() {
        // Setup Navigation Controls
        up.setOnClickListener {
            val oldPosition = mazeNavigator.getCurrentPosition()
            val newPosition = mazeNavigator.moveUp(oldPosition)
            addMazeChildFragment(newPosition.x, newPosition.y)
            doOnWin(mazeNavigator)
        }
        down.setOnClickListener {
            val oldPosition = mazeNavigator.getCurrentPosition()
            val newPosition = mazeNavigator.moveDown(oldPosition)
            addMazeChildFragment(newPosition.x, newPosition.y)
            doOnWin(mazeNavigator)
        }
        left.setOnClickListener {
            val oldPosition = mazeNavigator.getCurrentPosition()
            val newPosition = mazeNavigator.moveLeft(oldPosition)
            addMazeChildFragment(newPosition.x, newPosition.y)
            doOnWin(mazeNavigator)
        }
        right.setOnClickListener {
            val oldPosition = mazeNavigator.getCurrentPosition()
            val newPosition = mazeNavigator.moveRight(oldPosition)
            addMazeChildFragment(newPosition.x, newPosition.y)
            doOnWin(mazeNavigator)
        }
        undo.setOnClickListener {
            childFragmentManager.
            popMazeChildFragment()
        }
    }

    private fun doOnWin(mazeNavigator: MazeNavigator) {
        if (mazeNavigator.isAtFinish()) finishGame(true)
    }

    private fun getMaze(): Maze? = activity?.let { activity -> getDefaultMaze(activity) }

    private fun addMazeChildFragment(x: Int, y: Int) {
        childFragmentManager
            .beginTransaction()
            .add(R.id.child_nav_host, MazeChildFragment.newInstance(x, y))
            .commit()
    }

    private fun popMazeChildFragment() {
        childFragmentManager
            .popBackStackImmediate()
    }

    companion object {
        @JvmStatic
        fun newInstance() = GameFragment()
    }
}