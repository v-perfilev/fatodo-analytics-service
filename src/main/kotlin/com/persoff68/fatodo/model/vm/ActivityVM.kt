package com.persoff68.fatodo.model.vm

import com.persoff68.fatodo.model.constant.DeviceType
import jakarta.validation.constraints.NotNull

class ActivityVM(
    @field:NotNull var deviceType: DeviceType? = null,
    @field:NotNull var deviceId: String? = null
)
