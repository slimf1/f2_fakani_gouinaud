package fr.isima.expressvoyage.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import fr.isima.expressvoyage.R
import fr.isima.expressvoyage.databinding.FragmentSettingsBinding
import fr.isima.expressvoyage.ui.home.HomeFragment

class SettingsFragment : Fragment() {

    private var removeAllFavoriteButton: MaterialButton? = null
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        removeAllFavoriteButton = root.findViewById(R.id.btnRemoveAllFavorite)
        removeAllFavoriteButton?.setOnClickListener {
            val sharedPref = requireActivity()
                .getPreferences(Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.remove(HomeFragment.FAVORITE_COUNTRY_KEY)
            editor.apply()
            val toast = Toast.makeText(
                requireContext(),
                getString(R.string.delete_favorites_toast_text),
                Toast.LENGTH_SHORT
            )
            toast.show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}