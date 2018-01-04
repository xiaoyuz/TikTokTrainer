package xiaoyuz.com.tiktoktrainer.contract.view

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.*
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_training.*
import xiaoyuz.com.tiktoktrainer.R
import xiaoyuz.com.tiktoktrainer.constants.Mode
import xiaoyuz.com.tiktoktrainer.constants.ProgressStatus
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
        val allSchedules = listOf(TrainingSchedule(Mode.TIME_MODE, ScheduleType.GET_READY, 5)) + mSchedules
        presenter.startTicTok(allSchedules)
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

    override fun ticTokProgress(scheduleItem: ScheduleItem?, status: ProgressStatus) {
        scheduleItem?.let {
            val (time, turn, schedule) = scheduleItem
            schedule.time?.let { progress.setMaxValues(it.toFloat()) }
            progress.setCurrentValues(time.toFloat())
            turnCountTv.text = turn.toString()
            statusTv.text = when (status) {
                ProgressStatus.WORK_OUT -> resources.getString(R.string.status_work_out)
                ProgressStatus.REST_NOW -> resources.getString(R.string.status_rest_now)
                ProgressStatus.PAUSE -> resources.getString(R.string.status_pause)
                ProgressStatus.GET_READY -> resources.getString(R.string.status_get_ready)
            }
            backgroundLayout.setBackgroundResource(when (status) {
                ProgressStatus.WORK_OUT -> R.color.color_work_out_background
                ProgressStatus.REST_NOW -> R.color.color_rest_now_background
                ProgressStatus.PAUSE -> R.color.color_get_ready_background
                ProgressStatus.GET_READY -> R.color.color_get_ready_background
            })
            tikTokAlarm(schedule)
        }
    }

    override fun onProgressPause() {
        pauseButton.setImageResource(if (mPause) android.R.drawable.ic_media_play else android.R.drawable.ic_media_pause)
        statusTv.text = resources.getString(R.string.status_pause)
        backgroundLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.color_get_ready_background))
    }

    private fun tikTokAlarm(schedule: TrainingSchedule) {
        mToneGen.startTone(
                when (schedule.type) {
                    ScheduleType.GET_READY -> ToneGenerator.TONE_CDMA_REORDER
                    ScheduleType.FIGHTING -> ToneGenerator.TONE_CDMA_PIP
                    ScheduleType.REST -> ToneGenerator.TONE_CDMA_REORDER
                }, 150)
    }
}