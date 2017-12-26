package xiaoyuz.com.tiktoktrainer.contract.presenter

import xiaoyuz.com.tiktoktrainer.constants.Mode
import xiaoyuz.com.tiktoktrainer.contract.MainContract

class MainPresenter(private val mView: MainContract.View) : MainContract.Presenter {

    init {
        mView.presenter = this
    }

    override fun subscribe() {
    }

    override fun unsubscribe() {
    }

    override fun toModeSetting(mode: Mode) {
        mView.toSettingFragment(mode)
    }
}