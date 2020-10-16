package com.mikeni.randomuser.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.mikeni.randomuser.R
import com.mikeni.randomuser.data.entities.MainStateEvent
import com.mikeni.randomuser.data.entities.User
import com.mikeni.randomuser.databinding.FragmentUserBinding
import com.mikeni.randomuser.utils.DataState
import com.mikeni.randomuser.utils.formatDate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setListeners()
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData() {
        arguments?.let {
            UserFragmentArgs.fromBundle(it).recent?.let { user ->
                displayUserInfo(user)
            }
        }
    }

    private fun setListeners() {
        binding.btnGetUser.setOnClickListener {
            viewModel.setStateEvent(MainStateEvent.GetUserEvents)
        }
    }

    private fun setObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<User> -> {
                    displayProgressBar(false)
                    displayUserInfo(dataState.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.pbUser.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String?) {
        Snackbar.make(binding.root, message ?: "Error", Snackbar.LENGTH_SHORT).show()
    }

    private fun displayUserInfo(user: User) {
        with(user) {
            binding.cardViewUser.visibility = View.VISIBLE
            val address = getString(
                R.string.user_address,
                location.street.name,
                location.street.number,
                location.city,
                location.state,
                location.country
            )

            binding.tvUserName.text =
                getString(R.string.user_name, name.title, name.first, name.last)
            binding.tvUserGender.text = getString(R.string.user_gender, gender)
            binding.tvUserAge.text = getString(R.string.user_age, dob.age)
            binding.tvUserBirthday.text = getString(R.string.user_birthday, formatDate(dob.date))
            binding.tvUserPhoneNumber.text = getString(R.string.user_phone, phone)
            binding.tvUserEmail.text = getString(R.string.user_email, email)
            binding.tvUserAddress.text = address
            binding.tvUserUsername.text = getString(R.string.user_username, login.username)
            binding.tvUserPassword.text = getString(R.string.user_password, login.password)
            Glide.with(this@UserFragment)
                .load(picture.large)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.imgUser)
        }
    }

}