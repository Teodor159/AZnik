package com.example.aznik

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import android.widget.TextView





class FormActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var navButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        viewPager = findViewById(R.id.view_pager)
        navButton = findViewById(R.id.nav_button)

        viewPager.adapter = FormPagerAdapter(this)

        updateNavButtonText()

        navButton.setOnClickListener {
            val currentItem = viewPager.currentItem
            if (currentItem < 2) {
                viewPager.setCurrentItem(currentItem + 1, true)
                updateNavButtonText()
            } else {
                // Handle form submission
            }
        }
    }

    private fun updateNavButtonText() {
        val currentItem = viewPager.currentItem
        navButton.text = if (currentItem == 2) "Submit" else "Next"
    }

    private inner class FormPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CerpackaFragment()
                1 -> VozidloFragment()
                else -> FinalQuestionFragment()
            }
        }
    }
    fun updateSelectedChoice(fragmentClassName: String, choice: String) {
        findViewById<TextView>(R.id.selected_choice).text = choice
    }

    fun updateFinalQuestionFragment(fragmentClassName: String, choice: Int) {
        val finalQuestionFragment = supportFragmentManager.fragments.lastOrNull() as? FinalQuestionFragment
        finalQuestionFragment?.updateSelectedChoice(fragmentClassName, choice)
    }



}
