package me.nelsoncastro.pokeapi.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_view_pokemon.view.*
import me.nelsoncastro.pokeapi.Pojos.Pokemon
import me.nelsoncastro.pokeapi.R
import me.nelsoncastro.pokeapi.pokeAdapter

class PokemonSimpleListAdapter(var pokemons: List<Pokemon>, val clickListener: (Pokemon) -> Unit): RecyclerView.Adapter<PokemonSimpleListAdapter.ViewHolder>(), pokeAdapter {
    override fun changeDataSet(newDataSet: List<Pokemon>) {
        this.pokemons =newDataSet
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonSimpleListAdapter.ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = pokemons.size

    override fun onBindViewHolder(holder: PokemonSimpleListAdapter.ViewHolder, position: Int) = holder.bind(pokemons[position], clickListener)
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(pokemon: Pokemon, clickListener: (Pokemon) -> Unit) = with(itemView){

            tv_pokemon_id.text = pokemon.id.toString()
            tv_pokemon_nameUP.text = pokemon.name
            tv_pokemon_type.text = pokemon.fsttype
            tv_pokemon_name.text = pokemon.name
            Glide.with(itemView.context)
                .load(pokemon.sprite)
                .placeholder(R.drawable.ic_launcher_background)
                .into(pokemon_cv)
            this.setOnClickListener { clickListener(pokemon) }
        }
    }

}