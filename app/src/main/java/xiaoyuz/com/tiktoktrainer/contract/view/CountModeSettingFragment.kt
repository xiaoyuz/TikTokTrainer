package xiaoyuz.com.tiktoktrainer.contract.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_count_mode_setting.*
import xiaoyuz.com.tiktoktrainer.R
import xiaoyuz.com.tiktoktrainer.constants.perTimeValues
import xiaoyuz.com.tiktoktrainer.constants.restTimeValues
import xiaoyuz.com.tiktoktrainer.constants.roundCountValues
import xiaoyuz.com.tiktoktrainer.constants.roundTimeValues
import xiaoyuz.com.tiktoktrainer.contract.CountModeSettingContract
import xiaoyuz.com.tiktoktrainer.contract.presenter.TrainingPresenter
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule
import xiaoyuz.com.tiktoktrainer.utils.addFragment

class CountModeSettingFragment : Fragment(), CountModeSettingContract.View {

    override lateinit var presenter: CountModeSettingContract.Presenter

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initNumberPicks()
        startButton.setOnClickListener {
            presenter.start(roundCount.value, perTime.value, restTime.value, count.value)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_count_mode_setting, container, false)

    private fun initNumberPicks() {
        roundCount.apply {
            displayedValues = roundCountValues
            minValue = 0
            maxValue = roundTimeValues.size - 1
            value = 2
        }
        perTime.apply {
            displayedValues = perTimeValues
            minValue = 0
            maxValue = perTimeValues.size - 1
            value = 1
        }
        restTime.apply {
            displayedValues = restTimeValues
            minValue = 0
            maxValue = restTimeValues.size - 1
            value = 2
        }
        count.apply {
            maxValue = 30
            minValue = 1
            value = 4
        }
    }

    override fun toTrainingFragment(schedules: List<TrainingSchedule>) {
        val schedulesJsonStr = Gson().toJson(schedules)
        val trainingFragment = TrainingFragment().apply {
            arguments = Bundle().apply { putString("schedules", schedulesJsonStr) }
        }
        TrainingPresenter(trainingFragment)
        (activity as AppCompatActivity).addFragment(trainingFragment)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }
}