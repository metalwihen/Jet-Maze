package co.mewi.jetmaze.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import co.mewi.jetmaze.R
import kotlinx.android.synthetic.main.footer_navigator.view.*

class GameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
            .apply {
                this.quit.setOnClickListener {
                    this.findNavController().navigate(R.id.action_quit_game)
                }
                this.cheat.setOnClickListener {
                    this.findNavController()
                        .navigate(
                            R.id.action_win_game,
                            Bundle().apply { putBoolean(FinishFragment.ARG_IS_WIN, true) }
                        )
                }
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = GameFragment()
    }
}