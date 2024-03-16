package com.cs4520.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cs4520.assignment1.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener {
            if (binding.username.text.toString() == "admin" && binding.password.text.toString() == "admin") {
                binding.username.text.clear()
                binding.password.text.clear()
                findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
            } else {
                Toast.makeText(context, "Login Failed.", Toast.LENGTH_LONG).show()
            }
        }
    }
}