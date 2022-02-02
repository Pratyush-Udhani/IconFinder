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
import com.example.iconfinder.base.BaseFragment
import com.example.iconfinder.home.adapter.IconAdapter
import com.example.iconfinder.home.viewmodel.IconViewModel
import com.example.iconfinder.pojo.Icon
import com.example.iconfinder.pojo.RasterSize
import com.example.iconfinder.utils.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.emojiRecycler
import kotlinx.android.synthetic.main.activity_home.progressBar
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : BaseActivity(), IconAdapter.Onclick, RasterFragment.DialogItemClicked {

    private var backPressed: Long = 0
    private val iconViewModel by viewModels<IconViewModel> { viewModelFactory }
    private val iconAdapter by lazy { IconAdapter(mutableListOf(), this) }
    private val layoutManager by lazy { GridLayoutManager(this, 2) }

    var query = "\"\""
    val defaultQuery = "\"\""
    private var startIndex = 0
    private var rasterMap = mapOf<String, RasterSize>()

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
        setUpFragment()
        setUpObserver()
    }

    private fun setUpObserver() {
        iconViewModel.iconsLiveData.observe(this) { list ->
            listItems.addAll(list)
            removeDuplicateValues(listItems)
            iconAdapter.submitList(listItems)

            showLoading(false)
            if (list.isEmpty()) {
                Toast.makeText(this, "No more results found!", Toast.LENGTH_SHORT).show()
                emojiRecycler.makeGone()
                homeContainer.makeVisible()
                replaceFragment()
            }
        }
    }

    fun fetchIconsInSet(iconSetId: String) {
        homeContainer.makeGone()
        emojiRecycler.makeVisible()
        iconSetID = iconSetId
        iconViewModel.getIconsInIconSet(iconSetId, NUMBER_OF_ICONS)
    }

    private fun replaceFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.homeContainer, CategoriesFragment.newInstance())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun setUpFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.homeContainer, CategoriesFragment.newInstance())
        transaction.commit()
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
                if (query != defaultQuery) {
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
                homeContainer.makeGone()
                emojiRecycler.makeVisible()
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

    private fun changeFragment(fragment: BaseFragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.homeContainer, fragment)
        fragmentTransaction.commit()
        fragmentTransaction.addToBackStack(null)
    }

        override fun onBackPressed() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.homeContainer)

        if (iconSetID.isNullOrEmpty()) {
            if (currentFragment is IconSetFragment) {
                changeFragment(CategoriesFragment.newInstance())
            } else {
                if (backPressed.plus(2000) > System.currentTimeMillis()) {
                    super.onBackPressed()
                    finishAffinity()
                } else {
                    Toast.makeText(applicationContext, "Press again to exit", Toast.LENGTH_SHORT)
                        .show()
                    backPressed = System.currentTimeMillis()
                }
            }
        } else {
            homeContainer.makeVisible()
            emojiRecycler.makeGone()
            iconSetID = null
            listItems.clear()
            iconAdapter.submitList(listItems)
        }
    }

    companion object {
        fun newInstance(context: Context) = Intent(context, HomeActivity::class.java)
        private val listItems = mutableListOf<Icon>()
        var iconSetID : String? = null
    }

    private fun getDownloadUrl(baseUrl: String) =
        "$baseUrl?$CLIENT_ID=${BuildConfig.CLIENT_ID}&$CLIENT_SECRET=${BuildConfig.CLIENT_SECRET}"

    override fun onIconClicked(icon: Icon) {
        if (!isPermissionGranted(this)) {
            askForPermission(this)
        } else {
            rasterMap = icon.rasterSizes.map {
                it.size to it
            }.toMap()

            val rasterSelect = RasterFragment.newInstance(rasterMap.keys.toTypedArray())
            rasterSelect.show(supportFragmentManager, "raster")
        }
    }

    override fun rasterSelected(raster: String) {
        val filePath = rasterMap[raster]?.formats?.get(0)?.downloadUrl
        val downloadUrl = getDownloadUrl("$filePath")
        downloadImage(this, downloadUrl)
    }
}