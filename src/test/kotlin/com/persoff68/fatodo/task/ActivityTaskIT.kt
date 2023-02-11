package com.persoff68.fatodo.task

import com.persoff68.fatodo.annotation.WithCustomSecurityContext
import com.persoff68.fatodo.model.Activity
import com.persoff68.fatodo.model.constant.DeviceType
import com.persoff68.fatodo.repository.ActivityRepository
import com.persoff68.fatodo.web.rest.ActivityControllerIT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
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
    @WithCustomSecurityContext(id = ActivityControllerIT.USER_ID_1)
    fun testWriteActivity_ok_current() {
        activityTask.deleteEmptyActivities()
        val activityList = activityRepository.findAll()
        assertThat(activityList).isEmpty()
    }
}
