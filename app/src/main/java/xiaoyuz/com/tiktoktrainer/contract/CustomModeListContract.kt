package xiaoyuz.com.tiktoktrainer.contract

import xiaoyuz.com.tiktoktrainer.base.BasePresenter
import xiaoyuz.com.tiktoktrainer.base.BaseView
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule

interface CustomModeListContract {

    interface View : BaseView<Presenter> {
        fun toItemCreateFragment()

        fun updateSchedules(trainingSchedules: List<TrainingSchedule>)
    }

    interface Presenter : BasePresenter {
        fun createItem()

        fun commitItem(trainingSchedules: List<TrainingSchedule>)
    }
}