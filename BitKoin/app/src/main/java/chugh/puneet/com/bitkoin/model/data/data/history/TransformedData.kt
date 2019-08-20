package chugh.puneet.com.bitkoin.model.data.data.history

import chugh.puneet.com.bitkoin.model.data.data.model

object transformedModel{
    data class NewData(val datum: model.Datum, var isActive : Boolean = false)
}