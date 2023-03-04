package com.example.smartcarparkadmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.smartcarparkadmin.data.Admin
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.databinding.ActivityMainBinding
import com.example.smartcarparkadmin.databinding.HeaderLoginBinding
import com.example.smartcarparkadmin.util.setImageBlob
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val nav by lazy { supportFragmentManager.findFragmentById(R.id.host)!!.findNavController() }
    private val auth: AuthViewModel by viewModels()

    // AppBarConfiguration
    private lateinit var abc: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // AppBarConfiguration
        abc = AppBarConfiguration(
            setOf(
                // Top-level pages
                R.id.homeFragment,
                R.id.loginFragment,
                R.id.compoundFragment,
                R.id.notificationFragment,
                R.id.parkingLotFragment,
                R.id.compoundInsert

            ),
            binding.drawerLayout
        )

        // Action bar
        setupActionBarWithNavController(nav, abc)
        binding.navView.setupWithNavController(nav)

        // -----------------------------------------------------------------------------------------

        // TODO(5): Observe login status -> userLiveData
        auth.getUserLiveData().observe(this) { manager ->
            // TODO(5A): Clear menu + remove header
            binding.navView.menu.clear()
            val h = binding.navView.getHeaderView(0)
            binding.navView.removeHeaderView(h)

            // TODO(5B): Inflate menu + header (based on login status)
            if (manager == null){
                binding.navView.inflateMenu(R.menu.drawer)
                binding.navView.inflateHeaderView(R.layout.header)
                nav.popBackStack(R.id.homeFragment, false)
                nav.navigateUp()
            }else {
                binding.navView.inflateMenu(R.menu.drawer_login)
                binding.navView.inflateHeaderView((R.layout.header_login))
                setHeader(manager)
            }

            // TODO(5C): Handle logout menu item
            binding.navView.menu.findItem(R.id.logout)?.setOnMenuItemClickListener { logout() }

        }

        // TODO(8): Auto login -> auth.loginFromPreferences(...)
        lifecycleScope.launch{
            auth.loginFromPreferences(this@MainActivity)
        }
    }

    private fun setHeader(admin: Admin) {
        val h = binding.navView.getHeaderView(0)
        val b = HeaderLoginBinding.bind(h)

        b.imgPhoto.setImageBlob(admin.photo)
        b.txtName.text  = admin.name
        b.txtEmail.text = admin.adminEmail
    }

    private fun logout(): Boolean {
        // TODO(4): Logout -> auth.logout(...)
        //          Clear navigation backstack
        auth.logout(this)
        nav.popBackStack(R.id.compoundFragment, false)

        binding.drawerLayout.close()
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp(abc) || super.onSupportNavigateUp()
    }
}