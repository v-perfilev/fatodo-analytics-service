package com.persoff68.fatodo.web.rest

import com.persoff68.fatodo.model.vm.ActivityVM
import com.persoff68.fatodo.security.exception.UnauthorizedException
import com.persoff68.fatodo.security.util.SecurityUtils
import com.persoff68.fatodo.service.ActivityService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(ActivityController.ENDPOINT)
class ActivityController(private val activityService: ActivityService) {
    companion object {
        const val ENDPOINT = "/api/activity"
    }

    @PutMapping
    fun writeActivity(@Valid @RequestBody vm: ActivityVM): ResponseEntity<Unit> {
        val userId = SecurityUtils.getCurrentId() ?: throw UnauthorizedException()
        activityService.writeActivity(userId, vm.deviceType!!, vm.deviceId!!)
        return ResponseEntity.ok().build()
    }
}
