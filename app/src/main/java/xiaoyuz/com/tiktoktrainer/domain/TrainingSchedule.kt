package xiaoyuz.com.tiktoktrainer.domain

import xiaoyuz.com.tiktoktrainer.constants.Mode
import xiaoyuz.com.tiktoktrainer.constants.ScheduleType

data class TrainingSchedule(val mode: Mode, val type: ScheduleType, val time: Int? = null,
                            val count: Int? = null, val perTime: Int? = null) {
    constructor(): this(mode = Mode.TIME_MODE, type = ScheduleType.REST)
}