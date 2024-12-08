package com.meetfootball.adapter.user.out.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>
