package me.nelsoncastro.pokeapi.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.viewer_element_pokemon.*
import me.nelsoncastro.pokeapi.Pojos.Pokemon
import me.nelsoncastro.pokeapi.R

class PokemonViewer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewer_element_pokemon)

        setSupportActionBar(toolbarviewer)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setDisplayShowHomeEnabled(true)
        collapsingtoolbarviewer.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        collapsingtoolbarviewer.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)

        val reciever: Pokemon = intent?.extras?.getParcelable("POKEMON") ?: Pokemon()
        init(reciever)
    }

    fun init(pokemon: Pokemon){
        Glide.with(this)
            .load(pokemon.sprite)
            .placeholder(R.drawable.ic_launcher_background)
            .into(app_bar_image_viewer)
        collapsingtoolbarviewer.title = pokemon.name
        app_bar_rating_viewer.text = pokemon.id.toString()
        plot_viewer.text = pokemon.fsttype
        director_viewer.text = pokemon.sndtype
        actors_viewer.text = pokemon.height
        genre_viewer.text = pokemon.weight
        released_viewer.text = pokemon.sndtype
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {onBackPressed();true}
            else -> super.onOptionsItemSelected(item)
        }
    }

}