package com.example.iconfinder.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iconfinder.BuildConfig
import com.example.iconfinder.R
import com.example.iconfinder.base.BaseFragment
import com.example.iconfinder.home.adapter.IconAdapter
import com.example.iconfinder.home.viewmodel.IconViewModel
import com.example.iconfinder.pojo.Icon
import com.example.iconfinder.utils.*
import kotlinx.android.synthetic.main.activity_home.*

class IconsFragment : BaseFragment(), IconAdapter.Onclick {

    private val iconViewModel by viewModels<IconViewModel> { viewModelFactory }
    private val iconAdapter by lazy { IconAdapter(mutableListOf(), this) }
    private val layoutManager by lazy { GridLayoutManager(requireContext(), 2) }

    var query = "\"\""
    val defaultQuery = "\"\""
    private var startIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_icons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            query = savedInstanceState.getString(QUERY, defaultQuery)
            startIndex = savedInstanceState.getInt(START_INDEX, 0)
        }

        addOnScrollListener()
    }

    private fun addOnScrollListener() {
        Log.d("TAG!!!!", "called on")
        emojiRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val count = layoutManager.itemCount

                if (dy > 0 && !isLoading) {
                    val holderCount = layoutManager.childCount
                    val oldCount = layoutManager.findFirstVisibleItemPosition()

                    if (holderCount + oldCount >= count - 4 && !isLoading) {
                        startIndex += 20
                        iconViewModel.getIcons(query, NUMBER_OF_ICONS, startIndex)
                    }
                }
            }
        })
    }

    companion object {
        fun newInstance() = IconsFragment()
        private val listItems = mutableListOf<Icon>()
    }

    private fun getDownloadUrl(baseUrl: String) =
        "$baseUrl?$CLIENT_ID=${BuildConfig.CLIENT_ID}&$CLIENT_SECRET=${BuildConfig.CLIENT_SECRET}"

    override fun onIconClicked(icon: Icon) {
        if (!isPermissionGranted(requireContext())) {
            askForPermission(requireActivity())
        } else {
            val filePath = icon.rasterSizes[0].formats[0].downloadUrl
            val downloadUrl = getDownloadUrl("${BuildConfig.BASE_URL}$filePath")
            downloadImage(requireContext(), downloadUrl)
        }
    }
}