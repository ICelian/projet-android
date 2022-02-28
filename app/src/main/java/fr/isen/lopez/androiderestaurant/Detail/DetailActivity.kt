package fr.isen.lopez.androiderestaurant.Detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import fr.isen.lopez.androiderestaurant.BaseActivity
import fr.isen.lopez.androiderestaurant.Basket.Basket
import fr.isen.lopez.androiderestaurant.CategoryActivity
import fr.isen.lopez.androiderestaurant.R
import fr.isen.lopez.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.lopez.androiderestaurant.network.Dish
import kotlin.math.max

class DetailActivity : BaseActivity() {
    private lateinit var  binding: ActivityDetailBinding
    private var currentDish: Dish? = null
    private var itemCount = 1f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentDish = intent.getSerializableExtra(CategoryActivity.ItemSelected) as? Dish
        setupContent()
        observeClick()
        refreshShopButton()

    }
    private fun setupContent(){
        binding.title.text = currentDish?.name
        binding.ingredients.text = currentDish?.ingredients?.map { it.name }?.joinToString ( "," )
        currentDish?.let {
            binding.viewPager.adapter = PhotoAdapter(this,it.images)
        }
    }

    private fun refreshShopButton(){
        currentDish?.let { dish ->
            val price: Float = dish.prices.first().price.toFloat()
            val total = price * itemCount
            binding.buttonShop.text = "${getString(R.string.total)} $total €"
            binding.quantity.text = itemCount.toInt().toString()
        }
    }
    private fun observeClick(){
        binding.buttonLess.setOnClickListener{
            itemCount = max(1f,itemCount-1)
            refreshShopButton()
        }
        binding.buttonMore.setOnClickListener{
            itemCount++
            refreshShopButton()
        }
        binding.buttonShop.setOnClickListener{
            //ajouter a notre panier
            currentDish?.let {dish ->
                val basket = Basket.getBasket(this)
                basket.addItem(dish,itemCount.toInt())
                basket.save(this)

                Snackbar.make(binding.root,R.string.itemAdded,Snackbar.LENGTH_LONG).show()
                invalidateOptionsMenu()

            }


        }

    }

}
