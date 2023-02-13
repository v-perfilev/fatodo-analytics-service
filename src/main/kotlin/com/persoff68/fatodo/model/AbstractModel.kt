package com.persoff68.fatodo.model

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.util.UUID

@MappedSuperclass
abstract class AbstractModel(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JvmField
    var id: UUID? = null
)
