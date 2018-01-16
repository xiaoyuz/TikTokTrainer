package xiaoyuz.com.tiktoktrainer.contract.view.adapter.helper

interface ItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    fun onItemDismiss(position: Int)
}