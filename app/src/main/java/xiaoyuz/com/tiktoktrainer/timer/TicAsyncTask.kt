package xiaoyuz.com.tiktoktrainer.timer

import android.os.AsyncTask
import xiaoyuz.com.tiktoktrainer.constants.Mode
import xiaoyuz.com.tiktoktrainer.constants.ScheduleType
import xiaoyuz.com.tiktoktrainer.domain.ScheduleItem
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule

class TicAsyncTask(val roundTikTokFunc: (ScheduleItem) -> Unit,
                   val restTikTokFunc: (ScheduleItem) -> Unit,
                   val getReadyTiktokFunc: (ScheduleItem) -> Unit) : AsyncTask<List<TrainingSchedule>, ScheduleItem, Unit>() {

    private var mPause = false

    override fun doInBackground(vararg params: List<TrainingSchedule>) {
        val scheduleItems = genScheduleItems(params[0])
        var tikCount = 0
        try {
            while (!isCancelled) {
                while (!isCancelled && mPause) {
                    Thread.sleep(500)
                }
                if (tikCount >= scheduleItems.size) {
                    break
                }
                val (num, turn, schedule) = scheduleItems[tikCount]
                publishProgress(scheduleItems[tikCount])
                tikCount += 1
//                if (num == schedule.time!! && schedule.type == ScheduleType.FIGHTING) {
////                    roundFinish()
//                }
                Thread.sleep(1000)
            }
        } catch (e: Exception) {
        }
    }

    private fun genScheduleItems(trainingSchedules: List<TrainingSchedule>): List<ScheduleItem> {
        var index = 0
        return trainingSchedules.flatMap { schedule ->
            index += 1
            when (schedule.mode) {
                Mode.COUNT_MODE -> (0..schedule.count!!).flatMap { count ->
                    (0..schedule.perTime!! - 1).mapIndexed { s, i -> ScheduleItem(count, index, schedule, i == 0) }
                }
                Mode.TIME_MODE -> (0..schedule.time!!).map { ScheduleItem(it, index, schedule, true) }
                else -> emptyList()
            }
        }
    }

    override fun onProgressUpdate(vararg values: ScheduleItem?) {
        val scheduleItem = values[0]!!
        when (scheduleItem.schedule.type) {
            ScheduleType.FIGHTING -> roundTikTokFunc(scheduleItem)
            ScheduleType.REST -> restTikTokFunc(scheduleItem)
            ScheduleType.GET_READY -> getReadyTiktokFunc(scheduleItem)
        }
    }

    fun controlProgress(pause: Boolean) {
        mPause = pause
    }
}