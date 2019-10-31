package eu.rudisch.oauthadmin.authwindow


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import eu.rudisch.oauthadmin.databinding.FragmentAuthWindowBinding
import android.content.Intent
import android.net.Uri
import androidx.navigation.findNavController


/**
 * A simple [Fragment] subclass.
 */
class AuthWindowFragment : Fragment() {

    private lateinit var binding: FragmentAuthWindowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            eu.rudisch.oauthadmin.R.layout.fragment_auth_window,
            container,
            false
        )
        val OAUTH_SERVER = "http://192.168.188.109:9191"
        val RESPONSE_TYPE = "code"
        val CLIENT_ID = "auth_app"
//        val SCOPE = "create_oauth read_oauth update_oauth delete_oauth"
        val SCOPE = "read_oauth"
        val REDIRECT_URL = "oauthadmin://redirect"

        // new intent "retrofit tutorial oauth authentication with github, 11:40"
        val url =
            "${OAUTH_SERVER}/oauth/authorize?response_type=${RESPONSE_TYPE}&scope=${SCOPE}&client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URL}"
        // see constants
        Log.i("AuthWindowFragment", url)
        val authWindow = binding.authWindow

//            authWindow.settings.javaScriptEnabled = true
        authWindow.settings.domStorageEnabled = true
        authWindow.overScrollMode = WebView.OVER_SCROLL_NEVER
        authWindow.webViewClient = object : WebViewClient() {


            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                // Here put your code
                Log.d("My Webview", url)

                if ("oauthadmin" in url && "oauth/authorize" !in url) { //checking the URL for scheme required
                     view.findNavController().navigate(AuthWindowFragmentDirections.actionAuthWindowFragmentToLoginFragment())
                    return true //this might be unnecessary because another Activity

                }
                return false // then it is not handled by default action
            }
        }
        authWindow.loadUrl(url)

        return binding.root
    }

}
