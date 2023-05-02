package com.dsm.exception

/**
 *
 * 에러를 구분하는 ErrorCode
 *
 * @author Chokyunghyeon
 * @date 2023/04/17
 **/
interface ErrorCode {

    val defaultMessage: String
    val sequence: Int
    val header: String

    fun serial() : String = "$header-${formatCode.format(sequence)}"

    companion object {
        const val formatCode: String = "%03d"
    }
}