import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

suspend fun main(args: Array<String>) {
    val code = "USD"
    val date = "2022-10-08"
    val pattern = SimpleDateFormat("yyyy-MM-dd").parse(date)
    val formatter = SimpleDateFormat("dd/MM/yyyy").format(pattern)
    println(formatter)
    getCurrencyRates(code, formatter)
}


suspend fun getCurrencyRates(code: String, date: String) = coroutineScope {
    try {
        val response: Response<ValCurs> = retrofit().getCurrencyRates(date)
        if (response.isSuccessful) {
            val body = response.body()
            println(body.toString())
        } else {
            println(response.code())
        }
    } catch (e: Exception) {
        println(e.toString())
    }

}

@Root(strict = false, name = "ValCurs")
data class ValCurs(
    @field:Attribute(name = "Valute", required = false)
    val valute: List<Valute>,

    @field:Attribute(name = "Date", required = false)
    val date: String,

    @field:Attribute(name = "name", required = false)
    val name: String
)

data class Valute(
    val numCode: String,

    val charCode: String,

    val nominal: String,

    val name: String,

    val value: String,

    )

interface Api {
    @GET("./scripts/XML_daily.asp")
    suspend fun getCurrencyRates(@Query("date_req") date: String): Response<ValCurs>
}


fun retrofit(): Api {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cbr.ru")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(Api::class.java)
}