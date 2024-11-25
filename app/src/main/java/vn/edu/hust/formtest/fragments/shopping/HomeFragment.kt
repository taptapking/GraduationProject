package vn.edu.hust.formtest.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import vn.edu.hust.formtest.adapters.HomeViewpagerAdapter
import vn.edu.hust.formtest.fragments.categories.AccessoryFragment
import vn.edu.hust.formtest.fragments.categories.CamcorderFragment
import vn.edu.hust.formtest.fragments.categories.CompactFragment
import vn.edu.hust.formtest.fragments.categories.DSLRFragment
import vn.edu.hust.formtest.fragments.categories.MainCategoryFragment
import vn.edu.hust.formtest.fragments.categories.MirrorlessFragment
import vn.edu.hust.graduationproject.R
import vn.edu.hust.graduationproject.databinding.FragmentHomeBinding

class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoriesFragments = arrayListOf<Fragment>(
            MainCategoryFragment(),
            DSLRFragment(),
            MirrorlessFragment(),
            CompactFragment(),
            CamcorderFragment(),
            AccessoryFragment()
        )

        binding.viewpagerHome.isUserInputEnabled = false

        val viewPager2Adapter = HomeViewpagerAdapter(categoriesFragments, childFragmentManager, lifecycle)
        binding.viewpagerHome.adapter = viewPager2Adapter
        TabLayoutMediator(binding.tabLayout,binding.viewpagerHome){ tab, position ->
            when (position){
                0 -> tab.text = "Main"
                1 -> tab.text = "DSLR"
                2 -> tab.text = "Mirrorless"
                3 -> tab.text = "Compact"
                4 -> tab.text = "Camcorder"
                5 -> tab.text = "Accessory"
            }
        }.attach()
    }
}