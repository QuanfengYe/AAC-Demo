package me.yeqf.common.api.factory
import android.annotation.SuppressLint
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


/**
 * Created by Administrator on 2018\2\11 0011.
 */
object OkHttpConfig {

    private val TIME_OUT = 30L

    val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.i("okHttp", it)
            }).setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .sslSocketFactory(createSSLSocketFactory()!!, TrustAllManager())
            .hostnameVerifier(TrustAllHostnameVerifier())
            .build()

    /**
     * 默认信任所有的证书
     * TODO 最好加上证书认证，主流App都有自己的证书
     *
     * @return
     */
    @SuppressLint("TrulyRandom")
    private fun createSSLSocketFactory(): SSLSocketFactory? {
        var sSLSocketFactory: SSLSocketFactory? = null
        try {
            val sc: SSLContext = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(TrustAllManager()),
                    SecureRandom())
            sSLSocketFactory = sc.socketFactory
        } catch (e: Exception) {
        }
        return sSLSocketFactory
    }

    private class TrustAllManager : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

        }

        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

        }

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return emptyArray()
        }

    }

    private class TrustAllHostnameVerifier : HostnameVerifier {
        override fun verify(hostname: String?, session: SSLSession?): Boolean {
            return true
        }
    }
}