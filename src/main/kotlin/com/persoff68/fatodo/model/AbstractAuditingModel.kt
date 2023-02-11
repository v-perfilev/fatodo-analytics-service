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
import java.time.Instant
import java.util.UUID

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractAuditingModel(
    @CreatedBy var createdBy: UUID? = null,
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    var createdAt: Instant? = null,
    @LastModifiedBy
    var lastModifiedBy: UUID? = null,
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    var lastModifiedAt: Instant? = null
) : AbstractModel(), Persistable<UUID> {
    override fun getId(): UUID? = id
    override fun isNew(): Boolean = createdBy == null && createdAt == null
}
