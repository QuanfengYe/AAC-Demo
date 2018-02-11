package me.yeqf.android.api.retrofit

import io.reactivex.Single
import me.yeqf.android.bean.Category
import me.yeqf.android.bean.Ganhuo
import me.yeqf.android.bean.GankIo
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Administrator on 2018\2\11 0011.
 */
interface GankIoService {
    /**
     * http://gank.io/api/day/history 方式 GET
     */
    @GET("day/history")
    fun getPostedDateList(): Single<GankIo<List<String>>>

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     */
    @GET("day/{year}/{month}/{day}")
    fun getDailyData(@Path("year") year: Int,
                     @Path("month") month: Int,
                     @Path("day") day: Int) : Single<GankIo<Category>>

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     */
    @GET("data/{category}/{count}/{page}")
    fun getCategoryData(@Path("category") category: String,
                        @Path("count") count: Int,
                        @Path("page") page: Int): Single<GankIo<List<Ganhuo>>>

    /**
     * 随机数据：http://gank.io/api/random/data/分类/个数
     */
    @GET("random/data/{category}/{count}")
    fun getRandomData(@Path("category") category: String,
                        @Path("count") count: Int): Single<GankIo<List<Ganhuo>>>

    /**
     * 搜索 API
     * http://gank.io/api/search/query/listview/category/Android/count/10/page/1
     * 注：
     * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * count 最大 50
     */
    @GET("search/query/listview/category/{category}/count/{count}/page/{page}")
    fun getSearchResults(@Path("category") category: String,
                         @Path("count") count: Int,
                         @Path("page") page: Int): Single<GankIo<List<Ganhuo>>>
}