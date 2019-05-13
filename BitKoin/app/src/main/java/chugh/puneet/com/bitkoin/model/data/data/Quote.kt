package chugh.puneet.com.bitkoin.model.data.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Quote {

    @SerializedName("USD")
    @Expose
    var usd: USD? = null

}
