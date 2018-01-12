package xiaoyuz.com.tiktoktrainer.contract.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_main.*
import xiaoyuz.com.tiktoktrainer.R
import xiaoyuz.com.tiktoktrainer.constants.Mode
import xiaoyuz.com.tiktoktrainer.contract.MainContract
import xiaoyuz.com.tiktoktrainer.contract.presenter.CountModeSettingPresenter
import xiaoyuz.com.tiktoktrainer.contract.presenter.CustomModeListPresenter
import xiaoyuz.com.tiktoktrainer.contract.presenter.MainPresenter
import xiaoyuz.com.tiktoktrainer.contract.presenter.TimeModeSettingPresenter
import xiaoyuz.com.tiktoktrainer.utils.addFragment

class MainFragment : Fragment(), MainContract.View {

    override lateinit var presenter: MainContract.Presenter

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        time.setOnClickListener { presenter.toModeSetting(Mode.TIME_MODE) }
        count.setOnClickListener { presenter.toModeSetting(Mode.COUNT_MODE) }
        custom.setOnClickListener { presenter.toModeSetting(Mode.CUSTOM_MODE) }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_main, container, false)

    override fun toSettingFragment(mode: Mode) {
        when (mode) {
            Mode.TIME_MODE -> TimeModeSettingFragment().also {
                TimeModeSettingPresenter(it)
                (activity as AppCompatActivity).addFragment(it)
            }
            Mode.COUNT_MODE -> CountModeSettingFragment().also {
                CountModeSettingPresenter(it)
                (activity as AppCompatActivity).addFragment(it)
            }
            Mode.CUSTOM_MODE -> CustomModeListFragment().also {
                CustomModeListPresenter(it)
                (activity as AppCompatActivity).addFragment(it)
            }
        }
    }
}