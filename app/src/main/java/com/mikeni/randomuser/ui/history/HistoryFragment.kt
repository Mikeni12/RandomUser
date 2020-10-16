package com.mikeni.randomuser.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.mikeni.randomuser.R
import com.mikeni.randomuser.data.entities.MainStateEvent
import com.mikeni.randomuser.data.entities.User
import com.mikeni.randomuser.databinding.FragmentHistoryBinding
import com.mikeni.randomuser.utils.DataState
import com.mikeni.randomuser.utils.MarginItemDecorator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(), IRecyclerListener<User>  {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModels()
    private val historyAdapter = HistoryAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setData() {
        with(binding.recycler) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = historyAdapter
            addItemDecoration(MarginItemDecorator(resources.getDimensionPixelSize(R.dimen.margin_small)))
        }
        viewModel.setStateEvent(MainStateEvent.GetUserEvents)
    }


    private fun setObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, { dataState ->
            when (dataState) {
                is DataState.Success<List<User>> -> {
                    displayProgressBar(false)
                    historyAdapter.submitList(dataState.data)
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
        binding.pbHistory.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayError(message: String?) {
        Snackbar.make(binding.root, message ?: "Error", Snackbar.LENGTH_SHORT).show()
    }

    override fun onItemClick(item: User) {
        findNavController().navigate(HistoryFragmentDirections.actionToUserFragment(item))
    }
}