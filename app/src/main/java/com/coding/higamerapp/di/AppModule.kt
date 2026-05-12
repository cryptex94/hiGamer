package com.coding.higamerapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.coding.higamerapp.common.util.Constants
import com.coding.higamerapp.feature_chat_list.data.ChatRoomDatabase
import com.coding.higamerapp.feature_chat_list.data.repository.ChatRoomRepositoryImpl
import com.coding.higamerapp.feature_chat_list.domain.model.repository.ChatRoomRepository
import com.coding.higamerapp.feature_gamers.data.data_source.hiGamerApi
import com.coding.higamerapp.feature_gamers.data.repository.GamerRepositoryImpl
import com.coding.higamerapp.feature_gamers.domain.repository.GamerRepository
import com.coding.higamerapp.feature_login.presentation.util.UserRepository
import com.coding.higamerapp.feature_login.presentation.util.UserRepository.token
import com.coding.higamerapp.feature_profile.data.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHiGamerApi(): hiGamerApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(OkHttpClient.Builder().also { client ->
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
                client.addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
                        .addHeader(
                            "Authorization",
                            "Bearer $token"
                        )
                        .build()
                    chain.proceed(newRequest)
                }
            }.connectTimeout(4,TimeUnit.SECONDS).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(hiGamerApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGamerRepository(api: hiGamerApi): GamerRepository {
        return GamerRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return UserRepository
    }

    @Provides
    @Singleton
    fun dataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }

    @Provides
    @Singleton
    fun provideChatRoomDatabase(app: Application): ChatRoomDatabase {
        return Room.databaseBuilder(
            app,
            ChatRoomDatabase::class.java,
            ChatRoomDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideChatRoomRepository(db: ChatRoomDatabase): ChatRoomRepository {
        return ChatRoomRepositoryImpl(db.chatRoomDao)
    }
}
