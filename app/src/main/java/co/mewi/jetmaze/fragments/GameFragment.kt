package co.mewi.jetmaze.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import co.mewi.jetmaze.R
import co.mewi.jetmaze.child_fragments.MazeChildFragment
import co.mewi.maze.Maze
import co.mewi.maze.MazeNavigator
import co.mewi.maze.getDefaultMaze
import kotlinx.android.synthetic.main.footer_navigator.*

class GameFragment : Fragment() {

    private lateinit var mazeStepNavigator: MazeNavigator

    private lateinit var mainNavController: NavController
    private lateinit var mazeNavController: NavController
    private var backPressedCallback: OnBackPressedCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_game, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mazeNavController = childFragmentManager.findFragmentById(R.id.child_nav_host).let {
            (it as NavHostFragment).navController
        }
        mainNavController = Navigation.findNavController(requireActivity(), R.id.main_nav_host)

        setupMaze()
        setupHostFragmentControls()
        setupChildFragmentControls()
    }

    /** GAME SPECIFIC CONTROLS **/

    private fun getMaze(): Maze? = activity?.let { activity -> getDefaultMaze(activity) }

    private fun setupMaze() {
        val maze = getMaze()!!
        mazeStepNavigator = MazeNavigator(maze)

        // Setup Navigation Controls
        up.setOnClickListener {
            mazeStepNavigator.moveUp()
        }
        down.setOnClickListener {
            mazeStepNavigator.moveDown()
        }
        left.setOnClickListener {
            mazeStepNavigator.moveLeft()
        }
        right.setOnClickListener {
            mazeStepNavigator.moveRight()
        }
        undo.setOnClickListener {
            mazeStepNavigator.undoMove()
        }
    }

    /** Common - Game & Jetpack **/

    private fun setupHostFragmentControls() {
        quit.setOnClickListener {
            openFinishScreen(false)
        }
        cheat.setOnClickListener {
            openFinishScreen(true)
        }
    }

    private fun setupChildFragmentControls() {
        // Replace the default fragment with invalid arguments
        setDefaultChildFragment()

        mazeStepNavigator.setCallback(
            onMove = { newPosition ->
                addMazeChildFragment(newPosition.x, newPosition.y)
                setCustomBackstackBehaviour()
                if (mazeStepNavigator.isAtFinish()) openFinishScreen(true)
            },
            onUndoMove = {
                popMazeChildFragment()
                setCustomBackstackBehaviour()
            }
        )
    }

    private fun setCustomBackstackBehaviour() {
        backPressedCallback?.remove()
        if (enableCustomBackstack()) {
            backPressedCallback = createCustomBackpress()
        }
    }

    private fun createCustomBackpress() =
        requireActivity().onBackPressedDispatcher.addCallback(this, true) {
            mazeStepNavigator.undoMove()
        }

    private fun enableCustomBackstack() = !mazeStepNavigator.isAtStart()
            || mazeStepNavigator.countMoves() != 1

    /** JETPACK NAVIGATION IN ACTION **/

    private fun openFinishScreen(didWin: Boolean) {
        mainNavController.navigate(
            R.id.action_win_game,
            Bundle().apply { putBoolean(FinishFragment.ARG_IS_WIN, didWin) }
        )
    }

    private fun addMazeChildFragment(x: Int, y: Int) {
        // BEFORE: childFragmentManager .beginTransaction() .add(R.id.child_nav_host, MazeChildFragment.newInstance(x, y)).commit()
        // AFTER:
        mazeNavController.navigate(
            R.id.action_step_into_maze,
            Bundle().apply {
                putInt(MazeChildFragment.ARG_CURRENT_X, x)
                putInt(MazeChildFragment.ARG_CURRENT_Y, y)
            })
    }

    private fun popMazeChildFragment(): Boolean {
        return mazeNavController.popBackStack()
    }

    private fun setDefaultChildFragment() {
        /** HACK
         * - a default fragment is mandatory via NavController but arguments are unknown during creation
         * - In a single fragment graph, the starting fragment can not be cleared
         * - Therefore, create a placeholder fragment as the starting fragment and replace with relevant fragment with custom arguments
         */
        val startX = mazeStepNavigator.maze.start.x
        val startY = mazeStepNavigator.maze.start.y
        mazeNavController.navigate(
            R.id.action_setup_maze,
            Bundle().apply {
                putInt(MazeChildFragment.ARG_CURRENT_X, startX)
                putInt(MazeChildFragment.ARG_CURRENT_Y, startY)
            })
    }

    companion object {
        @JvmStatic
        fun newInstance() = GameFragment()
    }
}