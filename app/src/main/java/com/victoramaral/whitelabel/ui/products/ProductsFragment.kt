package com.victoramaral.whitelabel.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.fragment.findNavController
import com.victoramaral.whitelabel.R
import com.victoramaral.whitelabel.databinding.ProductsFragmentBinding
import com.victoramaral.whitelabel.domain.model.Product
import com.victoramaral.whitelabel.util.PRODUCT_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private var _binding: ProductsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductsViewModel by viewModels()

    private val productsAdapter = ProductsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductsFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        setListeners()
        observeViewModelEvents()
        observeNavBackStack()
        getProducts()
    }

    private fun setRecyclerView() {
        binding.productsRecycler.run {
            setHasFixedSize(true)
            adapter = productsAdapter
        }
    }

    private fun setListeners() {
        with(binding) {
            addFab.setOnClickListener {
                findNavController().navigate(R.id.action_nav_products_fragment_to_addProductFragment)
            }

            productsSwipe.setOnRefreshListener {
                getProducts()
            }
        }
    }

    private fun getProducts() {
        viewModel.getProducts()
    }

    private fun observeNavBackStack() {
        findNavController().run {
            val navBackStackEntry = getBackStackEntry(R.id.products_fragment)
            val savedStateHandle = navBackStackEntry.savedStateHandle
            val observer = LifecycleEventObserver { _, event ->
                if (
                    event == Lifecycle.Event.ON_RESUME
                    && savedStateHandle.contains(PRODUCT_KEY)
                ) {
                    val product = savedStateHandle.get<Product>(PRODUCT_KEY)
                    val adapterCurrentList = productsAdapter.currentList
                    val newList = adapterCurrentList.toMutableList().apply {
                        add(product)
                    }
                    productsAdapter.submitList(newList)
                    binding.productsRecycler.smoothScrollToPosition(newList.size - 1)
                    savedStateHandle.remove<Product>(PRODUCT_KEY)
                }
            }

            navBackStackEntry.lifecycle.addObserver(observer)

            viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    navBackStackEntry.lifecycle.removeObserver(observer)
                }
            })
        }
    }

    private fun observeViewModelEvents() {
        viewModel.productsData.observe(viewLifecycleOwner) { products ->
            productsAdapter.submitList(products)
            binding.productsSwipe.isRefreshing = false
        }
        viewModel.addButtonVisibilityData.observe(viewLifecycleOwner) { visibility ->
            binding.addFab.visibility = visibility
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}