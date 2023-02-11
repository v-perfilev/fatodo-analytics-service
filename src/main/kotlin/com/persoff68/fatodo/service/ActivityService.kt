package com.persoff68.fatodo.service

import com.persoff68.fatodo.model.Activity
import com.persoff68.fatodo.model.constant.DeviceType
import com.persoff68.fatodo.repository.ActivityRepository
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

@Service
class ActivityService(private val activityRepository: ActivityRepository) {
    companion object {
        private const val THRESHOLD_SECONDS = 60L
    }

    fun writeActivity(userId: UUID, deviceType: DeviceType, deviceId: String) {
        val threshold = Instant.now().minus(THRESHOLD_SECONDS, ChronoUnit.SECONDS)
        val activity = activityRepository.findCurrent(userId, deviceId, threshold) ?: Activity(deviceType, deviceId)
        activityRepository.save(activity)
    }

    fun deleteEmptyActivities() {
        val threshold = Instant.now().minus(THRESHOLD_SECONDS, ChronoUnit.SECONDS)
        activityRepository.deleteNotModified(threshold)
    }
}
