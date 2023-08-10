package com.example.appstore.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appstore.R
import com.example.appstore.base.BaseFragment
import com.example.appstore.base.showMessageError
import com.example.appstore.data.domain.AppsDetailModel
import com.example.appstore.data.domain.core.ResponseState
import com.example.appstore.databinding.FragmentHomeBinding
import com.example.appstore.ui.detail.DetailFragment
import com.example.appstore.ui.util.Constants

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel = HomeViewModel()

    private val appsAdapter by lazy {
        AppsAdapter()
    }

    override fun initComponents(savedInstanceState: Bundle?) {
        appsAdapter.listener = { view, item, position ->
            val detailFragment = DetailFragment()
            val args = Bundle()
            args.putParcelable(Constants.APP, item)
            detailFragment.arguments = args
            detailFragment.show(requireFragmentManager(), "detailDialogTag")

        }
        binding.appsListRecycler.adapter = appsAdapter
        viewModel.getAppsList()
        val layoutManager = GridLayoutManager(requireContext(), 6)
        binding.appsListRecycler.layoutManager = layoutManager

        binding.appsListRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    binding.fabScroll.show()
                } else {
                    binding.fabScroll.hide()
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        binding.fabScroll.setOnClickListener { view ->
            binding.appsListRecycler.post {
                binding.appsListRecycler.smoothScrollToPosition(0)
            }
        }

        binding.appSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                appsAdapter.filter.filter(newText)
                return false
            }
        })
        loadCategories()
    }

    private fun loadCategories() {
        val items= resources.getStringArray(R.array.categories)
        val spinnerAdapter= object : ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_item, items) {

            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                if(position == 0) {
                    view.setTextColor(Color.GRAY)
                }
                return view
            }
        }
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categoriesSpinner.adapter = spinnerAdapter

        binding.categoriesSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent?.getItemAtPosition(position).toString()
                if(value == items[0]){
                    (view as? TextView)?.setTextColor(Color.GRAY)
                } else {
                    appsAdapter.filter.filter(binding.categoriesSpinner.selectedItem.toString())
                }
            }
        }
    }

    override fun observe() {
        viewModel.getAppsResponse.observe(this) {
            when (it) {
                is ResponseState.Loading -> {
                    Toast.makeText(requireContext(), getString(R.string.load_data), Toast.LENGTH_SHORT).show()
                }
                is ResponseState.Success -> {
                    (it.data as? List<AppsDetailModel>)?.let { appsModel ->
                        appsAdapter.apply {
                            addItems(appsModel)
                            filter.filter("")
                        }
                    }
                }
                is ResponseState.Error -> {
                    it.error.showMessageError(this)
                }
                else -> {getString(R.string.error)}
            }
        }
    }
}