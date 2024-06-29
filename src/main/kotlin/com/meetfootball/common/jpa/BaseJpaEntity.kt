package com.meetfootball.common.jpa

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseJpaEntity(
    @LastModifiedDate
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = LocalDateTime.now(),

    @CreatedDate
    @Column(name = "created_at")
    var createdAt: LocalDateTime? = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) {

}