package com.meetfootball.adapter.user.`in`.web

import com.meetfootball.application.base.Error
import com.meetfootball.application.base.Result
import com.meetfootball.application.user.domain.entity.UserEntity
import com.meetfootball.application.user.port.`in`.FindUserByIdUseCase
import com.meetfootball.config.SecurityConfig
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(FindUserByIdController::class)
@Import(SecurityConfig::class)
class FindUserByIdControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var findUserByIdUseCase: FindUserByIdUseCase

    @Test
    fun `유저 조회 성공 시 200과 유저 정보를 반환한다`() {
        val user = UserEntity(name = "홍길동", email = "hong@test.com", password = "password")
        given(findUserByIdUseCase.findUserById(1L)).willReturn(Result.Success(user))

        mockMvc.get("/api/users/by-id/1")
            .andExpect {
                status { isOk() }
                jsonPath("$.name") { value("홍길동") }
                jsonPath("$.email") { value("hong@test.com") }
            }
    }

    @Test
    fun `존재하지 않는 유저 조회 시 404를 반환한다`() {
        given(findUserByIdUseCase.findUserById(99L)).willReturn(Result.Fail(Error.NotFound))

        mockMvc.get("/api/users/by-id/99")
            .andExpect {
                status { isNotFound() }
            }
    }
}
