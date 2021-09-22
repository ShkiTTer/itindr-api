package com.shkitter.app.di

import com.shkitter.app.jwt.Jwt
import com.shkitter.domain.common.utils.SystemEnvVariablesUtil
import com.shkitter.data.db.Database
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

    }

    private val serviceModule = module {

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