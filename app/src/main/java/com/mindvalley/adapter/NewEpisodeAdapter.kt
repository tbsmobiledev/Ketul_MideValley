package com.mindvalley.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindvalley.R
import com.mindvalley.bean.ChannelsBean
import kotlinx.android.synthetic.main.row_new_episode.view.*
import com.mindvalley.listeners.Click

class NewEpisodeAdapter() : RecyclerView.Adapter<NewEpisodeAdapter.ViewHolder>() {

    private var mArrayList = ArrayList<ChannelsBean.Data.Media>()
    private lateinit  var mClick: Click
    private lateinit  var mContext: Context

    /**
     * Class constructor
     */
    constructor(context: Context, arrayListnew: ArrayList<ChannelsBean.Data.Media>,clickval: Click) : this() {
        this.mContext = context
        this.mClick = clickval
        this.mArrayList.addAll(arrayListnew)
    }

    /**
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_new_episode, parent, false)
        return ViewHolder(v)
    }

    /***
     * Data binding for view
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tempBean:ChannelsBean.Data.Media = mArrayList[position]

        holder.tv_title.text = tempBean.title
        holder.tv_des.text = tempBean.channel.title

        Glide.with(mContext)
            .load(tempBean.coverAsset.url)
            .placeholder(R.drawable.ic_place_holder_rec)
            .error(R.drawable.ic_place_holder_rec)
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
        val iv_product_IMG  = view.iv_product_IMG
        val tv_title = view.tv_title
        val tv_des = view.tv_des

    }

}