package xiaoyuz.com.tiktoktrainer.contract.view.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.custom_mode_item.view.*
import xiaoyuz.com.tiktoktrainer.R
import xiaoyuz.com.tiktoktrainer.constants.ScheduleType
import xiaoyuz.com.tiktoktrainer.contract.view.adapter.helper.ItemTouchHelperAdapter
import xiaoyuz.com.tiktoktrainer.contract.view.adapter.helper.ItemTouchHelperViewHolder
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule
import java.util.*

private const val ITEM_TYPE = 1

class CustomModeListAdapter(private val mTrainingSchedules: MutableList<TrainingSchedule>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder {
        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }
        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_mode_item, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ItemViewHolder) {
            if (mTrainingSchedules[position].type == ScheduleType.FIGHTING) {
                holder.itemView.name.text = mTrainingSchedules[position].name
                holder.itemView.count.text = "${mTrainingSchedules[position].count.toString()}次"
                holder.itemView.perTime.text = "每次${mTrainingSchedules[position].perTime.toString()}s"
            } else if (mTrainingSchedules[position].type == ScheduleType.REST) {
                holder.itemView.name.text = "Rest"
                holder.itemView.count.visibility = View.GONE
                holder.itemView.perTime.text = "${mTrainingSchedules[position].time}s"
            }
            holder.itemView.tag = mTrainingSchedules[position]
        }
    }

    override fun getItemViewType(position: Int) = ITEM_TYPE

    override fun getItemCount() = mTrainingSchedules.size

    override fun onItemDismiss(position: Int) {
        mTrainingSchedules.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(mTrainingSchedules, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }
}