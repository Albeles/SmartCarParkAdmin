package com.example.smartcarparkadmin
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.smartcarparkadmin.ui.LoginFragment
import com.example.smartcarparkadmin.data.Admin
import com.example.smartcarparkadmin.data.AuthViewModel
import com.example.smartcarparkadmin.databinding.ActivityMainBinding
import com.example.smartcarparkadmin.databinding.HeaderLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.smartcarparkadmin.util.setImageBlob
import com.google.android.material.navigation.NavigationBarView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {


    private val nav by lazy { supportFragmentManager.findFragmentById(R.id.nav_host_container)!!.findNavController() }
    private lateinit var binding: ActivityMainBinding
    private val auth: AuthViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


   // private lateinit var abc: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // binding.navView.visibility= View.INVISIBLE
        binding.navView.visibility= View.VISIBLE

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        navController = navHostFragment.navController

        binding.navView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.compoundFragment, R.id.notificationFragment,R.id.profileFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
//        nav.navigate(R.id.compoundInsert)
//        nav.popBackStack(R.id.homeFragment, true)


        // AppBarConfiguration
       // abc = AppBarConfiguration(
      //      setOf(
    //            // Top-level pages
     //           R.id.homeFragment,
   //             R.id.loginFragment,
    //            R.id.compoundFragment,
  //              R.id.notificationFragment,
    //            R.id.parkingLotFragment,
   //             R.id.compoundInsert
///
     //       ),
   //         binding.drawerLayout
    //    )


        // Action bar

   //     setupActionBarWithNavController(nav, abc)
   //     binding.navView.setupWithNavController(nav)



        // -----------------------------------------------------------------------------------------

        // TODO(5): Observe login status -> userLiveData
        auth.getUserLiveData().observe(this) { manager ->
            // TODO(5A): Clear menu + remove header

            //     val h = binding.navView.getHeaderView(0)
            //     binding.navView.removeHeaderView(h)

            // TODO(5B): Inflate menu + header (based on login status)
            if (manager == null) {
                //  binding.navView.inflateMenu(R.menu.bottom_nav)
                //  binding.navView.inflateHeaderView(R.layout.header)
                //    nav.popBackStack(R.id.homeFragment, true)
                //
                //      nav.navigateUp()
            } else {

//                binding.navView.inflateMenu(R.menu.bottom_nav)
                // binding.navView.inflateMenu(R.menu.drawer_login)
                //  binding.navView.inflateHeaderView((R.layout.header_login))


                // setHeader(manager)
            }
        }

    }
            // TODO(5C): Handle logout menu item
         //   binding.navView.menu.findItem(R.id.logout)?.setOnMenuItemClickListener { logout() }



        // TODO(8): Auto login -> auth.loginFromPreferences(...)
     //   lifecycleScope.launch{
     //       auth.loginFromPreferences(this@MainActivity)
    //    }
  //  }

    //private fun setHeader(admin: Admin) {
     //   val h = binding.navView.getHeaderView(0)
    //    val b = HeaderLoginBinding.bind(h)

   //     b.imgPhoto.setImageBlob(admin.photo)
    //    b.txtName.text  = admin.name
    //    b.txtEmail.text = admin.adminEmail


//    private fun logout(): Boolean {
//        // TODO(4): Logout -> auth.logout(...)
//        //          Clear navigation backstack
//        auth.logout(this)
//        nav.popBackStack(R.id.compoundFragment, false)
//
//        binding.drawerLayout.close()
//        return true
//    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}