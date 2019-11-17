package com.uk.androidrecruitmentapp.feature.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uk.androidrecruitmentapp.BaseFragment
import com.uk.androidrecruitmentapp.R
import com.uk.androidrecruitmentapp.feature.characters.list.CharactersAdapter
import com.uk.androidrecruitmentapp.utils.getVM
import kotlinx.android.synthetic.main.fragment_characters.*
import javax.inject.Inject

class CharactersFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CharactersVM by lazy { getVM<CharactersVM>(viewModelFactory) }

    private val charactersAdapter by lazy { CharactersAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observe()
    }

    private fun setupList() {
        charactersRV.apply {
            adapter = charactersAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    private fun observe() {
        viewModel.progressVisibility.observe(this, Observer {
            if (it) {
                charactersLoadPB.visibility = View.VISIBLE
            } else {
                charactersLoadPB.visibility = View.GONE
            }
        })
        viewModel.charactersList.observe(this, Observer {
            charactersAdapter.submitData(it.toMutableList())
        })
        viewModel.toastMessage.observe(this, Observer { errorMsgResId ->
            context?.let { Toast.makeText(it, errorMsgResId, Toast.LENGTH_SHORT).show() }
        })
    }
}