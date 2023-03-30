package com.dsm.plugins

import org.mindrot.jbcrypt.BCrypt

/**
 *
 * 암호화를 담당하는 PasswordFormatter
 *
 * @author Chokyunghyeon
 * @date 2023/03/23
 **/
object PasswordFormatter {

    fun checkPassword(attempt: String, password: String): Boolean = BCrypt.checkpw(attempt, password)

    fun encodePassword(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())
}
