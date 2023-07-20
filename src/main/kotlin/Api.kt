import models.ValCurs
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("./scripts/XML_daily.asp")
    suspend fun getCurrencyRates(@Query("date_req") date: String): Response<ValCurs>
}