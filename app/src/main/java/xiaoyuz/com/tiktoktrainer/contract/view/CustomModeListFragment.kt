package xiaoyuz.com.tiktoktrainer.contract.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_custom_mode_list.*
import xiaoyuz.com.tiktoktrainer.R
import xiaoyuz.com.tiktoktrainer.contract.CustomModeListContract
import xiaoyuz.com.tiktoktrainer.contract.presenter.TrainingPresenter
import xiaoyuz.com.tiktoktrainer.contract.view.adapter.CustomModeListAdapter
import xiaoyuz.com.tiktoktrainer.contract.view.adapter.helper.ItemTouchHelperAdapter
import xiaoyuz.com.tiktoktrainer.contract.view.adapter.helper.SimpleItemTouchHelperCallback
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule
import xiaoyuz.com.tiktoktrainer.utils.addFragment

class CustomModeListFragment : Fragment(), CustomModeListContract.View {

    private var mTrainingSchedules: MutableList<TrainingSchedule> = mutableListOf()
    private lateinit var mItemTouchHelper: ItemTouchHelper

    override lateinit var presenter: CustomModeListContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_custom_mode_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        itemList.layoutManager = LinearLayoutManager(context)
        itemList.adapter = CustomModeListAdapter(mTrainingSchedules)
        itemList.adapter.notifyDataSetChanged()
        itemList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val callback = SimpleItemTouchHelperCallback(itemList.adapter as ItemTouchHelperAdapter)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper.attachToRecyclerView(itemList)

        startButton.setOnClickListener {
            toTrainingFragment(mTrainingSchedules)
        }
        addButton.setOnClickListener {
            presenter.createItem()
        }
    }

    override fun toItemCreateFragment() {
        val itemFragment = CustomModeItemSettingFragment(presenter)
        (activity as AppCompatActivity).addFragment(itemFragment)
    }

    override fun updateSchedules(trainingSchedules: List<TrainingSchedule>) {
        mTrainingSchedules.addAll(trainingSchedules)
        itemList.adapter.notifyDataSetChanged()
        itemList.visibility = View.VISIBLE
        noItemTv.visibility = View.GONE
    }

    fun toTrainingFragment(schedules: List<TrainingSchedule>) {
        val schedulesJsonStr = Gson().toJson(schedules)
        val trainingFragment = TrainingFragment().apply {
            arguments = Bundle().apply { putString("schedules", schedulesJsonStr) }
        }
        TrainingPresenter(trainingFragment)
        (activity as AppCompatActivity).addFragment(trainingFragment)
    }
}