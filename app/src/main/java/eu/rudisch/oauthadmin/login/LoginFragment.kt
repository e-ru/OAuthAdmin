package eu.rudisch.oauthadmin.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import eu.rudisch.oauthadmin.R
import eu.rudisch.oauthadmin.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )

        binding.loginButton.setOnClickListener {
            it.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToAuthWindowFragment())
        }
        return binding.root
    }


}
