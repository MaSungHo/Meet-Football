package com.meetfootball.application.user.domain.service

import com.meetfootball.application.base.Result
import com.meetfootball.application.user.domain.entity.UserEntity
import com.meetfootball.application.user.port.out.LoadUserPort
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class FindUserByIdServiceTest {

    private val loadUserPort: LoadUserPort = mock(LoadUserPort::class.java)
    private val service = FindUserByIdService(loadUserPort)

    @Test
    fun `유저가 존재하면 Success를 반환한다`() {
        val user = UserEntity(name = "홍길동", email = "hong@test.com", password = "password")
        `when`(loadUserPort.findById(1L)).thenReturn(user)

        val result = service.findUserById(1L)

        assertTrue(result is Result.Success)
        assertEquals("홍길동", (result as Result.Success).data.name)
        assertEquals("hong@test.com", result.data.email)
    }

    @Test
    fun `유저가 존재하지 않으면 Fail을 반환한다`() {
        `when`(loadUserPort.findById(99L)).thenReturn(null)

        val result = service.findUserById(99L)

        assertTrue(result is Result.Fail)
        assertEquals(404, (result as Result.Fail).error.statusCode)
    }
}
