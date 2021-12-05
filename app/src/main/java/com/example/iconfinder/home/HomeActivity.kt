package com.example.iconfinder.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.iconfinder.BuildConfig
import com.example.iconfinder.BuildConfig.BASE_URL
import com.example.iconfinder.R
import com.example.iconfinder.base.BaseActivity
import com.example.iconfinder.home.adapter.IconAdapter
import com.example.iconfinder.home.viewmodel.IconViewModel
import com.example.iconfinder.pojo.Icon
import com.example.iconfinder.utils.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.emojiRecycler
import kotlinx.android.synthetic.main.activity_home.progressBar
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

class HomeActivity : BaseActivity(), IconAdapter.Onclick {

    private var backPressed: Long = 0
    private val iconViewModel by viewModels<IconViewModel> { viewModelFactory }
    private val iconAdapter by lazy { IconAdapter(mutableListOf(), this) }
    private val layoutManager by lazy { GridLayoutManager(this, 2) }

    var query = "\"\""
    val defaultQuery = "\"\""
    private var startIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        title = ""

        init()

        if (savedInstanceState != null) {
            query = savedInstanceState.getString(QUERY, defaultQuery)
            startIndex = savedInstanceState.getInt(START_INDEX, 0)
        }

        addOnScrollListener()
    }

    private fun init() {
        showLoading(false)
        setUpRecycler()

        iconViewModel.iconsLiveData.observe(this) { list ->
            listItems.addAll(list)
            removeDuplicateValues(listItems)
            iconAdapter.submitList(listItems)

            showLoading(false)
            if (list.isEmpty()) Toast.makeText(this, "No more results found!", Toast.LENGTH_SHORT).show()
            Log.d("Main", listItems.size.toString())
        }
    }

    private fun loadData(query: String, count: Int, index: Int) {
        showLoading(true)
        iconViewModel.getIcons(query, count, index)
    }

    private fun addOnScrollListener() {
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


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(QUERY, query)
        outState.putInt(START_INDEX, startIndex)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val search = menu?.findItem(R.id.action_search)
        val searchView = search?.actionView as SearchView

        search.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean = true

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                if (query != defaultQuery) { // Something has been searched by the user
                    removeAndReload()
                    iconViewModel.getIcons(defaultQuery, NUMBER_OF_ICONS, 0)
                }
                return true
            }

        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query == null) return false

                removeAndReload()
                this@HomeActivity.query = query
                iconViewModel.getIcons(query, NUMBER_OF_ICONS, 0)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
        return super.onCreateOptionsMenu(menu)
    }


    private fun removeAndReload() {
        listItems.clear()
        iconAdapter.submitList(mutableListOf())
        showLoading(true)
    }

    private fun removeDuplicateValues(movieItems: List<Icon>) {
        val map = LinkedHashMap<Int, Icon>()

        for (item in movieItems) {
            map[item.id] = item
        }
        listItems.clear()
        listItems.addAll(map.values)
    }

    private fun setUpRecycler() {
        emojiRecycler.apply {
            adapter = this@HomeActivity.iconAdapter
            layoutManager = GridLayoutManager(this@HomeActivity, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun showLoading(boolean: Boolean) {
        if (boolean) progressBar.makeVisible()
        else progressBar.makeGone()
    }



    override fun onBackPressed() {
        if (backPressed.plus(2000) > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity()
        } else {
            Toast.makeText(applicationContext, "Press again to exit", Toast.LENGTH_SHORT).show()
            backPressed = System.currentTimeMillis()
        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, HomeActivity::class.java)
        private val listItems = mutableListOf<Icon>()
    }

    private fun getDownloadUrl(baseUrl: String) =
        "$baseUrl?$CLIENT_ID=${BuildConfig.CLIENT_ID}&$CLIENT_SECRET=${BuildConfig.CLIENT_SECRET}"

    override fun onIconClicked(icon: Icon) {
        if (!isPermissionGranted(this)) {
            askForPermission(this)
        } else {
            val filePath = icon.rasterSizes[0].formats[0].downloadUrl
            val downloadUrl = getDownloadUrl("$BASE_URL$filePath")
            downloadImage(this, downloadUrl)
        }
    }
}