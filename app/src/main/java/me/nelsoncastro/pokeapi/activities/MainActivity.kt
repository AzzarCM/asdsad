package me.nelsoncastro.pokeapi.activities

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import me.nelsoncastro.pokeapi.adapters.PokemonAdapter
import me.nelsoncastro.pokeapi.R
import me.nelsoncastro.pokeapi.Pojos.Pokemon
import me.nelsoncastro.pokeapi.fragments.MainContentFragment
import me.nelsoncastro.pokeapi.fragments.MainListFragment
import me.nelsoncastro.pokeapi.utilities.NetworkUtils
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: PokemonAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var mainFragment : MainListFragment
    private lateinit var mainContentFragment: MainContentFragment

    private var pokemonList = ArrayList<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FetchPokemonTask().execute("")
        searchPokemon()
        clearSearchPokemon()
    }

    fun initRecycler(pokemon: MutableList<Pokemon>){
        viewManager = LinearLayoutManager(this)
        viewAdapter =
            PokemonAdapter(
                pokemon,
                { pokemonItem: Pokemon -> pokemonItemClicked(pokemonItem) })

        rv_pokemon_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private fun searchPokemon(){
        searchbarbutton.setOnClickListener {
            if (!searchbar.text.isEmpty()){
                QueryPokemonTask().execute("${searchbar.text}")
            }
        }
    }

    private fun clearSearchPokemon(){
        searchbarclearbutton.setOnClickListener {
            searchbar.setText("")
            FetchPokemonTask().execute("")
        }
    }

    private fun pokemonItemClicked(item: Pokemon){
        startActivity(Intent(this, PokemonViewer::class.java).putExtra("CLAVIER", item.url))
    }

    private inner class FetchPokemonTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg query: String): String {

            if (query.isNullOrEmpty()) return ""

            val ID = query[0]
            val pokeAPI = NetworkUtils().buildUrl("pokemon",ID)

            return try {
                NetworkUtils().getResponseFromHttpUrl(pokeAPI)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }

        }

        override fun onPostExecute(pokemonInfo: String) {
            val pokemon = if (!pokemonInfo.isEmpty()) {
                val root = JSONObject(pokemonInfo)
                val results = root.getJSONArray("results")
                MutableList(20) { i ->
                    val result = JSONObject(results[i].toString())
                    Pokemon(i,
                        result.getString("name").capitalize(),
                        R.string.n_a_value.toString(),
                        R.string.n_a_value.toString(),
                        R.string.n_a_value.toString(),
                        R.string.n_a_value.toString(),
                        result.getString("url"),
                        R.string.n_a_value.toString())
                }
            } else {
                MutableList(20) { i ->
                    Pokemon(i, R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(),
                        R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString())
                }
            }
            initRecycler(pokemon)
        }
    }

    private inner class QueryPokemonTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg query: String): String {

            if (query.isNullOrEmpty()) return ""

            val ID = query[0]
            val pokeAPI = NetworkUtils().buildUrl("type",ID)

            return try {
                NetworkUtils().getResponseFromHttpUrl(pokeAPI)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }

        }

        override fun onPostExecute(pokemonInfo: String) {
            val pokemon = if (!pokemonInfo.isEmpty()) {
                val root = JSONObject(pokemonInfo)
                val results = root.getJSONArray("pokemon")
                MutableList(20) { i ->
                    val resulty = JSONObject(results[i].toString())
                    val result = JSONObject(resulty.getString("pokemon"))

                    Pokemon(i,
                        result.getString("name").capitalize(),
                        R.string.n_a_value.toString(),
                        R.string.n_a_value.toString(),
                        R.string.n_a_value.toString(),
                        R.string.n_a_value.toString(),
                        result.getString("url"),
                        R.string.n_a_value.toString())
                }
            } else {
                MutableList(20) { i ->
                    Pokemon(i, R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(),
                        R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString())
                }
            }
            initRecycler(pokemon)
        }
    }
}
