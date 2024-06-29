package com.meetfootball.user.adapter.out.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface JpaUserRepository: JpaRepository<JpaUser, Long>
