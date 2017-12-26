package xiaoyuz.com.tiktoktrainer.timer

import android.os.AsyncTask
import xiaoyuz.com.tiktoktrainer.constants.ScheduleType
import xiaoyuz.com.tiktoktrainer.domain.ScheduleItem
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule

class TicAsyncTask(val roundTikTokFunc: (ScheduleItem) -> Unit,
                   val restTikTokFunc: (ScheduleItem) -> Unit) : AsyncTask<List<TrainingSchedule>, ScheduleItem, Unit>() {

    private var mPause = false

    override fun doInBackground(vararg params: List<TrainingSchedule>) {
        var index = 0
        val scheduleItems = params[0].flatMap { schedule ->
            index += 1
            (0..schedule.time!!).map { ScheduleItem(it, index, schedule) }
        }
        var tikCount = 0
        try {
            while (!isCancelled) {
                while (!isCancelled && mPause) {
                    Thread.sleep(500)
                }
                if (tikCount >= scheduleItems.size) {
                    break
                }
                val (time, turn, schedule) = scheduleItems[tikCount]
                publishProgress(scheduleItems[tikCount])
                tikCount += 1
                if (time == schedule.time!! && schedule.type == ScheduleType.FIGHTING) {
//                    roundFinish()
                }
                Thread.sleep(1000)
            }
        } catch (e: Exception) {
        }
    }

    override fun onProgressUpdate(vararg values: ScheduleItem?) {
        val scheduleItem = values[0]!!
        when (scheduleItem.schedule.type) {
            ScheduleType.FIGHTING -> roundTikTokFunc(scheduleItem)
            ScheduleType.REST -> restTikTokFunc(scheduleItem)
        }
    }

    fun controlProgress(pause: Boolean) {
        mPause = pause
    }
}