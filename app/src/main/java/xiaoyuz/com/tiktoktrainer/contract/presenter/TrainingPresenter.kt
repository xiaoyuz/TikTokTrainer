package xiaoyuz.com.tiktoktrainer.contract.presenter

import xiaoyuz.com.tiktoktrainer.contract.TrainingContract
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule
import xiaoyuz.com.tiktoktrainer.timer.TicAsyncTask

class TrainingPresenter(private val mView: TrainingContract.View) : TrainingContract.Presenter {

    private lateinit var mTicTask: TicAsyncTask

    init {
        mView.presenter = this
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun startTicTok(schedules: List<TrainingSchedule>) {
        mTicTask = TicAsyncTask(roundTikTokFunc = {
            mView.ticTokProgress(it, "WORK OUT")
        }, restTikTokFunc = {
            mView.ticTokProgress(it, "REST NOW")
        })
        mTicTask.execute(schedules)
    }

    override fun cancelTicTok() {
        mTicTask.cancel(true)
    }

    override fun progressControlSet(pause: Boolean) {
        mTicTask.controlProgress(pause)
        mView.updatePauseButton()
    }
}