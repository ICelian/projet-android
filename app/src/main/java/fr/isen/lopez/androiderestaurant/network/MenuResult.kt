package fr.isen.lopez.androiderestaurant.network

import com.google.gson.annotations.SerializedName

class MenuResult(@SerializedName("data") val data: List<Category>){
}