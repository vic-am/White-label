package com.victoramaral.whitelabel.ui.addproduct

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import com.victoramaral.whitelabel.databinding.AddProductFragmentBinding
import com.victoramaral.whitelabel.util.CurrencyTextWatcher
import com.victoramaral.whitelabel.util.PRODUCT_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductFragment : BottomSheetDialogFragment() {

    private var _binding: AddProductFragmentBinding? = null
    private val binding get() = _binding!!

    private var imageURI: Uri? = null

    private val viewModel: AddProductViewModel by viewModels()

    private val getContent = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        imageURI = it
        binding.imageProduct.setImageURI(it)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddProductFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        observeViewModelEvents()
    }

    private fun observeViewModelEvents() {
        viewModel.imageUriErrorResId.observe(viewLifecycleOwner) {
            binding.imageProduct.setBackgroundResource(it)
        }

        viewModel.descriptionFieldErrorResId.observe(viewLifecycleOwner) {
            binding.inputLayoutDescription.setError(it)
        }

        viewModel.priceFieldErrorResId.observe(viewLifecycleOwner) {
            binding.inputLayoutPrice.setError(it)
        }

        viewModel.productCreated.observe(viewLifecycleOwner) {
            findNavController().run {
                previousBackStackEntry?.savedStateHandle?.set(PRODUCT_KEY, it)
                popBackStack()
            }
        }
    }

    private fun setListener() {
        binding.imageProduct.setOnClickListener {
            chooseImage()
        }

        binding.buttonAddProduct.setOnClickListener {
            val description = binding.inputDescription.text.toString()
            val price = binding.inputPrice.text.toString()
            viewModel.createProduct(description, price, imageURI)
        }

        binding.inputPrice.run {
            addTextChangedListener(CurrencyTextWatcher(this))
        }

    }

    private fun chooseImage() {
        getContent.launch("image/*")

    }

    private fun TextInputLayout.setError(stringResId: Int?) {
        error = if (stringResId != null) {
            getString(stringResId)
        } else null
    }

}