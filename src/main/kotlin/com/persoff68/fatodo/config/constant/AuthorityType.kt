package com.persoff68.fatodo.config.constant

enum class AuthorityType(private val value: String) {

    SYSTEM("ROLE_SYSTEM"),
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    fun getValue(): String {
        return value
    }

    companion object {

        fun contains(value: String): Boolean {
            return values().asList().stream().anyMatch { authorityType -> authorityType.value == value }
        }

    }
}
