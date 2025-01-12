package vn.edu.hust.formtest.fragments.shopping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import vn.edu.hust.formtest.adapters.BestProductAdapter
import vn.edu.hust.formtest.util.Resource
import vn.edu.hust.formtest.util.showBottomNavigationView
import vn.edu.hust.formtest.viewmodel.SearchViewModel
import vn.edu.hust.graduationproject.R
import vn.edu.hust.graduationproject.databinding.FragmentSearchBinding

private val TAG = "SearchFragment"
@AndroidEntryPoint
class SearchFragment: Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var ProductsAdapter: BestProductAdapter
    private val viewModel by viewModels<SearchViewModel>()
    private var searchQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupProductsRv()

        ProductsAdapter.onClick = {
            val b = Bundle().apply{
                putParcelable("product",it)
            }
            findNavController().navigate(R.id.action_searchFragment_to_productDetailsFragment,b)
        }

        binding.icSearch.setOnClickListener {
            searchQuery = binding.edSearch.toString()
            viewModel.fetchProducts(searchQuery)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.Products.collectLatest {
                when (it){
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        ProductsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Error -> {
                        hideLoading()
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
        binding.nestedScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener{ v, _, scrollY, _, _ ->
            if (v.getChildAt(0).bottom <= v.height + scrollY){
                viewModel.fetchProducts(searchQuery)
            }
        })
    }

    private fun setupProductsRv() {
        ProductsAdapter = BestProductAdapter()
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            adapter = ProductsAdapter
        }
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()

        showBottomNavigationView()
    }
}