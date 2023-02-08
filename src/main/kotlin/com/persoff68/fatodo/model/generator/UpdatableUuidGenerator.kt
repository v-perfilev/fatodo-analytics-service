@file:Suppress("DEPRECATION")

package com.persoff68.fatodo.model.generator

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.UUIDGenerator
import java.io.Serializable

class UpdatableUuidGenerator : UUIDGenerator() {

    override fun generate(session: SharedSessionContractImplementor, value: Any): Serializable {
        val id = session.getEntityPersister(null, value)
            .classMetadata.getIdentifier(value, session)
        return (id ?: super.generate(session, value)) as Serializable
    }

}
