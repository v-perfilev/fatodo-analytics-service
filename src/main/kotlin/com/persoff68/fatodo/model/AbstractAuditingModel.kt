package com.persoff68.fatodo.model

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.Date
import java.util.UUID

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractAuditingModel : AbstractModel(), Persistable<UUID> {
    @CreatedBy
    protected var createdBy: UUID? = null

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected var createdAt: Date? = null

    @LastModifiedBy
    protected var lastModifiedBy: UUID? = null

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected var lastModifiedAt: Date? = null

    override fun isNew(): Boolean {
        return createdBy == null && createdAt == null
    }
}
