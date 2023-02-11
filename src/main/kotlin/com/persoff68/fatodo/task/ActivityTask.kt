package com.persoff68.fatodo.task

import com.persoff68.fatodo.service.ActivityService
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class ActivityTask(private val activityService: ActivityService) {
    @Scheduled(fixedDelay = 10000, initialDelay = 10000)
    fun deleteEmptyActivities() {
        activityService.deleteEmptyActivities()
    }
}
