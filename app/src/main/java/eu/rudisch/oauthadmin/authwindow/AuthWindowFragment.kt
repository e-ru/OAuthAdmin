package eu.rudisch.oauthadmin.authwindow


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import eu.rudisch.oauthadmin.databinding.FragmentAuthWindowBinding


/**
 * A simple [Fragment] subclass.
 */
class AuthWindowFragment : Fragment() {

    private lateinit var binding: FragmentAuthWindowBinding

    private val viewModel: AuthWindowViewModel by lazy {
        ViewModelProviders.of(this).get(AuthWindowViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthWindowBinding.inflate(inflater)

        setupWebView()

        viewModel.loggedIn.observe(this, Observer {
            it?.let {
                if (it) {
                    this.findNavController()
                        .navigate(AuthWindowFragmentDirections.actionAuthWindowFragmentToLoginFragment())
                }
            }
        })

        return binding.root
    }

    private fun setupWebView() {
        val authWindow = binding.authWindow
        authWindow.settings.domStorageEnabled = true
        authWindow.overScrollMode = WebView.OVER_SCROLL_NEVER
        authWindow.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (AuthWindowViewModel.OAUTH_SCHEME in url && AuthWindowViewModel.OAUTH_PATH !in url) {
                    viewModel.checkUrlAndPostCode(url)
                    return true //this might be unnecessary because another Activity

                }
                return false // then it is not handled by default action
            }
        }
        authWindow.loadUrl(AuthWindowViewModel.codeUrl)
    }

}
