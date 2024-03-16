package com.cs4520.assignment1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cs4520.assignment1.Product
import com.cs4520.assignment1.api.RetrofitClient
import com.cs4520.assignment1.database.DatabaseProvider
import com.cs4520.assignment1.database.ProductEntity
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _productList = MutableLiveData<List<Product>>(listOf())
    val productList: LiveData<List<Product>> = _productList

    private val _errors = MutableLiveData<String?>(null)
    val errors: LiveData<String?> = _errors

    fun init() {
        val call = RetrofitClient.instance.fetchData();
        _isLoading.value = true;
        call.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    _isLoading.value = false;
                    val listType = object : TypeToken<List<Product>>() {}.type
                    val products: List<Product> = Gson().fromJson(data, listType)
                    if (products.size == 0) {
                        _productList.value = listOf()
                        _errors.value = "No Products Available"
                    }
                    else {
                        val processedProducts = filterProducts(products);
                        setdb(processedProducts)
                        _productList.value = processedProducts
                        _errors.value = null;
                    }
                } else {
                    _isLoading.value = false;
                    _errors.value = "Unknown Error";
                    _productList.value = listOf();
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                _isLoading.value = false;

                // Assuming user is not connected to internet.
                setProductsFromDb();
            }
        })
    }

    private fun setProductsFromDb() {
        viewModelScope.launch {
            val products = DatabaseProvider.getDatabase()?.productDao()?.getAll();
            if (products != null && products.size > 0) {
                _productList.value = products.map {
                        p -> Product(p!!.name, p.type, p.expiryDate, p.price)
                }
                _errors.value = null;
            }
            else {
                _errors.value = "Not connected to internet";
            }
        }
    }

    private fun filterProducts(products: List<Product>): List<Product> {
        val outProds: MutableList<Product> = mutableListOf();

        val productNamesSeen: MutableSet<String> = mutableSetOf();
        for (p in products) {
            if (
                (p.type == "Equipment" || p.type == "Food") &&
                p.name != null &&
                p.price != null &&
                (p.type == "Equipment" || p.expiryDate != null) &&
                !productNamesSeen.contains(p.name)
            ){
                productNamesSeen.add(p.name)
                outProds.add(p)
            }
        }

        return outProds
    }
    private fun setdb(products: List<Product>) {
        val dao = DatabaseProvider.getDatabase()?.productDao()
        if (dao != null) {
            viewModelScope.launch {
                dao.clearProducts();
                val entities = products.map {
                    val entity = ProductEntity();
                    entity.name = it.name;
                    entity.type = it.type;
                    entity.price = it.price;
                    entity.expiryDate = it.expiryDate;
                    entity
                };
                dao.insertAll(entities);
            }
        }
    }


}