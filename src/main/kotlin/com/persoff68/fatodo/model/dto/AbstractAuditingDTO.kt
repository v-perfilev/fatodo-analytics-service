package com.persoff68.fatodo.model.dto

import java.util.*

abstract class AbstractAuditingDTO : AbstractDTO() {

    protected var createdBy: UUID? = null
    protected var createdAt: Date? = null
    protected var lastModifiedBy: UUID? = null
    protected var lastModifiedAt: Date? = null

}
