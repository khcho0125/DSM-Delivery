package com.dsm_delivery.domain.auth.usecase

import com.dsm_delivery.domain.auth.token.TokenProvider

/**
 *
 * 학생 로그인을 담당하는 StudentLogin
 *
 * @author Chokyunghyeon
 * @date 2023/03/20
 **/
class StudentLogin(
    private val tokenProvider: TokenProvider
) {
    operator fun invoke() {

    }
}