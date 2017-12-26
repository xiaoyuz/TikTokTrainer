package xiaoyuz.com.tiktoktrainer.contract

import xiaoyuz.com.tiktoktrainer.base.BasePresenter
import xiaoyuz.com.tiktoktrainer.base.BaseView
import xiaoyuz.com.tiktoktrainer.domain.ScheduleItem
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule

interface TrainingContract {

    interface View : BaseView<Presenter> {
        fun ticTokProgress(scheduleItem: ScheduleItem, status: String)

        fun updatePauseButton()
    }

    interface Presenter : BasePresenter {
        fun startTicTok(schedules: List<TrainingSchedule>)

        fun cancelTicTok()

        fun progressControlSet(pause: Boolean)
    }
}