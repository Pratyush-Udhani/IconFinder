package com.example.iconfinder.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iconfinder.R
import com.example.iconfinder.base.BaseFragment
import com.example.iconfinder.home.adapter.CategoriesAdapter
import com.example.iconfinder.home.viewmodel.IconViewModel
import com.example.iconfinder.pojo.Category
import com.example.iconfinder.utils.NUMBER_OF_CATEGORIES
import com.example.iconfinder.utils.START_INDEX
import com.example.iconfinder.utils.isLoading
import kotlinx.android.synthetic.main.fragment_categories.*
import java.util.*

class CategoriesFragment : BaseFragment(), CategoriesAdapter.Onclick {

    private val categoryAdapter by lazy { CategoriesAdapter(mutableListOf(), this) }
    private val layoutManager by lazy { GridLayoutManager(requireContext(), 2) }
    private val iconViewModel by viewModels<IconViewModel> { viewModelFactory }

    private var startIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        if (savedInstanceState != null) {
            startIndex = savedInstanceState.getInt(START_INDEX, 0)
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
        iconViewModel.getCategories(NUMBER_OF_CATEGORIES)
        setUpRecycler()
        setUpObserver()
    }

    private fun setUpObserver() {
        iconViewModel.categoryLiveData.observe(viewLifecycleOwner) { list ->
            listCategories.addAll(list)
            removeDuplicateValues(listCategories)
            categoryAdapter.submitList(listCategories)
        }
    }

    private fun removeDuplicateValues(movieItems: List<Category>) {
        val map = LinkedHashMap<String, Category>()

        for (item in movieItems) {
            map[item.name] = item
        }
        listCategories.clear()
        listCategories.addAll(map.values)
    }

    private fun setUpRecycler() {
        categoryRecycler.apply {
            layoutManager = this@CategoriesFragment.layoutManager
            adapter = this@CategoriesFragment.categoryAdapter
        }

        categoryRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val count = layoutManager.itemCount

                if (dy > 0 && !isLoading) {
                    val holderCount = layoutManager.childCount
                    val oldCount = layoutManager.findFirstVisibleItemPosition()

                    if (holderCount + oldCount >= count - 4 && !isLoading) {
                        startIndex += 20
                        iconViewModel.getCategories(startIndex)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        listCategories.clear()
        categoryAdapter.submitList(listCategories)
        init()
    }

    private fun changeFragment(fragment: BaseFragment) {
        val fragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.homeContainer, fragment)
        fragmentTransaction?.commit()
        fragmentTransaction?.addToBackStack(null)
    }

    companion object {
        fun newInstance() = CategoriesFragment()
        private val listCategories = mutableListOf<Category>()
    }

    override fun onCategoryClicked(category: Category) {
        changeFragment(IconSetFragment.newInstance(category.identifier))
    }
}