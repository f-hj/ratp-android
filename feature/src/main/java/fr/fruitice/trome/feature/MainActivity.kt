package fr.fruitice.trome.feature

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Intent
import android.view.Menu
import android.view.MenuItem


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_credits -> {
                val intent = Intent(this, CreditsActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return false
    }
}
