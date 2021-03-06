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
import com.example.iconfinder.home.adapter.IconSetAdapter
import com.example.iconfinder.home.viewmodel.IconViewModel
import com.example.iconfinder.pojo.IconSet
import com.example.iconfinder.utils.*
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_icon_set.*
import kotlinx.android.synthetic.main.fragment_icon_set.progressBar
import java.util.*

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
        showLoading(true)
        clearList()
        setUpRecycler()
        setUpObserver()
        iconViewModel.getIconSet(category!!, NUMBER_OF_ICON_SETS)
    }

    private fun clearList() {
        listIconSet.clear()
        iconSetAdapter.submitList(listIconSet)
    }

    private fun showLoading(boolean: Boolean) {
        if (boolean) progressBar.makeVisible()
        else progressBar.makeGone()
    }

    private fun setUpObserver() {
        iconViewModel.iconSetLiveData.observe(viewLifecycleOwner) { list ->
            if (list != null) {
                showLoading(false)
                listIconSet.addAll(list)
                removeDuplicateValues(listIconSet)
                iconSetAdapter.submitList(listIconSet)
            } else {
            }
        }
    }

    private fun setUpRecycler() {
        iconSetRecycler.apply {
            layoutManager = this@IconSetFragment.layoutManager
            adapter = this@IconSetFragment.iconSetAdapter
        }
        iconSetRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val count = layoutManager.itemCount

                if (dy > 0 && !isLoading) {
                    val holderCount = layoutManager.childCount
                    val oldCount = layoutManager.findFirstVisibleItemPosition()

                    if (holderCount + oldCount >= count - 4 && !isLoading) {
                        startIndex += 20
                        iconViewModel.getIconSet(category!!, startIndex)
                    }
                }
            }
        })
    }

    private fun removeDuplicateValues(movieItems: List<IconSet>) {
        val map = LinkedHashMap<String, IconSet>()

        for (item in movieItems) {
            map[item.iconSetId] = item
        }
        listIconSet.clear()
        listIconSet.addAll(map.values)
    }

    companion object {

        private const val ARG_PARAM1 = "param1"
        private val listIconSet = mutableListOf<IconSet>()

        fun newInstance(param1: String) =
            IconSetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onIconSetClicked(iconSet: IconSet) {
        (activity as HomeActivity).fetchIconsInSet(iconSet.iconSetId)
    }
}