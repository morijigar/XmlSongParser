package com.example.apifetch.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.apifetch.activity.DetailsActivity
import com.example.apifetch.databinding.ItemPostsBinding
import com.example.apifetch.databinding.ItemSongsBinding
import com.example.apifetch.model.Entry
import com.example.apifetch.model.Result
import com.example.apifetch.util.Change

/**
 * This class is used to show each row of pass
 */
class SongListAdapter(
    private val context: Context,
    private val data: MutableList<Entry>
) : RecyclerView.Adapter<SongListAdapter.ViewHolder>() {
    private val layoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemSongsBinding.inflate(
                layoutInflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        val model = data[position]
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val listD = payloads as List<Change<Entry>>
            if (listD.isNotEmpty()) {
                holder.binding.model = model
            }
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val model = data[position]
        holder.binding.model = model
        holder.binding.executePendingBindings()

        holder.binding.llMain.setOnClickListener {
            val intent = Intent(holder.binding.llMain.context,DetailsActivity::class.java)
            intent.putExtra("songTitle",model.title)
            holder.binding.llMain.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateList(data: MutableList<Entry>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * View holder to hold individual row
     */
    inner class ViewHolder //binding.rlRoot.setOnClickListener(this);
        (var binding: ItemSongsBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        override fun onClick(v: View) {}

    }


    fun setItems(newItems: List<Entry>) {
        val result = DiffUtil.calculateDiff(
            MyListDiffUtilCallback(data, newItems)
        )
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newItems)
    }

    class MyListDiffUtilCallback(
        private var oldItems: List<Entry>,
        private var newItems: List<Entry>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition].id == newItems[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldItems[oldItemPosition] == newItems[newItemPosition]

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return Change(
                oldItem,
                newItem
            )
        }
    }

}