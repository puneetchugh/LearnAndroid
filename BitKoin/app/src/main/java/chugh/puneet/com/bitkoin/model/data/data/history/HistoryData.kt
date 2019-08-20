package chugh.puneet.com.bitkoin.model.data.data.history

object modelHistory{
    data class Status(val timestamp : String, val error_code : Int, val error_message : String, val elapsed : Int,
                      val credit_count : Int)
    data class Platform(val id : Int, val name : String, val symbol: String, val slug: String, val token_address : String)
    data class Datum(val id : Int, val name : String, val symbol : String, val slug : String,
                     val is_active : Int, val first_historical_data : String?, val last_historical_data : String?,
                     val platform : Platform?)
    data class DataList(val data : List<Datum>?, val status : Status)
}