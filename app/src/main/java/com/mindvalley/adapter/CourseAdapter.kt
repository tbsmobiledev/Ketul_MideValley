package com.mindvalley.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindvalley.R
import com.mindvalley.bean.ChannelSectionBean
import kotlinx.android.synthetic.main.row_course.view.*
import kotlinx.android.synthetic.main.row_new_episode.view.tv_title
import com.mindvalley.listeners.Click

class CourseAdapter() : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {

    private var mArrayList = ArrayList<ChannelSectionBean.Data.Channel.LatestMedia>()
    private lateinit  var mCclick: Click
    private lateinit  var mContext: Context

    /**
     * Class constructor
     */
    constructor(context: Context, arrayListnew: ArrayList<ChannelSectionBean.Data.Channel.LatestMedia>,clickval: Click) : this() {
        this.mContext = context
        this.mCclick = clickval
        this.mArrayList.addAll(arrayListnew)
    }

    /**
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_course, parent, false)
        return ViewHolder(v)
    }

    /***
     * Data binding for view
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tempBean:ChannelSectionBean.Data.Channel.LatestMedia = mArrayList.get(position)

        holder.tv_title.setText(tempBean.title)

       Glide.with(mContext)
            .load(tempBean.coverAsset.url)
            .placeholder(R.drawable.ic_place_holder)
            .error(R.drawable.ic_place_holder)
            .into(holder.iv_product_IMG)

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
        val iv_product_IMG  = view.iv_product_IMG1
        val tv_title = view.tv_title

    }


}