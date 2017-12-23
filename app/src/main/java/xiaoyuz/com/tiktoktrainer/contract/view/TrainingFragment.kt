package xiaoyuz.com.tiktoktrainer.contract.view

import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_training.*
import xiaoyuz.com.tiktoktrainer.R
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule
import xiaoyuz.com.tiktoktrainer.utils.fromJson

class TrainingFragment : Fragment() {

    private lateinit var mSchedules: List<TrainingSchedule>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_training, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mSchedules = Gson().fromJson<List<TrainingSchedule>>(arguments.getString("schedules"))
        var currentTurn = 0
        chronometer.setOnChronometerTickListener {
            if ((SystemClock.elapsedRealtime() - chronometer.base)
                    > (mSchedules[currentTurn].time!! + 1) * 1000 ){
                it.base = SystemClock.elapsedRealtime()
                currentTurn += 1
                if (currentTurn == mSchedules.size) {
                    it.stop()
                }
            }
        }
        chronometer.start()
    }

}