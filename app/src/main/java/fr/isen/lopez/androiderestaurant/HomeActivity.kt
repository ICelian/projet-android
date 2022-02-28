package fr.isen.lopez.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import fr.isen.lopez.androiderestaurant.databinding.ActivityHomeBinding


class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listenClick()
        Log.d("life cycle", "HomeActivity onCreate")
    }



    private fun listenClick () {
        binding.buttonStarter.setOnClickListener{
            //appeler quand on clique sur le bouton
            showCategory(LunchType.STARTER)
        }
        binding.buttonMain.setOnClickListener{
            showCategory(LunchType.MAIN)
        }
        binding.buttonFinish.setOnClickListener{
            showCategory(LunchType.FINISH)
        }
    }

    private fun showCategory(item: LunchType){
        val intent = Intent(this,CategoryActivity::class.java)
        intent.putExtra(HomeActivity.CategoryType, item)
        startActivity(intent)
    }
    override fun onDestroy() {
        Log.d("life cycle", "HomeActivity onDestroy")
        super.onDestroy()
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("life cycle", "HomeActivity onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("life cycle", "HomeActivity onResume")
    }

    override fun onStart() {
        super.onStart()
        Log.d("life cycle", "HomeActivity onStart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("life cycle", "HomeActivity onStop")
    }
    companion object {
        const val CategoryType = "CategoryType"
    }

}