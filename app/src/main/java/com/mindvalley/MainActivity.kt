package com.mindvalley

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mindvalley.adapter.CategoriesAdapter
import com.mindvalley.adapter.ChannelAdapter
import com.mindvalley.adapter.NewEpisodeAdapter
import com.mindvalley.api.GetCategarySectionAPI
import com.mindvalley.api.GetChannelsSectionAPI
import com.mindvalley.api.GetNewEpisodesSectionAPI
import com.mindvalley.bean.CategoryBean
import com.mindvalley.bean.ChannelSectionBean
import com.mindvalley.bean.ChannelsBean
import com.mindvalley.database.MV_DB_Hepler
import com.mindvalley.listeners.Click
import com.mindvalley.ui.GridItemDecoration
import com.mindvalley.ui.LogHistory
import com.mindvalley.ui.Utils
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import com.stocktakeonline.backend.ResponseListener
import ui.Config

class MainActivity : AppCompatActivity() {

    private var mActivity: Activity = this

    private lateinit var mGetNewEpisodesSectionAPI: GetNewEpisodesSectionAPI
    private lateinit var mGetChannelsSectionAPI: GetChannelsSectionAPI
    private lateinit var mGetCategarySectionAPI: GetCategarySectionAPI

    internal var mCategoryBean: CategoryBean = CategoryBean()
    internal var mChannelsBean: ChannelsBean = ChannelsBean()
    internal var nChannelSectionBean: ChannelSectionBean = ChannelSectionBean()

    private lateinit var mRvNewEpisode: RecyclerView
    private lateinit var mRvChannels: RecyclerView
    private lateinit var mRvCategory: RecyclerView

    private lateinit var mSwipeRefresh: SwipyRefreshLayout

    private lateinit var mNewEpisodeAdapter: NewEpisodeAdapter
    private lateinit var mChannelAdapter: ChannelAdapter
    private lateinit var mCategoriesAdapter: CategoriesAdapter

    lateinit var mStoDbHepler: MV_DB_Hepler

    private lateinit var mLogHistory: LogHistory


    /**
     * Activity life cycle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        val listNE = mStoDbHepler.getNewEpisode()
        val listCat = mStoDbHepler.getCategories()
        val listCH = mStoDbHepler.getChannel()

        if (listNE.size == 0) {
            getNewEpisodesAPI()
        } else {
            bindNewEpisode()
        }

        if (listCat.size == 0) {
            getCategaryAPI()
        } else {
            bindCategories()
        }

        if (listCH.size == 0) {
            getChannelsAPI()
        } else {
            bindChannels()
        }

        mSwipeRefresh.setOnRefreshListener { direction ->
            if (direction == SwipyRefreshLayoutDirection.TOP) {
                mSwipeRefresh.isRefreshing = false
                getCategaryAPI()
                getChannelsAPI()
                getNewEpisodesAPI()
            } else if (direction == SwipyRefreshLayoutDirection.BOTTOM) {
                mSwipeRefresh.isRefreshing = false
            }
        }
    }

    /**
     * Variable initialization
     */
    @SuppressLint("WrongConstant")
    private fun init() {

        mLogHistory = LogHistory()
        mRvNewEpisode = findViewById(R.id.rv_newEpisode)
        mRvNewEpisode.isNestedScrollingEnabled = false
        mRvNewEpisode.layoutManager = LinearLayoutManager(mActivity, LinearLayout.HORIZONTAL, false)

        mRvChannels = findViewById(R.id.rv_channels)
        mRvChannels.isNestedScrollingEnabled = false
        mRvChannels.layoutManager = LinearLayoutManager(mActivity, LinearLayout.VERTICAL, false)

        mRvCategory = findViewById(R.id.rv_category)
        mRvCategory.layoutManager = GridLayoutManager(this, 2)
        mRvCategory.addItemDecoration(GridItemDecoration(10, 2))

         mSwipeRefresh = findViewById(R.id.swipeRefresh)
        mStoDbHepler = MV_DB_Hepler(mActivity)
    }

