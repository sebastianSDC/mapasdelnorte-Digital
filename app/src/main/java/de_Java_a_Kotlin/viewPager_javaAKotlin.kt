package de_Java_a_Kotlin

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import com.example.pilasnotebook.mapasdelnortedigital.R
import com.example.pilasnotebook.mapasdelnortedigital.view.fragment.*


class ViewPagerAdminActivity /*: AppCompatActivity()*//*, CarteleraClienteFragment.OnFragmentInteractionListener, CuponeraClienteFragment.OnFragmentInteractionListener, MapaClienteFragment.OnFragmentInteractionListener, FotosClienteFragment.OnFragmentInteractionListener, DatosClienteFragment.OnFragmentInteractionListener, CatalogoOnLineClienteFragment.OnFragmentInteractionListener*/ {

   /* private var mSectionsPagerAdapter: PlaceholderFragment.SectionsPagerAdapter? = null

    *//**
     * The [ViewPager] that will host the section contents.
     *//*
    private var mViewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager_admin)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = PlaceholderFragment.SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById<View>(R.id.container) as ViewPager
        mViewPager!!.adapter = mSectionsPagerAdapter

        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout

        mViewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mViewPager))

        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_view_pager_admin, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onFragmentInteraction(uri: Uri) {

    }

    *//**
     * A placeholder fragment containing a simple view.
     *//*
    class PlaceholderFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            super.onCreateView(inflater, container, savedInstanceState)

            return inflater.inflate(R.layout.activity_view_pager_admin, container, false)
        }


        class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

            override fun getItem(position: Int): Fragment? {
                return newInstance(position + 1)

            }

            override fun getCount(): Int {
                return 6
            }


            override fun getPageTitle(position: Int): CharSequence? {
                when (position) {
                    0 -> return "Datos"
                    1 -> return "Mapa"
                    2 -> return "Cuponera"
                    3 -> return "Cartelera"
                    4 -> return "Fotos"
                    5 -> return "Catalogo On-Line"
                }
                return null
            }


        }

        companion object {
            *//**
             * The fragment argument representing the section number for this
             * fragment.
             *//*
            private val ARG_SECTION_NUMBER = "section_number"

            *//**
             * Returns a new instance of this fragment for the given section
             * number.
             *//*
            fun newInstance(sectionNumber: Int): Fragment? {
                var fragment: Fragment? = null

                when (sectionNumber) {

                    1 -> fragment = DatosClienteFragment()
                    2 -> fragment = MapaClienteFragment()
                    3 -> fragment = CuponeraClienteFragment()
                    4 -> fragment = CarteleraClienteFragment()
                    5 -> fragment = FotosClienteFragment()
                    6 -> fragment = CatalogoOnLineClienteFragment()
                }
                return fragment
            }
        }
    }
*/
}
