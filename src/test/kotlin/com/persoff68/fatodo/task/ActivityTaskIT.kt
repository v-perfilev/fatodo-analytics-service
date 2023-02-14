package com.persoff68.fatodo.task

import com.persoff68.fatodo.model.Activity
import com.persoff68.fatodo.model.constant.DeviceType
import com.persoff68.fatodo.repository.ActivityRepository
import com.persoff68.fatodo.service.util.InstantUtils
import com.persoff68.fatodo.web.rest.ActivityControllerIT
import io.mockk.every
import io.mockk.mockkObject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant
import java.time.temporal.ChronoUnit

@SpringBootTest
@AutoConfigureMockMvc
class ActivityTaskIT(
    @Autowired val activityTask: ActivityTask,
    @Autowired val activityRepository: ActivityRepository
) {
    @BeforeEach
    fun setup() {
        activityRepository.deleteAll()

        val activity = Activity(DeviceType.ANDROID, ActivityControllerIT.DEVICE_ID)
        activityRepository.save(activity)
    }

    @Test
    fun testDeleteEmptyActivities() {
        mockkObject(InstantUtils) {
            every { InstantUtils.getPastInstant(any()) } returns Instant.now().plus(1, ChronoUnit.DAYS)
            activityTask.deleteEmptyActivities()
        }

        val activityList = activityRepository.findAll()
        assertThat(activityList).isEmpty()
    }
}
