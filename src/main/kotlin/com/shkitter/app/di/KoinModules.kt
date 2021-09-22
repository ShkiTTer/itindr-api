package com.shkitter.app.di

import com.shkitter.data.db.Database
import com.shkitter.data.db.auth.AuthDataSourceImpl
import com.shkitter.data.db.user.UserDataSourceImpl
import com.shkitter.domain.auth.AuthDataSource
import com.shkitter.domain.auth.AuthService
import com.shkitter.domain.auth.AuthServiceImpl
import com.shkitter.domain.common.jwt.Jwt
import com.shkitter.domain.common.utils.SystemEnvVariablesUtil
import com.shkitter.domain.user.UserDataSource
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

object KoinModules {
    private val dataBaseModule = module {
        single {
            Database.getHikariConfig(
                jdbcUrl = SystemEnvVariablesUtil.jdbcUrl,
                dbUser = SystemEnvVariablesUtil.dbUser,
                dbPassword = SystemEnvVariablesUtil.dbPassword
            )
        }
        single { Database.getHikariDataSource(config = get()) }
        single { Database.getDatabase(dataSource = get()) }
    }

    private val dataSourceModule = module {
        factory<UserDataSource> { UserDataSourceImpl(db = get()) }
        factory<AuthDataSource> { AuthDataSourceImpl(db = get()) }
    }

    private val serviceModule = module {
        factory<AuthService> { AuthServiceImpl(authDataSource = get(), userDataSource = get(), jwt = get()) }
    }

    private val jwtModule = module {
        single { (secret: String, issuer: String, accessTokenValiditySeconds: Long, refreshTokenValiditySeconds: Long) ->
            Jwt(
                secret = secret,
                accessTokenValiditySeconds = accessTokenValiditySeconds,
                refreshTokenValiditySeconds = refreshTokenValiditySeconds,
                issuer = issuer
            )
        }
    }

    val all = module {
        loadKoinModules(
            listOf(
                dataBaseModule,
                dataSourceModule,
                serviceModule,
                jwtModule
            )
        )
    }
}