package co.mewi.jetmaze.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.findNavController
import co.mewi.jetmaze.R
import co.mewi.jetmaze.deeplink.NotificationHelper
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_play.setOnClickListener {
            requireView().findNavController().navigate(R.id.action_start_game)
        }
        button_deeplink.setOnClickListener {
            NotificationHelper.imitatePushNotification(requireContext())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}