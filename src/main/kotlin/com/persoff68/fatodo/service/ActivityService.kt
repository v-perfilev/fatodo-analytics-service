package com.persoff68.fatodo.service

import com.persoff68.fatodo.model.Activity
import com.persoff68.fatodo.model.constant.DeviceType
import com.persoff68.fatodo.repository.ActivityRepository
import com.persoff68.fatodo.service.util.InstantUtils
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.UUID

@Service
@Transactional
class ActivityService(private val activityRepository: ActivityRepository) {
    companion object {
        private const val THRESHOLD_SECONDS = 60L
    }

    fun writeActivity(userId: UUID, deviceType: DeviceType, deviceId: String) {
        val threshold = InstantUtils.getPastInstant(THRESHOLD_SECONDS)
        val activity = activityRepository.findCurrent(userId, deviceId, threshold) ?: Activity(deviceType, deviceId)
        activity.counter++
        activityRepository.save(activity)
    }

    fun deleteEmptyActivities() {
        val threshold = InstantUtils.getPastInstant(THRESHOLD_SECONDS)
        activityRepository.deleteNotModified(threshold)
    }
}
