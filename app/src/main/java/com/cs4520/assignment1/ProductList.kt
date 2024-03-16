package com.cs4520.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cs4520.assignment1.databinding.ProductListFragmentBinding
import com.cs4520.assignment1.viewModel.ProductListViewModel

class ProductListFragment : Fragment() {

    private lateinit var binding: ProductListFragmentBinding;
    private lateinit var viewModel: ProductListViewModel;
    private lateinit var adapter: ProductAdapter;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductListFragmentBinding.inflate(inflater, container, false)
        binding.productListView.layoutManager = LinearLayoutManager(context)
        adapter = ProductAdapter()
        binding.productListView.adapter = adapter;
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductListViewModel::class.java)
        viewModel.init()
    }

    override fun onResume() {
        super.onResume();
        setObservers()
    }

    private fun setObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { b ->
            if (b) {
                binding.progressBar.visibility = View.VISIBLE;
                binding.productListView.visibility = View.INVISIBLE;
            }
            else {
                binding.progressBar.visibility = View.INVISIBLE;
                binding.productListView.visibility = View.VISIBLE;
            }
        })

        viewModel.productList.observe(viewLifecycleOwner, Observer { l ->
            adapter.setProducts(l);
        })

        viewModel.errors.observe(viewLifecycleOwner, Observer { err ->
            if (err == null) {
                binding.errText.visibility = View.GONE;
            }
            else {
                binding.errText.visibility = View.VISIBLE;
                binding.errText.text = err;
            }
        })
    }

}