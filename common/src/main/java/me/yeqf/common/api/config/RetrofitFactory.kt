package me.yeqf.common.api.config

import me.yeqf.common.base.Constant
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Administrator on 2018\2\11 0011.
 */
object RetrofitFactory {
    private val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(OkHttpConfig.client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> getService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}