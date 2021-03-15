package co.mewi.maze.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import co.mewi.maze.R

class MazeGridAdapter : RecyclerView.Adapter<BlockViewHolder>() {

    private var list = listOf<BlockViewItem>()

    private var start = -1
    private var finish = -1
    private var current = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockViewHolder {
        return BlockViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_block, null, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BlockViewHolder, position: Int) {
        holder.bind(
            list[position],
            when (position) {
                start -> android.R.color.transparent
                current -> android.R.color.holo_blue_bright
                finish -> android.R.color.holo_green_light
                else -> android.R.color.transparent
            }
        )
    }

    fun updateList(list: List<BlockViewItem>, start: Int?, finish: Int?, current: Int?) {
        this.list = list
        start?.let { this.start = start }
        finish?.let { this.finish = finish }
        current?.let { this.current = current }
        notifyDataSetChanged()
    }
}

class BlockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(blockItem: BlockViewItem, centerRes: Int = android.R.color.transparent) {
        setVisibility(itemView.findViewById<View>(R.id.left), blockItem.hideLeft)
        setVisibility(itemView.findViewById<View>(R.id.right), blockItem.hideRight)
        setVisibility(itemView.findViewById<View>(R.id.top), blockItem.hideTop)
        setVisibility(itemView.findViewById<View>(R.id.bottom), blockItem.hideBottom)
        itemView.findViewById<ImageView>(R.id.center_icon).setBackgroundResource(centerRes)
    }

    private fun setVisibility(view: View, hide: Boolean) {
        view.visibility = if (hide) View.GONE else View.VISIBLE
    }
}