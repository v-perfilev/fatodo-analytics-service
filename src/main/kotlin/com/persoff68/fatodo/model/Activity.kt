package com.persoff68.fatodo.model

import com.persoff68.fatodo.model.constant.DeviceType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "ftd_analytics_activity")
class Activity(
    @Enumerated(EnumType.STRING)
    val deviceType: DeviceType = DeviceType.WEB,
    val deviceId: String = ""
) : AbstractAuditingModel()
