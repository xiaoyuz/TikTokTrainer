package xiaoyuz.com.tiktoktrainer.contract.presenter

import xiaoyuz.com.tiktoktrainer.constants.Mode
import xiaoyuz.com.tiktoktrainer.constants.ScheduleType
import xiaoyuz.com.tiktoktrainer.constants.restTimeValues
import xiaoyuz.com.tiktoktrainer.constants.roundTimeValues
import xiaoyuz.com.tiktoktrainer.contract.TimeModeSettingContract
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule

class TimeModeSettingPresenter(private val mView: TimeModeSettingContract.View) : TimeModeSettingContract.Presenter {

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun start(roundTimeIndex: Int, restTimeIndex: Int, count: Int) {
        val roundTime = roundTimeValues[roundTimeIndex].toInt()
        val restTime = restTimeValues[restTimeIndex].toInt()
        val schedules = mutableListOf<TrainingSchedule>()
        for (i in 0..count) {
            schedules.add(TrainingSchedule(mode = Mode.TIME_MODE, type = ScheduleType.FIGHTING,
                    time = roundTime))
            schedules.add(TrainingSchedule(mode = Mode.TIME_MODE, type = ScheduleType.REST,
                    time = restTime))
        }
        mView.toTrainingFragment(schedules)
    }
}