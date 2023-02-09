package com.persoff68.fatodo.config.database

import com.persoff68.fatodo.security.util.SecurityUtils
import org.springframework.data.domain.AuditorAware
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.UUID

@Component
class SecurityAuditorAware : AuditorAware<UUID> {
    override fun getCurrentAuditor(): Optional<UUID> {
        val currentId = SecurityUtils.getCurrentId()
        return Optional.ofNullable(currentId)
    }
}
