package digital.metro.companion.interviewapp.data.api

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient<T> {

    fun createApi(
        host: String,
        clazz: Class<T>
    ): T {
        val okHttpClientBuilder =
            OkHttpClient()
                .newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val requestBuilder = chain.request().newBuilder()
                        .addHeader("Accept", "application/vnd.github.v3+json")
                    chain.proceed(requestBuilder.build())
                }
                .addInterceptor(getLoggingInterceptor(clazz))

        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setPrettyPrinting().create()
                )
            )
            .client(okHttpClientBuilder.build())
            .baseUrl(host)
            .build()
            .create(clazz)
    }

    private fun getLoggingInterceptor(
        clazz: Class<T>
    ): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message ->
            Log.d(clazz.simpleName, message)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }
}
