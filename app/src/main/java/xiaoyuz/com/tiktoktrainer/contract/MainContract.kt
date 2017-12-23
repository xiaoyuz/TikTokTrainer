package xiaoyuz.com.tiktoktrainer.contract

import xiaoyuz.com.tiktoktrainer.base.BasePresenter
import xiaoyuz.com.tiktoktrainer.base.BaseView
import xiaoyuz.com.tiktoktrainer.constants.Mode

interface MainContract {

    interface View : BaseView<Presenter> {
        fun toSettingFragment(mode: Mode)
    }

    interface Presenter : BasePresenter {
        fun toModeSetting(mode: Mode)
    }
}