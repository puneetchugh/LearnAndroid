package chugh.puneet.com.bitkoin.model.data.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataList {

    @SerializedName("status")
    @Expose
    var status: Status? = null
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

}
