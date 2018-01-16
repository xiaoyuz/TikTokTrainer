package xiaoyuz.com.tiktoktrainer.contract.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_custom_mode_item_setting.*
import xiaoyuz.com.tiktoktrainer.R
import xiaoyuz.com.tiktoktrainer.constants.*
import xiaoyuz.com.tiktoktrainer.contract.CustomModeListContract
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule

class CustomModeItemSettingFragment(private val mPresenter: CustomModeListContract.Presenter) : Fragment() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initNumberPicks()
        addButton.setOnClickListener {
            backToListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_custom_mode_item_setting, container, false)

    private fun initNumberPicks() {
        roundCount.apply {
            displayedValues = roundCountValues
            minValue = 0
            maxValue = roundTimeValues.size - 1
            value = 2
        }
        perTime.apply {
            displayedValues = perTimeValues
            minValue = 0
            maxValue = perTimeValues.size - 1
            value = 1
        }
        restTime.apply {
            displayedValues = restTimeValues
            minValue = 0
            maxValue = restTimeValues.size - 1
            value = 2
        }
        count.apply {
            maxValue = 30
            minValue = 1
            value = 4
        }
    }

    fun backToListFragment() {
        val roundCount = roundCountValues[roundCount.value].toInt()
        val perTime = perTimeValues[perTime.value].toInt()
        val restTime = restTimeValues[restTime.value].toInt()
        val schedules = mutableListOf<TrainingSchedule>()
        for (i in 0..count.value - 1) {
            schedules.add(TrainingSchedule(mode = Mode.COUNT_MODE, type = ScheduleType.FIGHTING,
                    count = roundCount, perTime = perTime, name = "${itemName.text}"))
            schedules.add(TrainingSchedule(mode = Mode.TIME_MODE, type = ScheduleType.REST,
                    time = restTime, name = "Rest!"))
        }
        mPresenter.commitItem(schedules)
        fragmentManager.popBackStack()
    }
}