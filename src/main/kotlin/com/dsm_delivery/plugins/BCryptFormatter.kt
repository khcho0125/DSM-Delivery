package com.dsm_delivery.plugins

import org.mindrot.jbcrypt.BCrypt

/**
 *
 * Bcrypt 암호를 담당하는 BCryptFormatter
 *
 * @author Chokyunghyeon
 * @date 2023/03/23
 **/
object BCryptFormatter {

    fun checkPassword(attempt: String, password: String): Boolean = BCrypt.checkpw(attempt, password)

    fun encodePassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())
}