package chugh.puneet.com.bitkoin.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import chugh.puneet.com.bitkoin.R
import chugh.puneet.com.bitkoin.constants.LOG_TAG
import chugh.puneet.com.bitkoin.model.data.data.history.transformedModel
import kotlinx.android.synthetic.main.card_view_item.view.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class BitkoinListAdapter(val context : Context,
                         var bitkoinList : MutableList<transformedModel.NewData>?) :
        RecyclerView.Adapter<BitkoinListAdapter.BitkoinViewHolder>(){

    class BitkoinViewHolder(view : View) : RecyclerView.ViewHolder(view){

        val bitkoinName = view.id_bitkoin_name
        val bitkoinSymbol = view.id_bitkoin_symbol
        val bitkoinPrice = view.id_bitkoin_price
        val dateAdded = view.id_added_on
        val marketCap = view.id_bitkoin_marketcap
        val vol24Hours = view.id_bitkoin_vol_24h
        val percentageChange =  view.id_percent_change
        val isActive = view.id_is_active
    }
    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): BitkoinViewHolder {
        return BitkoinViewHolder(
                LayoutInflater.from(context).inflate(
                        R.layout.card_view_item,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int {
        return this.bitkoinList?.size ?: 0
    }

    override fun onBindViewHolder(holder : BitkoinViewHolder, position: Int) {

        val datum = bitkoinList?.get(position)?.datum
        Log.d(LOG_TAG, "Inside onBindViewHolder()...datum : "+datum)
        if(datum != null) {
                Log.d(LOG_TAG, "datum is not null")
                holder.bitkoinName.text = datum.name
                holder.bitkoinSymbol.text = datum.symbol

                holder.dateAdded.text = String.format("Added on : %s",convertDateTime(datum.date_added))

                val usd = datum.quote.usd
                usd.let {
                        Log.d(LOG_TAG, "usd is not null")
                        holder.bitkoinPrice.text = String.format("Price : $%,f", it.price)
                        holder.marketCap.text = String.format("Market Cap : $%,f", it.market_cap)
                        holder.vol24Hours.text = String.format("Volume (24 Hours) : $%,f", it.volume_24h)
                        holder.percentageChange.text = String.format("1h:%.2f%% \t\t\t24h:%.2f%% \t\t\t7d:%.2f%%", it.percent_change_1h,
                            it.percent_change_24h, it.percent_change_7d)
                }
            val isActive = if (bitkoinList?.get(position)?.isActive != null && (bitkoinList?.get(position)?.isActive!!)) context.resources.getString(R.string.yes)
                                    else context.resources.getString(R.string.no)
            holder.isActive.text = String.format("%s %s", context.resources.getString(R.string.active), isActive)
        }
    }

    fun updateDataSet(list : MutableList<transformedModel.NewData>?){
        Log.d(LOG_TAG, "updateDataSet() called")

        if (bitkoinList != null){
            bitkoinList?.clear()
            list?.let {
                bitkoinList?.addAll(list.toList())
            }
        }
        else
            bitkoinList = list
        notifyDataSetChanged()
    }

    //convert DateTime from Instant object to better readable format
    private fun convertDateTime(dateTimeInstant : String) : String{

        val instant = Instant.parse(dateTimeInstant)
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.id)).toLocalDate()

        val dateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy")
        return localDateTime.format(dateTimeFormatter)
    }

}