package chugh.puneet.com.bitkoin.model.data.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

object model{
    data class DataList(val status : Status, val data: List<Datum>)
    data class Datum(val id : Int, val name : String, val symbol : String, val slug : String, val circulating_supply: Double,
                     val total_supply: Double, val max_supply: Double?, val date_added: String, val num_market_pairs: Int,
                     val tags: List<String>, val platform: Any?, val cmc_rank: Int, val last_updated: String, val quote: Quote)
    data class USD(val price : Double, val volume_24h: Double, val percent_change_1h: Double, val percent_change_24h: Double,
                        val percent_change_7d: Double, val market_cap: Double, val last_updated: String)
    data class Status(val timestamp: String, val error_code: Int, val error_message: Any?, val elapsed: Int, val credit_count: Int)
    data class Quote(
            @SerializedName("USD")
            @Expose
            val usd: USD)
    data class Platform(val id: Int, val name: String, val symbol: String, val slug: String, val token_address : String)
}