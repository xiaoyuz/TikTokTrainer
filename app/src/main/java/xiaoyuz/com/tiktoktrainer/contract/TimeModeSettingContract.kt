package xiaoyuz.com.tiktoktrainer.contract

import xiaoyuz.com.tiktoktrainer.base.BasePresenter
import xiaoyuz.com.tiktoktrainer.base.BaseView
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule

interface TimeModeSettingContract {

    interface View : BaseView<Presenter> {
        fun toTrainingFragment(schedules: List<TrainingSchedule>)
    }

    interface Presenter : BasePresenter {
        fun start(roundTimeIndex: Int, restTimeIndex: Int, count: Int)
    }
}