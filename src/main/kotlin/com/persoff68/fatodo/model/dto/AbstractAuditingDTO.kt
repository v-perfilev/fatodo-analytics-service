package com.persoff68.fatodo.model.dto

import java.time.Instant
import java.util.UUID

abstract class AbstractAuditingDTO : AbstractDTO() {
    var createdBy: UUID? = null
    var createdAt: Instant? = null
    var lastModifiedBy: UUID? = null
    var lastModifiedAt: Instant? = null
}
