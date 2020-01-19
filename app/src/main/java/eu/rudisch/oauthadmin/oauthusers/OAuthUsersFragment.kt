package eu.rudisch.oauthadmin.oauthusers

import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import eu.rudisch.oauthadmin.databinding.FragmentOauthusersBinding
import timber.log.Timber
import android.content.Intent
import android.net.Uri
import android.view.*


class OAuthUsersFragment : Fragment() {

    private lateinit var binding: FragmentOauthusersBinding

//    private val viewModel: OAuthUserViewModel by lazy {
//        val activity = requireNotNull(this.activity) {
//            "You can only access the viewModel after onActivityCreated()"
//        }
//        ViewModelProviders.of(this, OAuthUserViewModel.Factory(activity.application))
//            .get(OAuthUserViewModel::class.java)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOauthusersBinding.inflate(inflater)

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        val cookie = cookieManager.getCookie("http://192.168.188.109")
        Timber.i("cookie app: $cookie")
        //cookie format:
//        https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Cookie

        setupWebView()

//        setupBrowserWindow()
//        viewModel.oAuthUsers.observe(this, Observer {
//            it.let {
//                binding.usersText.text = it.toString()
//            }
//        })

        return binding.root
    }

    private fun setupBrowserWindow() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kicker.de"))
        startActivity(browserIntent)
    }

    private fun setupWebView() {
        val webView = binding.oAuthUsers
        webView.settings.domStorageEnabled = true
        webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                url: String
            ): Boolean {
                Timber.i("users url: $url")
                if ("https://www.chip.de" !in url) {
                    val browserIntent =
                        Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)
                    return true
                }
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                val cookie = cookieManager.getCookie("http://192.168.188.109")
                Timber.i("cookie webview: $cookie")
            }
        }
        webView.loadUrl("https://www.chip.de")
    }
}