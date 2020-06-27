package com.mindvalley.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindvalley.R
import com.mindvalley.bean.CategoryBean
import kotlinx.android.synthetic.main.row_channels.view.tv_title
import com.mindvalley.listeners.Click

class CategoriesAdapter() : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private var mArrayList = ArrayList<CategoryBean.Data.Category>()
    private lateinit var mClick: Click
    private lateinit var mContext: Context

    /**
     * Class constructor
     */
    constructor(context: Context, arrayListnew: ArrayList<CategoryBean.Data.Category>, clickval: Click) : this() {
        this.mContext = context
        this.mClick = clickval
        this.mArrayList.addAll(arrayListnew)
    }

    /**
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_categories, parent, false)
        return ViewHolder(v)
    }

    /***
     * Data binding for view
     */
    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tempBean: CategoryBean.Data.Category = mArrayList[position]

        holder.mTv_title.text = tempBean.name

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
        val mTv_title = view.tv_title
    }

}