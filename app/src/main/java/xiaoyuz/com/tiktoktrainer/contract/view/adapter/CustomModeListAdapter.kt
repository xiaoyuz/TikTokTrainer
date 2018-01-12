package xiaoyuz.com.tiktoktrainer.contract.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.custom_mode_item.view.*
import xiaoyuz.com.tiktoktrainer.R
import xiaoyuz.com.tiktoktrainer.contract.CustomModeListContract
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule

private const val ITEM_TYPE = 1
private const val ADD_TYPE = 2

class CustomModeListAdapter(private val mTrainingSchedules: List<TrainingSchedule>,
                            private val mPresenter: CustomModeListContract.Presenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    inner class AddViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                mPresenter.createItem()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ITEM_TYPE -> ItemViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.custom_mode_item, parent, false))
        else -> AddViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.custom_mode_add_item, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ItemViewHolder) {
            holder.itemView.name.text = mTrainingSchedules[position].name ?: ""
            holder.itemView.tag = mTrainingSchedules[position]
        }
    }

    override fun getItemViewType(position: Int)
            = if (position == mTrainingSchedules.size) ADD_TYPE else ITEM_TYPE

    override fun getItemCount() = mTrainingSchedules.size + 1
}