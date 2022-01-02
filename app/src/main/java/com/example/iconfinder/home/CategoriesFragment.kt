package com.example.iconfinder.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.iconfinder.R
import com.example.iconfinder.base.BaseFragment
import com.example.iconfinder.home.adapter.CategoriesAdapter
import com.example.iconfinder.pojo.Category
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : BaseFragment(), CategoriesAdapter.Onclick {

    private val categoryAdapter by lazy { CategoriesAdapter(mutableListOf(), this) }
    private val layoutManager by lazy { GridLayoutManager(requireContext(), 2) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        setUpRecycler()
    }

    private fun setUpRecycler() {
        categoryRecycler.apply {
            layoutManager = this@CategoriesFragment.layoutManager

        }
    }

    companion object {
        fun newInstance() = CategoriesFragment()
        private val listCategories = mutableListOf<Category>()
    }

    override fun onCategoryClicked(category: Category) {

    }
}