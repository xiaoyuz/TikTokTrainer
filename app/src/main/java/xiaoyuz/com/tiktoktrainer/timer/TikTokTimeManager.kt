package xiaoyuz.com.tiktoktrainer.timer

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import xiaoyuz.com.tiktoktrainer.constants.ScheduleType
import xiaoyuz.com.tiktoktrainer.domain.TrainingSchedule
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer

class TikTokTimeManager(val mTrainingSchedules: List<TrainingSchedule>) {

    private val mTimer = Timer(true)

    fun work(roundTikTok: (Int) -> Unit, roundFinish: () -> Unit, restTikTok: (Int) -> Unit) {
        val timeList = mTrainingSchedules.flatMap { schedule ->
            (1..schedule.time!!).map { it to schedule}
        }
        var tikCount = 0
        timer("", true, Date(System.currentTimeMillis()), 1000, {
            if (tikCount > timeList.size) {
                cancel()
            }
            val (time, schedule) = timeList[tikCount]
            when (schedule.type) {
                ScheduleType.FIGHTING -> roundTikTok(time)
                ScheduleType.REST -> restTikTok(time)
            }
            tikCount += 1
            if (time == schedule.time!! && schedule.type == ScheduleType.FIGHTING) {
                roundFinish()
            }
        })

        //        chronometer.setOnChronometerTickListener {
//            val maxTime = (mSchedules[currentTurn].time!! + 1) * 1000
//            val currentTime = SystemClock.elapsedRealtime() - chronometer.base
//            timeBar.max = maxTime
//            timeBar.progress = currentTime.toInt()
//            if (currentTime > maxTime ){
//                it.base = SystemClock.elapsedRealtime()
//                currentTurn += 1
//                if (currentTurn == mSchedules.size) {
//                    it.stop()
//                }
//            }
//        }
//        chronometer.start()
    }
}