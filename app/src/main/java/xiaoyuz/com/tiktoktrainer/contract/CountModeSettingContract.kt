package xiaoyuz.com.tiktoktrainer.contract

import xiaoyuz.com.tiktoktrainer.base.BasePresenter
import xiaoyuz.com.tiktoktrainer.base.BaseView
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule

interface CountModeSettingContract {

    interface View : BaseView<Presenter> {
        fun toTrainingFragment(schedules: List<TrainingSchedule>)
    }

    interface Presenter : BasePresenter {
        fun start(roundCountIndex: Int, perTimeIndex: Int, restTimeIndex: Int, count: Int)
    }
}