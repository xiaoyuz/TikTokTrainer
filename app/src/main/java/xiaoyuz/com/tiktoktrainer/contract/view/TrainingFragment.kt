package xiaoyuz.com.tiktoktrainer.contract.view

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.*
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_training.*
import xiaoyuz.com.tiktoktrainer.R
import xiaoyuz.com.tiktoktrainer.constants.ScheduleType
import xiaoyuz.com.tiktoktrainer.contract.TrainingContract
import xiaoyuz.com.tiktoktrainer.domain.ScheduleItem
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule
import xiaoyuz.com.tiktoktrainer.utils.fromJson

class TrainingFragment : Fragment(), TrainingContract.View {

    private lateinit var mSchedules: List<TrainingSchedule>
    private val mToneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 100)

    override lateinit var presenter: TrainingContract.Presenter

    private var mPause = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?)
            = inflater?.inflate(R.layout.fragment_training, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        mSchedules = Gson().fromJson<List<TrainingSchedule>>(arguments.getString("schedules"))
        presenter.startTicTok(mSchedules)
        pauseButton.setOnClickListener {
            mPause = !mPause
            presenter.progressControlSet(mPause)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancelTicTok()
    }

    override fun ticTokProgress(scheduleItem: ScheduleItem, status: String) {
        val (time, turn, schedule) = scheduleItem
        progress.setMaxValues(schedule.time!!.toFloat())
        progress.setCurrentValues(time.toFloat())
        turnCountTv.text = turn.toString()
        statusTv.text = status
        tikTokAlarm(schedule)
    }

    override fun updatePauseButton() {
        pauseButton.setImageResource(if (mPause) android.R.drawable.ic_media_play else android.R.drawable.ic_media_pause)
    }

    private fun tikTokAlarm(schedule: TrainingSchedule) {
        mToneGen.startTone(
                when (schedule.type) {
                    ScheduleType.FIGHTING ->ToneGenerator.TONE_CDMA_PIP
                    ScheduleType.REST ->ToneGenerator.TONE_CDMA_REORDER
                }, 150)
    }
}