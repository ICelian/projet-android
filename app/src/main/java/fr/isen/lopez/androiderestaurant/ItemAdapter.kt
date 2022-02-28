package fr.isen.lopez.androiderestaurant

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.lopez.androiderestaurant.databinding.CellMainBinding
import fr.isen.lopez.androiderestaurant.network.Dish

class ItemAdapter(val items: List<Dish>,val itemClickListener:(Dish)-> Unit): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
// string car pour listant item 1,item2 etc ce sont des string apres il faudra changer
    class ItemViewHolder(binding:CellMainBinding): RecyclerView.ViewHolder(binding.root){
        val titlePlat: TextView = binding.title
        val price: TextView = binding.price
        val image: ImageView = binding.imageView
        val layout: CardView = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //créer le view holder et attacher le layout a celui-ci
        val binding = CellMainBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        //appelé au moment de l'affichage de la cellule
        val item = items[position]
        viewHolder.titlePlat.text = item.name
        viewHolder.price.text = item.prices.first().price + "€"
        Picasso.get().load(item.getThumbnailURL()).into(viewHolder.image)
        //pour rajouter une image temporaire on peut faire entre load et into un placeholder
        //(R.drawable.nom_de_l'image)
        viewHolder.layout.setOnClickListener {
            itemClickListener.invoke(item)
        }



    }

    override fun getItemCount(): Int {
        //on ifore a la recycle view combien d'item nous avons dans la liste
        return items.count()
    }
}