package com.persoff68.fatodo.model.vm

import com.persoff68.fatodo.model.constant.DeviceType
import jakarta.validation.constraints.NotNull

class ActivityVM(
    @NotNull var deviceType: DeviceType,
    @NotNull var deviceId: String
) {
    constructor() : this(DeviceType.WEB, "")
}
