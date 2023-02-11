package com.persoff68.fatodo.web.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.persoff68.fatodo.annotation.WithCustomSecurityContext
import com.persoff68.fatodo.model.Activity
import com.persoff68.fatodo.model.constant.DeviceType
import com.persoff68.fatodo.model.vm.ActivityVM
import com.persoff68.fatodo.repository.ActivityRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.put
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID

@SpringBootTest
@AutoConfigureMockMvc
class ActivityControllerIT(
    @Autowired val mvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
    @Autowired val activityRepository: ActivityRepository
) {
    companion object {
        const val USER_ID_1 = "2fe7c6cf-56ad-442d-a43e-432969f333e4"
        const val USER_ID_2 = "1c326c76-fc39-4315-b4e9-2071abe8faaa"
        const val DEVICE_ID = "test_device_id"
    }

    @BeforeEach
    fun setup() {
        activityRepository.deleteAll()

        var activity = Activity(DeviceType.ANDROID, DEVICE_ID)
        activity = activityRepository.save(activity)
        activity.createdBy = UUID.fromString(USER_ID_1)
        activity.createdAt = Instant.now().minus(10, ChronoUnit.SECONDS)
        activityRepository.save(activity)
    }

    @Test
    @WithCustomSecurityContext(id = USER_ID_1)
    fun testWriteActivity_ok_current() {
        val vm = ActivityVM(DeviceType.ANDROID, DEVICE_ID)
        mvc.put(ActivityController.ENDPOINT) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(vm)
        }.andExpect {
            status { isOk() }
        }

        val userActivityList = activityRepository.findAll().filter { it.createdBy == UUID.fromString(USER_ID_1) }
        assertThat(userActivityList).hasSize(1)
        val activity = userActivityList[0]
        assertThat(activity.createdAt).isNotEqualTo(activity.lastModifiedAt)
    }

    @Test
    @WithCustomSecurityContext(id = USER_ID_2)
    fun testWriteActivity_ok_new() {
        val vm = ActivityVM(DeviceType.ANDROID, DEVICE_ID)
        mvc.put(ActivityController.ENDPOINT) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(vm)
        }.andExpect {
            status { isOk() }
        }

        val userActivityList = activityRepository.findAll().filter { it.createdBy == UUID.fromString(USER_ID_2) }
        assertThat(userActivityList).hasSize(1)
        val activity = userActivityList[0]
        assertThat(activity.createdAt).isEqualTo(activity.lastModifiedAt)
    }

    @Test
    @WithAnonymousUser
    fun testWriteActivity_unauthorized() {
        val vm = ActivityVM(DeviceType.ANDROID, DEVICE_ID)
        mvc.put(ActivityController.ENDPOINT) {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(vm)
        }.andExpect {
            status { isUnauthorized() }
        }
    }
}
