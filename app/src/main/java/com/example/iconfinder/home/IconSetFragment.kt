package com.example.iconfinder.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.iconfinder.R
import com.example.iconfinder.base.BaseFragment
import com.example.iconfinder.home.adapter.IconSetAdapter
import com.example.iconfinder.home.viewmodel.IconViewModel
import com.example.iconfinder.pojo.IconSet
import com.example.iconfinder.utils.START_INDEX

class IconSetFragment : BaseFragment(), IconSetAdapter.OnClick {

    private val iconSetAdapter by lazy { IconSetAdapter(mutableListOf(), this) }
    private val layoutManager by lazy { GridLayoutManager(requireContext(), 2) }
    private val iconViewModel by viewModels<IconViewModel> { viewModelFactory }
    private var category: String? = null

    private var startIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            category = it.getString(ARG_PARAM1)
        }

        if (savedInstanceState != null) {
            startIndex = savedInstanceState.getInt(START_INDEX, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_icon_set, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setUpRecycler()
    }

    private fun setUpRecycler() {

    }

    companion object {

        private const val ARG_PARAM1 = "param1"

        fun newInstance(param1: String) =
            IconSetFragment().apply {
                arguments = Bundle().apply {
                    putString(Companion.ARG_PARAM1, param1)
                }
            }

    }

    override fun onIconSetClicked(iconSet: IconSet) {

    }
}