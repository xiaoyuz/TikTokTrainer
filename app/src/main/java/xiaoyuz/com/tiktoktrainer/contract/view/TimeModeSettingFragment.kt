package xiaoyuz.com.tiktoktrainer.contract.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_time_mode_setting.*
import xiaoyuz.com.tiktoktrainer.R
import xiaoyuz.com.tiktoktrainer.constants.restTimeValues
import xiaoyuz.com.tiktoktrainer.constants.roundTimeValues
import xiaoyuz.com.tiktoktrainer.contract.TimeModeSettingContract
import xiaoyuz.com.tiktoktrainer.contract.presenter.TrainingPresenter
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule
import xiaoyuz.com.tiktoktrainer.utils.addFragment

class TimeModeSettingFragment : Fragment(), TimeModeSettingContract.View {

    override lateinit var presenter: TimeModeSettingContract.Presenter

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initNumberPicks()
        start.setOnClickListener {
            presenter.start(roundTime.value, restTime.value, count.value)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_time_mode_setting, container, false)

    private fun initNumberPicks() {
        roundTime.apply {
            displayedValues = roundTimeValues
            minValue = 0
            maxValue = roundTimeValues.size - 1
            value = 4
        }
        restTime.apply {
            displayedValues = restTimeValues
            minValue = 0
            maxValue = restTimeValues.size - 1
            value = 1
        }
        count.apply {
            maxValue = 30
            minValue = 1
            value = 4
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun toTrainingFragment(schedules: List<TrainingSchedule>) {
        val schedulesJsonStr = Gson().toJson(schedules)
        val trainingFragment = TrainingFragment().apply {
            arguments = Bundle().apply { putString("schedules", schedulesJsonStr) }
        }
        TrainingPresenter(trainingFragment)
        (activity as AppCompatActivity).addFragment(trainingFragment)
    }
}