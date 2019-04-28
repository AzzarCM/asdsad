package me.nelsoncastro.pokeapi.activities

import android.app.Fragment
import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.pokemon_list_fragment.*
import me.nelsoncastro.pokeapi.AppConstants
import me.nelsoncastro.pokeapi.adapters.PokemonAdapter
import me.nelsoncastro.pokeapi.R
import me.nelsoncastro.pokeapi.Pojos.Pokemon
import me.nelsoncastro.pokeapi.fragments.MainContentFragment
import me.nelsoncastro.pokeapi.fragments.MainListFragment
import me.nelsoncastro.pokeapi.utilities.NetworkUtils
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity(), MainListFragment.SearchNewPokemonListener{

    private lateinit var mainFragment : MainListFragment
    private lateinit var mainContentFragment: MainContentFragment

    private var pokemonList = ArrayList<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pokemonList = savedInstanceState?.getParcelableArrayList(AppConstants.datase_savinstance_key) ?: ArrayList()
        FetchPokemonTask().execute("")

        initMainFragment()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.datase_savinstance_key, pokemonList)
        super.onSaveInstanceState(outState)
    }

    fun initMainFragment (){
        mainFragment = MainListFragment.newInstance(pokemonList)
        val resource = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            R.id.main_fragment
        else{
            mainContentFragment = MainContentFragment.newInstance(Pokemon())
            changeFragment(R.id.land_main_cont_fragment, mainContentFragment)
            R.id.land_main_fragment
        }

        changeFragment(resource, mainFragment)

    }
    fun addPokemonToList(movie: Pokemon) {
        pokemonList.add(movie)
        mainFragment.updatePokemonAdapter(pokemonList)
        Log.d("Number", pokemonList.size.toString())
    }


    override fun searchPokemonType(pokemonType: String){
        FetchPokemonTask().execute(pokemonType)
    }
    override fun managePortraitItemClick(pokemon: Pokemon) {
        val pokemonBundle = Bundle()
        pokemonBundle.putParcelable("POKEMON", pokemon)
        startActivity(Intent(this, PokemonViewer::class.java).putExtras(pokemonBundle))
    }

    private fun changeFragment(id: Int, frag : android.support.v4.app.Fragment){
        supportFragmentManager.beginTransaction().replace(id, frag).commit()
    }

    override fun manageLandScapeItemClick(pokemon: Pokemon) {
        mainContentFragment = MainContentFragment.newInstance(pokemon)
        changeFragment(R.id.land_main_cont_fragment, mainContentFragment)
    }

    /*
    private fun pokemonItemClicked(item: Pokemon){
        startActivity(Intent(this, PokemonViewer::class.java).putExtra("CLAVIER", item.url))
    }
*/
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
        /*

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
        }*/
        override fun onPostExecute(pokemonInfo: String) {
            super.onPostExecute(pokemonInfo)
            if (!pokemonInfo.isEmpty()) {
                val movieJson = JSONObject(pokemonInfo)
                if (movieJson.getString("Response") == "True") {
                    val pokemon = Gson().fromJson<Pokemon>(pokemonInfo, Pokemon::class.java)
                    addPokemonToList(pokemon)
                } else {
                    Toast.makeText(this@MainActivity, "No existe en la base de datos,", Toast.LENGTH_LONG).show()
                }
            }else
            {
                Toast.makeText(this@MainActivity, "A ocurrido un error,", Toast.LENGTH_LONG).show()
            }
        }
    }


}
