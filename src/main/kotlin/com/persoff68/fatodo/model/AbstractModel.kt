package com.persoff68.fatodo.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.GenericGenerator
import java.util.*

@MappedSuperclass
abstract class AbstractModel {

    @Id
    @GeneratedValue(generator = "custom-uuid")
    @GenericGenerator(name = "custom-uuid", strategy = "com.persoff68.fatodo.model.generator.UpdatableUuidGenerator")
    protected var id: UUID? = null

}
