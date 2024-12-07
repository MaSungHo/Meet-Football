package com.meetfootball.user.adapter.out.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>
