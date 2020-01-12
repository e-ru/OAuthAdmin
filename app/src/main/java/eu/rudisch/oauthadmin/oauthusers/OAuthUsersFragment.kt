package eu.rudisch.oauthadmin.oauthusers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import eu.rudisch.oauthadmin.R
import eu.rudisch.oauthadmin.databinding.FragmentOauthusersBinding

class OAuthUsersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentOauthusersBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_oauthusers, container, false)

        return binding.root
    }
}