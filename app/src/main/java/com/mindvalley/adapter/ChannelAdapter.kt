package com.mindvalley.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindvalley.R
import com.mindvalley.bean.ChannelSectionBean
import kotlinx.android.synthetic.main.row_channels.view.*
import kotlinx.android.synthetic.main.row_channels.view.iv_product_IMG
import kotlinx.android.synthetic.main.row_channels.view.tv_des
import kotlinx.android.synthetic.main.row_channels.view.tv_title
import com.mindvalley.listeners.Click

class ChannelAdapter() : RecyclerView.Adapter<ChannelAdapter.ViewHolder>() {

    private var mArrayList = ArrayList<ChannelSectionBean.Data.Channel>()
    private lateinit var mClick: Click
    private lateinit var mContext: Context

    /**
     * Class constructor
     */
    constructor(context: Context, arrayListnew: ArrayList<ChannelSectionBean.Data.Channel>, clickval: Click) : this() {
        this.mContext = context
        this.mClick = clickval
        this.mArrayList.addAll(arrayListnew)
    }

    /**
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_channels, parent, false)
        return ViewHolder(v)
    }

    /***
     * Data binding for view
     */
    @SuppressLint("WrongConstant", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tempBean: ChannelSectionBean.Data.Channel = mArrayList.get(position)

        holder.tv_title.text = tempBean.title
        holder.tv_des.text = tempBean.mediaCount.toString() + " " + mContext.resources.getString(R.string.episode)

        Glide.with(mContext)  //2
            .load(tempBean.coverAsset.url) //3
            .placeholder(R.mipmap.ic_launcher) //5
            .error(R.mipmap.ic_launcher) //6
            .into(holder.iv_product_IMG)

        val list = tempBean.series
        val list1 = tempBean.latestMedia
        if (list.size == 0) {
            holder.tv_des.text = tempBean.mediaCount.toString() + " " + mContext.resources.getString(R.string.episode)
            var arrayList = ArrayList<ChannelSectionBean.Data.Channel.LatestMedia>()
            if (list1.size > 6) {
                for (i in 0 until 6) {
                    arrayList.add(tempBean.latestMedia[i])
                }
            } else {
                arrayList = tempBean.latestMedia
            }
            holder.rv_channels1.setNestedScrollingEnabled(false)
            holder.rv_channels1.layoutManager =
                LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false)
            val courseAdapter =
                CourseAdapter(mContext.applicationContext, arrayList, object : Click {
                    override fun onclick(position: Int, `object`: Any, text: String) {

                    }
                })
            holder.rv_channels1!!.adapter = courseAdapter
        } else {
            holder.tv_des.text = tempBean.mediaCount.toString() + " " + mContext.resources.getString(R.string.series)
            var arrayList = ArrayList<ChannelSectionBean.Data.Channel.Sery>()
            if (list.size > 6) {
                for (i in 0 until 6) {
                    arrayList.add(tempBean.series[i])
                }
            } else {
                arrayList = tempBean.series
            }

            holder.rv_channels1.isNestedScrollingEnabled = false
            holder.rv_channels1.layoutManager =
                LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false)
            val seriesAdapter =
                SeriesAdapter(mContext.applicationContext, arrayList, object : Click {
                    override fun onclick(position: Int, `object`: Any, text: String) {

                    }
                })
            holder.rv_channels1!!.adapter = seriesAdapter
        }

    }

    /***
     * Method is giving the size of the list
     */
    override fun getItemCount(): Int {
        return mArrayList.size
    }

    /***
     * Class is hodling the list view
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val iv_product_IMG = view.iv_product_IMG
        val tv_title = view.tv_title
        val tv_des = view.tv_des
        val rv_channels1 = view.rv_channels1


    }


}