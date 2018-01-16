package xiaoyuz.com.tiktoktrainer.contract.view.adapter.helper

import android.graphics.Canvas
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

private const val ALPHA_FULL = 1.0f

class SimpleItemTouchHelperCallback(private val mAdapter: ItemTouchHelperAdapter) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled() = true

    override fun isItemViewSwipeEnabled() = true

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) = if (recyclerView.layoutManager is GridLayoutManager) {
        makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, 0)
    } else {
        makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.START or ItemTouchHelper.END)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        if (viewHolder.itemViewType != target.itemViewType) {
            return false
        }
        mAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mAdapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView,
                             viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
                             actionState: Int, isCurrentlyActive: Boolean) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            viewHolder.itemView.alpha = ALPHA_FULL - Math.abs(dX) / viewHolder.itemView.width.toFloat()
            viewHolder.itemView.translationX = dX
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is ItemTouchHelperViewHolder) {
            viewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.alpha = ALPHA_FULL
        if (viewHolder is ItemTouchHelperViewHolder) {
            viewHolder.onItemClear()
        }
    }
}