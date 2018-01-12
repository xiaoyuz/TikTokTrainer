package xiaoyuz.com.tiktoktrainer.contract.presenter

import xiaoyuz.com.tiktoktrainer.contract.CustomModeListContract
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule

class CustomModeListPresenter(private val mView: CustomModeListContract.View) : CustomModeListContract.Presenter {

    init {
        mView.presenter = this
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun commitItem(trainingSchedules: List<TrainingSchedule>) {
        mView.updateSchedules(trainingSchedules)
    }

    override fun createItem() {
        mView.toItemCreateFragment()
    }
}