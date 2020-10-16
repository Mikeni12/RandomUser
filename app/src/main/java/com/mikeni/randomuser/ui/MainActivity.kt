package com.mikeni.randomuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.mikeni.randomuser.R
import com.mikeni.randomuser.databinding.ActivityMainBinding
import com.simform.custombottomnavigation.SSCustomBottomNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        private const val ID_SEARCH = 1
        private const val ID_RECENT = 2
    }

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_RandomUser)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.navHostFragmentContainer)

        setData()
        setListeners()
    }

    private fun setData() {
        binding.bottomNavigation.apply {
            add(SSCustomBottomNavigation.Model(ID_SEARCH, R.drawable.ic_home, getString(R.string.label_home)))
            add(SSCustomBottomNavigation.Model(ID_RECENT, R.drawable.ic_history, getString(R.string.label_recent)))
        }
    }

    private fun setListeners() {
        bottomNavigation.setOnClickMenuListener {
            navController.popBackStack()
            when (it.id) {
                ID_SEARCH -> navController.navigate(R.id.userFragment)
                ID_RECENT -> navController.navigate(R.id.historyFragment)
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.userFragment -> bottomNavigation.show(ID_SEARCH, true)
                R.id.historyFragment -> bottomNavigation.show(ID_RECENT, true)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}