import kotlinx.coroutines.coroutineScope
import models.ValCurs
import retrofit2.Response
import java.text.SimpleDateFormat
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jaxb.JaxbConverterFactory

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

fun retrofit(): Api {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cbr.ru")
        .client(okHttpClient)
        .addConverterFactory(JaxbConverterFactory.create())
        .build()

    return retrofit.create(Api::class.java)
}