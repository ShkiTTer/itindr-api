package com.shkitter.app.di

import com.shkitter.data.db.Database
import com.shkitter.data.db.chat.ChatDataSourceImpl
import com.shkitter.data.db.message.MessageDataSourceImpl
import com.shkitter.data.db.profile.ProfileDataSourceImpl
import com.shkitter.data.db.token.TokenDataSourceImpl
import com.shkitter.data.db.topic.TopicDataSourceImpl
import com.shkitter.data.db.user.UserDataSourceImpl
import com.shkitter.data.files.FilesDataSourceImpl
import com.shkitter.domain.auth.AuthService
import com.shkitter.domain.auth.AuthServiceImpl
import com.shkitter.domain.chat.ChatDataSource
import com.shkitter.domain.chat.ChatService
import com.shkitter.domain.chat.ChatServiceImpl
import com.shkitter.domain.common.jwt.Jwt
import com.shkitter.domain.common.utils.SystemEnvVariablesUtil
import com.shkitter.domain.files.FilesDataSource
import com.shkitter.domain.message.MessageDataSource
import com.shkitter.domain.profile.ProfileDataSource
import com.shkitter.domain.profile.ProfileService
import com.shkitter.domain.profile.ProfileServiceImpl
import com.shkitter.domain.token.TokenDataSource
import com.shkitter.domain.topic.TopicDataSource
import com.shkitter.domain.topic.TopicService
import com.shkitter.domain.topic.TopicServiceImpl
import com.shkitter.domain.user.UserDataSource
import com.shkitter.domain.user.UserService
import com.shkitter.domain.user.UserServiceImpl
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
        factory<TokenDataSource> { TokenDataSourceImpl(db = get()) }
        factory<ProfileDataSource> { ProfileDataSourceImpl(db = get()) }
        factory<TopicDataSource> { TopicDataSourceImpl(db = get()) }
        factory<FilesDataSource> {
            FilesDataSourceImpl(
                storePath = SystemEnvVariablesUtil.filesStorePath
            )
        }
        factory<ChatDataSource> { ChatDataSourceImpl(db = get()) }
        factory<MessageDataSource> { MessageDataSourceImpl(db = get()) }
    }

    private val serviceModule = module {
        factory<AuthService> {
            AuthServiceImpl(
                tokenDataSource = get(),
                userDataSource = get(),
                profileDataSource = get(),
                jwt = get()
            )
        }
        factory<ProfileService> {
            ProfileServiceImpl(
                profileDataSource = get(),
                userDataSource = get(),
                topicDataSource = get(),
                filesDataSource = get()
            )
        }
        factory<TopicService> { TopicServiceImpl(topicDataSource = get()) }
        factory<UserService> { UserServiceImpl(userDataSource = get(), profileDataSource = get()) }
        factory<ChatService> {
            ChatServiceImpl(
                chatDataSource = get(),
                userDataSource = get(),
                messageDataSource = get()
            )
        }
    }

    private val jwtModule = module {
        single { (secret: String, issuer: String) ->
            Jwt(
                secret = secret,
                issuer = issuer,
                accessTokenValiditySeconds = SystemEnvVariablesUtil.accessTokenValidityInSeconds,
                refreshTokenValiditySeconds = SystemEnvVariablesUtil.refreshTokenValidityInSeconds
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