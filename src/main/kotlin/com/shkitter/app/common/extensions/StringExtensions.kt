package com.shkitter.app.common.extensions

import com.shkitter.domain.common.utils.SystemEnvVariablesUtil

fun String.createAvatarUrl(scheme: String) =
    "$scheme://${SystemEnvVariablesUtil.host}${com.shkitter.domain.common.utils.SystemEnvVariablesUtil.accessFileUrlPath}$this"