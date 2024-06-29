package com.meetfootball.user.adapter.out.persistence

import com.meetfootball.common.jpa.BaseJpaEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class JpaUser(
    @Column
    val name: String,

    @Column
    val email: String,

    @Column
    val password: String,
): BaseJpaEntity()
