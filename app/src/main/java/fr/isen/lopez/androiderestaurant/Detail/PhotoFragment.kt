package fr.isen.lopez.androiderestaurant.Detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.squareup.picasso.Picasso
import fr.isen.lopez.androiderestaurant.R
import fr.isen.lopez.androiderestaurant.databinding.FragmentPhotoBinding
import java.net.URL

class PhotoFragment : Fragment() {

    private lateinit var binding: FragmentPhotoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val url = arguments?.getString(URL)
        if (url?.isNotEmpty() == true){
            Picasso.get().load(url).into(binding.photoFragment)
            //faire aussi le place older
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPhotoBinding.inflate(inflater,container,false)
        return binding.root
    }
    companion object{
        fun newInstance(url: String) : PhotoFragment{
            return PhotoFragment().apply { arguments = Bundle().apply { putString(
            URL,url) } } }
        const val URL ="URL"

    }


}