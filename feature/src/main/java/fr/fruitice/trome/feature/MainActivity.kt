package fr.fruitice.trome.feature

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import fr.fruitice.trome.feature.Adapters.LinesAdapter
import fr.fruitice.trome.feature.Objects.Line
import fr.fruitice.trome.feature.Objects.Lines
import kotlinx.android.synthetic.main.activity_main.*
import android.R.anim.slide_out_right
import android.R.anim.slide_in_left
import android.annotation.SuppressLint
import android.app.Fragment
import android.app.FragmentTransaction


class MainActivity : AppCompatActivity() {
    var linesFragment: LinesFragment? = null
    var itineraryFragment: ItineraryFragment? = null
    var favoritesFragment: FavoritesFragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_lines -> {
                showFragment(linesFragment as Fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_itinerary -> {
                showFragment(itineraryFragment as Fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorites -> {
                showFragment(favoritesFragment as Fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        linesFragment = LinesFragment()
        itineraryFragment = ItineraryFragment()
        favoritesFragment = FavoritesFragment()
        showFragment(linesFragment as Fragment)
    }

    private fun showFragment(fragment: Fragment) {
        val ft = fragmentManager.beginTransaction()
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        //ft.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right)
        ft.replace(R.id.content, fragment)

        // Start the animated transition.
        ft.commit()
    }
}
