package xiaoyuz.com.tiktoktrainer.contract.presenter

import xiaoyuz.com.tiktoktrainer.constants.*
import xiaoyuz.com.tiktoktrainer.contract.CountModeSettingContract
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule

class CountModeSettingPresenter(private val mView: CountModeSettingContract.View) : CountModeSettingContract.Presenter {

    init {
        mView.presenter = this
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun start(roundCountIndex: Int, perTimeIndex: Int, restTimeIndex: Int, count: Int) {
        val roundCount = roundCountValues[roundCountIndex].toInt()
        val perTime = perTimeValues[perTimeIndex].toInt()
        val restTime = restTimeValues[restTimeIndex].toInt()
        val schedules = mutableListOf<TrainingSchedule>()
        for (i in 0..count - 1) {
            schedules.add(TrainingSchedule(mode = Mode.COUNT_MODE, type = ScheduleType.FIGHTING,
                    count = roundCount, perTime = perTime))
            schedules.add(TrainingSchedule(mode = Mode.TIME_MODE, type = ScheduleType.REST,
                    time = restTime))
        }
        mView.toTrainingFragment(schedules)
    }
}