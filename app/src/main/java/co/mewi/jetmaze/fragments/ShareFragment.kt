package co.mewi.jetmaze.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import co.mewi.jetmaze.R
import kotlinx.android.synthetic.main.fragment_share.*

class ShareFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_share, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_share.setOnClickListener {
            shareSomething()
        }
        setCustomBackstack()
    }

    private fun setCustomBackstack() {
        val isDeeplink = arguments?.getBoolean(ARG_IS_DEEPLINK) ?: false
        requireActivity().onBackPressedDispatcher.addCallback(this, isDeeplink) {
            requireView().findNavController().popBackStack(R.id.homeFragment, true)
        }
    }

    private fun shareSomething() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "I AM VICTORIOUS.")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    companion object {
        const val ARG_IS_DEEPLINK = "is_deeplink"

        @JvmStatic
        fun newInstance(isDeeplink: Boolean) = ShareFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARG_IS_DEEPLINK, isDeeplink)
            }
        }
    }
}