import kotlinx.coroutines.coroutineScope
import models.ValCurs
import retrofit2.Response
import java.text.SimpleDateFormat
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import kotlin.system.exitProcess

suspend fun main(args: Array<String>) {
    //Example input USD 2022-10-08
    val (code, date) = readln().split(' ')
    val pattern = SimpleDateFormat("yyyy-MM-dd").parse(date)
    val formatter = SimpleDateFormat("dd/MM/yyyy").format(pattern)
    getCurrencyRates(code, formatter)
}


suspend fun getCurrencyRates(code: String, date: String) = coroutineScope {
    try {
        val response: Response<ValCurs> = retrofit().getCurrencyRates(date)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                if (!getValuteValue(code, body)){
                    println("Error")
                }
                exitProcess(130)
            } else {
                println("Body is null")
            }
        } else {
            println(response.code())
        }
    } catch (e: Exception) {
        println(e.toString())
    }

}

fun getValuteValue(code: String, valCurs: ValCurs):Boolean {
    if (valCurs.valute != null) {
        valCurs.valute!!.forEach { valute ->
            if (valute.charCode == code) {
                if (valute.value != null) {
                    println(" ${valute.charCode} - ${valute.value}")
                    return true
                }
            }
        }
    } else {
        println("Not Found")
        return true
    }
    return false
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
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    return retrofit.create(Api::class.java)
}