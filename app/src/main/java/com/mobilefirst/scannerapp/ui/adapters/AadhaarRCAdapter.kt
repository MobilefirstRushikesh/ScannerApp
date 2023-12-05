package com.mobilefirst.scannerapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mobilefirst.scannerapp.R
import com.mobilefirst.scannerapp.model.AadhaarData

class AadhaarRCAdapter : RecyclerView.Adapter<AadhaarRCAdapter.ItemViewHolder>() {
    private var dataset = ArrayList<AadhaarData>()

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userNameTv = view.findViewById<TextView>(R.id.user_name_tv)
        val userUIDTv = view.findViewById<TextView>(R.id.user_uid_tv)
        val userDOBTv = view.findViewById<TextView>(R.id.user_dob_tv)
        val userGenderTv = view.findViewById<TextView>(R.id.user_gender_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapter = LayoutInflater.from(parent.context).inflate(R.layout.aadhaar_rc_item, parent, false)
        return ItemViewHolder(adapter)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.apply {
            userNameTv.text =  item.name
            userUIDTv.text = "UID: ${item.uid}"
            userDOBTv.text = "DOB: ${item.yob}"
            userGenderTv.text = "Gender: ${item.gender}"
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<AadhaarData>) {
        val diffResult = DiffUtil.calculateDiff(AadhaarDataDiffCallback(dataset, newData))
        dataset.clear()
        dataset.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    // DiffUtil callback class
    private class AadhaarDataDiffCallback(private val oldList: List<AadhaarData>, private val newList: List<AadhaarData>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].uid == newList[newItemPosition].uid
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
