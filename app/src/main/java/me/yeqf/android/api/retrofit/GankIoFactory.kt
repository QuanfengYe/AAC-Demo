package me.yeqf.android.api.retrofit

import me.yeqf.common.api.factory.RetrofitFactory

/**
 * Created by yeqf on 2018/2/11.
 */
class GankIoFactory : RetrofitFactory<GankIoService>() {

    override val baseUrl = "https://gank.io/api/"

    override fun getClassType(): Class<GankIoService> =
            GankIoService::class.java

    companion object {
        private var mFactory: GankIoFactory ?= null

        fun getService(): GankIoService {
            if (mFactory == null) {
                mFactory = GankIoFactory()
            }
            return mFactory!!.makeService()
        }
    }
}