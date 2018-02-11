package me.yeqf.android.repository

import me.yeqf.android.persistence.dao.UserDao
import me.yeqf.android.persistence.database.UserDatabase
import me.yeqf.android.persistence.entity.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.yeqf.android.api.retrofit.GankIoService
import me.yeqf.android.bean.Category
import me.yeqf.android.bean.GankIo
import me.yeqf.common.api.config.RetrofitFactory
import me.yeqf.common.utils.rxjava.RxSchedulers
import retrofit2.Retrofit

/**
 * Created by yeqf on 2018/2/10.
 */
object UserRepository {
    private fun getUserDao() : UserDao {
        return UserDatabase.getInstance().getUserDao()
    }

    fun getUserById(id: String): Flowable<User> =
            getUserDao().getUserById(id)

    fun addUser(user: User): Completable =
        Completable.fromAction( {
            getUserDao().insert(user)
        })

    fun getDaily(year: Int, month: Int, day: Int): Single<GankIo<Category>> {
        return RetrofitFactory.getService(GankIoService::class.java).getDailyData(year, month, day)
    }
}