    /**
     * API Call for New Episodes
     */
    private fun getNewEpisodesAPI() {
        if (Utils.isOnline(mActivity)) {
            Utils.showProgress(mActivity)
            mLogHistory.apiCallTest()
            mGetNewEpisodesSectionAPI = GetNewEpisodesSectionAPI(mActivity, responseListener)
            mGetNewEpisodesSectionAPI.execute()
        } else {
            Utils.showToast(mActivity, getString(R.string.internet_connection_problem))
        }
    }

    /**
     * API Call for Channels
     */
    private fun getChannelsAPI() {
        if (Utils.isOnline(mActivity)) {
            Utils.showProgress(mActivity)
            mLogHistory.apiCallTest()
            mGetChannelsSectionAPI = GetChannelsSectionAPI(mActivity, responseListener)
            mGetChannelsSectionAPI.execute()
        } else {
            Utils.showToast(mActivity, getString(R.string.internet_connection_problem))
        }
    }

    /**
     * API Call for Categories
     */
    private fun getCategaryAPI() {
        if (Utils.isOnline(mActivity)) {
            Utils.showProgress(mActivity)
            mLogHistory.apiCallTest()
            mGetCategarySectionAPI = GetCategarySectionAPI(mActivity, responseListener)
            mGetCategarySectionAPI.execute()
        } else {
            Utils.showToast(mActivity, getString(R.string.internet_connection_problem))
        }
    }

    /***
     * API Response Listener
     */
    private var responseListener: ResponseListener = object : ResponseListener {
        override fun onResponse(tag: String, result: Int, obj: Any) {
            try {
                Utils.hideProgress()

                if (tag == Config.TAG_NEW_EPISODES_SECTION) {
                    if (result == Config.API_SUCCESS) {
                        mStoDbHepler.deleteNewEpisode()
                        mChannelsBean = obj as ChannelsBean
                        mStoDbHepler.addNewEpisode(mChannelsBean)
                        bindNewEpisode()

                    } else {
                        Utils.showToast(mActivity, obj)
                    }
                } else if (tag == Config.TAG_CHANNELS_SECTION) {
                    if (result == Config.API_SUCCESS) {
                        mStoDbHepler.deleteChannel()
                        nChannelSectionBean = obj as ChannelSectionBean
                        mStoDbHepler.addChannel(mActivity, nChannelSectionBean)
                        bindChannels()

                    } else {
                        Utils.showToast(mActivity, obj)
                    }
                } else if (tag == Config.TAG_CATEGORIES_SECTION) {
                    if (result == Config.API_SUCCESS) {
                        mStoDbHepler.deleteCategory()
                        mCategoryBean = obj as CategoryBean
                        mStoDbHepler.addCategories(mCategoryBean)
                        bindCategories()

                    } else {
                        Utils.showToast(mActivity, obj)
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /***
     * Data binding for categories
     */
    private fun bindCategories() {
        // var list = categoryBean.data.categories
        val list = mStoDbHepler.getCategories()
        mCategoriesAdapter = CategoriesAdapter(mActivity.applicationContext, list, object :
            Click {
            override fun onclick(position: Int, `object`: Any, text: String) {
            }
        })
        mLogHistory.dataBinding()
        mRvCategory.adapter = mCategoriesAdapter
    }

    /***
     * Data binding for Channels
     */
    private fun bindChannels() {
        // var list = channelSectionBean.data.channels
        val list = mStoDbHepler.getChannel()
        mChannelAdapter = ChannelAdapter(mActivity.applicationContext, list, object : Click {
            override fun onclick(position: Int, `object`: Any, text: String) {
            }
        })
        mLogHistory.dataBinding()
        mRvChannels.adapter = mChannelAdapter
    }

    /***
     * Data binding for New Episodes
     */
    private fun bindNewEpisode() {
        val list = mStoDbHepler.getNewEpisode()
        // var list = channelsBean.data.media

        var arrayList = ArrayList<ChannelsBean.Data.Media>()
        if (list.size > 6) {
            for (i in 0 until 6) {
                arrayList.add(list[i])
            }
        } else {
            arrayList = list
        }
        mNewEpisodeAdapter = NewEpisodeAdapter(mActivity.applicationContext, arrayList, object :
            Click {
            override fun onclick(position: Int, `object`: Any, text: String) {
            }
        })
        mLogHistory.dataBinding()
        mRvNewEpisode.adapter = mNewEpisodeAdapter

    }
}