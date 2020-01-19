package eu.rudisch.oauthadmin.oauthusers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import eu.rudisch.oauthadmin.databinding.FragmentOauthusersBinding

class OAuthUsersFragment : Fragment() {

    private lateinit var binding: FragmentOauthusersBinding

    private val viewModel: OAuthUserViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, OAuthUserViewModel.Factory(activity.application))
            .get(OAuthUserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOauthusersBinding.inflate(inflater)

        viewModel.oAuthUsers.observe(this, Observer {
            it.let {
                binding.usersText.text = it.toString()
            }
        })

        return binding.root
    }
}