package xiaoyuz.com.tiktoktrainer.contract.view

import android.os.*
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_training.*
import xiaoyuz.com.tiktoktrainer.R
import xiaoyuz.com.tiktoktrainer.constants.ScheduleType
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule
import xiaoyuz.com.tiktoktrainer.utils.fromJson

class TrainingFragment : Fragment() {

    private lateinit var mSchedules: List<TrainingSchedule>
    private lateinit var mTicTask: TicAsyncTask

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_training, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mSchedules = Gson().fromJson<List<TrainingSchedule>>(arguments.getString("schedules"))
        mTicTask = TicAsyncTask()
        mTicTask.execute(mSchedules)
    }

    inner class TicAsyncTask : AsyncTask<List<TrainingSchedule>, Int, Unit>() {
        override fun doInBackground(vararg params: List<TrainingSchedule>) {
            val timeList = params[0].flatMap { schedule ->
                (0..schedule.time!!).map { it to schedule}
            }
            var tikCount = 0
            while (true) {
                if (tikCount >= timeList.size) {
                    break
                }
                val (time, schedule) = timeList[tikCount]
//                when (schedule.type) {
//                    ScheduleType.FIGHTING -> roundTikTok(time)
//                    ScheduleType.REST -> restTikTok(time)
//                }
                publishProgress(time)
                tikCount += 1
                if (time == schedule.time!! && schedule.type == ScheduleType.FIGHTING) {
//                    roundFinish()
                }
                Thread.sleep(1000)
            }
        }

        override fun onProgressUpdate(vararg values: Int?) {
            timerTv.text = values[0].toString()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTicTask.cancel(true)
    }

}