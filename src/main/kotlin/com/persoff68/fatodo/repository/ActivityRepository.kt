package com.persoff68.fatodo.repository

import com.persoff68.fatodo.model.Activity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.UUID

@Repository
interface ActivityRepository : JpaRepository<Activity, UUID> {
    @Query(
        """
            select a from Activity a
            where a.createdBy = :userId and a.deviceId = :deviceId and a.lastModifiedAt > :threshold
        """
    )
    fun findCurrent(
        @Param("userId") userId: UUID,
        @Param("deviceId") deviceId: String,
        @Param("threshold") threshold: Instant
    ): Activity?

    @Query(
        """
            delete from Activity a
            where a.createdAt = a.lastModifiedAt and a.lastModifiedAt > :threshold
        """
    )
    @Modifying
    fun deleteNotModified(@Param("threshold") threshold: Instant)
}
