package com.example.iconfinder.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.iconfinder.R
import com.example.iconfinder.base.BaseFragment
import com.example.iconfinder.home.adapter.IconAdapter
import com.example.iconfinder.home.viewmodel.IconViewModel
import com.example.iconfinder.pojo.Emoji
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment(), IconAdapter.Onclick {

    private val iconViewModel by viewModels<IconViewModel> { viewModelFactory }
    private val iconAdapter by lazy {IconAdapter(mutableListOf(), this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setUpRecycler()
        setUpObserver()
    }

    private fun setUpObserver() {

    }

    private fun setUpRecycler() {
        emojiRecycler.apply {
            adapter = this@HomeFragment.iconAdapter
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        }
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onIconClicked(emoji: Emoji) {

    }
}