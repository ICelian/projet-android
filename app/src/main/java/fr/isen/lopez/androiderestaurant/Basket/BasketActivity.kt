package fr.isen.lopez.androiderestaurant.Basket

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.lopez.androiderestaurant.R
import fr.isen.lopez.androiderestaurant.User.UserActivity
import fr.isen.lopez.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.lopez.androiderestaurant.network.NetworkConstants
import org.json.JSONObject

class BasketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBasketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadlist()

        binding.orderButton.setOnClickListener{
            val intent = Intent(this, UserActivity::class.java)
            getResult.launch(intent)


        }
    }
    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == Activity.RESULT_OK){
            makeRequest()

        }
    }



    private fun loadlist(){
        val basket = Basket.getBasket(this)
        val items= Basket.getBasket(this).items
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = BasketAdapter(items) {
            basket.removeItem(it)
            basket.save(this)
            loadlist()

        }
    }
    private fun makeRequest(){
        val path = NetworkConstants.BASE_URL + NetworkConstants.ORDER
        val queue = Volley.newRequestQueue(this)
        val basket = Basket.getBasket(this)
        val sharedPreferences = getSharedPreferences(UserActivity.USER_PREFERENCES_NAME,Context.MODE_PRIVATE)

        val parameters = JSONObject()
        parameters.put(NetworkConstants.KEY_MSG,basket.toJson())
        parameters.put(NetworkConstants.KEY_USER,sharedPreferences.getInt(UserActivity.ID_USER, -1))
        parameters.put(NetworkConstants.KEY_SHOP, NetworkConstants.SHOP)
        val request = JsonObjectRequest(
            Request.Method.POST,
            path,
            parameters,
            {
                basket.clear()
                basket.save(this)
                finish()


            },
            {

            }


        )
        queue.add(request)
    }


}