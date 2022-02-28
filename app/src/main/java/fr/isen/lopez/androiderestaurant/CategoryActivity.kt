package fr.isen.lopez.androiderestaurant
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.lopez.androiderestaurant.Detail.DetailActivity
import fr.isen.lopez.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.lopez.androiderestaurant.network.Dish
import fr.isen.lopez.androiderestaurant.network.MenuResult
import fr.isen.lopez.androiderestaurant.network.NetworkConstants
import org.json.JSONObject

enum class LunchType {
    STARTER, MAIN, FINISH;

    companion object {
        fun getResString(type: LunchType): Int{
            return when(type){
                STARTER -> R.string.starters
                MAIN -> R.string.main
                FINISH -> R.string.finish
            }
        }
        fun getCategoryTitle(type: LunchType): String{
            return when(type){
                STARTER -> "Entrées"
                MAIN -> "Plats"
                FINISH -> "Desserts"

            }

         }
    }
}

class CategoryActivity : BaseActivity() {
    lateinit var binding: ActivityCategoryBinding
    lateinit var currentCategory: LunchType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentCategory = intent.getSerializableExtra(HomeActivity.CategoryType) as? LunchType ?: LunchType.STARTER
        setupTilte()

        makeRequest()

    }



    private fun makeRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstants.BASE_URL+NetworkConstants.MENU
        val parameters = JSONObject()
        parameters.put(NetworkConstants.KEY_SHOP,NetworkConstants.SHOP)
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            parameters,
            {

                parseResult(it.toString())

            },
            {


            })
        queue.add(request)
    }
    private fun parseResult(response: String){
        val result = GsonBuilder().create().fromJson(response ,MenuResult::class.java)
        val items = result.data.firstOrNull{
            it.name == LunchType.getCategoryTitle(currentCategory)
        }?.items

        items?.let {
            setupList(it)
        }

    }



    private fun setupTilte(){

        binding.title.text = getString(LunchType.getResString(currentCategory))
    }

    private fun setupList(items: List<Dish>) {
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ItemAdapter(items){item ->
            val intent = Intent(this@CategoryActivity, DetailActivity::class.java)
            //permet de rediriger vers une autre page quand on clique
            intent.putExtra(CategoryActivity.ItemSelected, item)
            startActivity(intent)

        }
        binding.itemRecyclerView.adapter = adapter
    }

    companion object{
        const val ItemSelected = "ItemSelected"
    }
}
