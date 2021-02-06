package com.uk.androidrecruitmentapp.feature.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uk.androidrecruitmentapp.BaseFragment
import com.uk.androidrecruitmentapp.databinding.CharactersFragmentBinding
import com.uk.androidrecruitmentapp.feature.characters.list.CharactersAdapter
import com.uk.androidrecruitmentapp.utils.addOnScrolledEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : BaseFragment() {

    private lateinit var binding: CharactersFragmentBinding

    private val viewModel: CharactersVM by viewModels<CharactersVMImpl>()

    private val charactersAdapter by lazy { CharactersAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CharactersFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observe()
    }

    private fun setupList() {
        binding.charactersRV.apply {
            adapter = charactersAdapter
            val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            layoutManager = linearLayoutManager
            addOnScrolledEvent {
                viewModel.onListScrolled(linearLayoutManager.findLastCompletelyVisibleItemPosition(), charactersAdapter.itemCount)
            }
        }
    }

    private fun observe() {
        viewModel.progressVisibility.observe(viewLifecycleOwner, {
            if (it) {
                binding.charactersLoadPB.visibility = View.VISIBLE
            } else {
                binding.charactersLoadPB.visibility = View.GONE
            }
        })
        viewModel.charactersList.observe(viewLifecycleOwner, {
            charactersAdapter.submitData(it.toMutableList())
        })
        viewModel.toastMessage.observe(viewLifecycleOwner, { errorMsgResId ->
            context?.let { Toast.makeText(it, errorMsgResId, Toast.LENGTH_SHORT).show() }
        })
        viewModel.loadingMoreVisibility.observe(viewLifecycleOwner, {
            if (it) {
                charactersAdapter.showLoading()
            } else {
                charactersAdapter.hideLoading()
            }
        })
    }
}