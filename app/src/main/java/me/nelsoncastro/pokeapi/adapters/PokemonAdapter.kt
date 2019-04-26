package me.nelsoncastro.pokeapi.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_element_pokemon.view.*
import me.nelsoncastro.pokeapi.R
import me.nelsoncastro.pokeapi.Pojos.Pokemon
import me.nelsoncastro.pokeapi.pokeAdapter

class PokemonAdapter(var items: List<Pokemon>, val clickListener: (Pokemon) -> Unit):RecyclerView.Adapter<PokemonAdapter.ViewHolder>(), pokeAdapter {
    override fun changeDataSet(newDataSet: List<Pokemon>) {
        this.items = newDataSet
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_element_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], clickListener)


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: Pokemon, clickListener: (Pokemon) -> Unit) = with(itemView) {
            tv_pokemon_id.text = item.id.toString()
            tv_pokemon_name.text = item.name
            tv_pokemon_type.text = item.url
            Glide.with(itemView.context)
                .load(item.sprite)
                .placeholder(R.drawable.ic_launcher_background)
                .into(tv_pokemon_img)
            this.setOnClickListener { clickListener(item) }
        }
    }
}