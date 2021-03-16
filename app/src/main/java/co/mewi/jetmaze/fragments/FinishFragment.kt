package co.mewi.jetmaze.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import co.mewi.jetmaze.R
import kotlinx.android.synthetic.main.fragment_finish.view.*

class FinishFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_finish, container, false)
            .apply {
                val isWin = arguments?.getBoolean(ARG_IS_WIN)?: false
                this.message.setText(
                    if (isWin) {
                        "CONGRATUALTIONS!\nYOU GOT OUT!"
                    } else {
                        "AH WELL...\nBETTER LUCK NEXT TIME"
                    }
                )
                this.button_return.setOnClickListener {
                    this.findNavController().navigate(R.id.action_return_home)
                }
            }
    }

    companion object {
        const val ARG_IS_WIN = "arg_is_win"

        @JvmStatic
        fun newInstance(isWin: Boolean) = GameFragment().apply {
            val bundle = Bundle().apply {
                putBoolean(ARG_IS_WIN, isWin)
            }
        }
    }
}