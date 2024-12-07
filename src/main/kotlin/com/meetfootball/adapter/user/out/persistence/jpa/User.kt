package com.meetfootball.adapter.user.out.persistence.jpa

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column
    val name: String,

    @Column
    val email: String,

    @Column
    val password: String,

    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = LocalDateTime.now(),

    @CreatedDate
    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now(),
)
