package com.pureblacksoft.gradinfo.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pureblacksoft.gradinfo.R
import com.pureblacksoft.gradinfo.activity.MainActivity
import com.pureblacksoft.gradinfo.adapter.GradAdapter
import com.pureblacksoft.gradinfo.databinding.FragmentSearchBinding
import com.pureblacksoft.gradinfo.service.GradDataService

class SearchFragment : Fragment(R.layout.fragment_search)
{
    companion object {
        private const val TAG = "SearchFragment"
    }

    private var _context: Context? = null
    private var _activity: MainActivity? = null
    private var _binding: FragmentSearchBinding? = null

    private val mContext get() = _context!!
    private val activity get() = _activity!!
    private val binding get() = _binding!!

    private lateinit var gradAdapter: GradAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        _context = requireContext()
        _activity = requireActivity() as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //region Toolbar
        activity.binding.imgToolbarIconMA.setImageResource(R.drawable.ic_search)
        activity.binding.txtToolbarTitleMA.text = getString(R.string.Search_Title)
        activity.binding.imgToolbarButtonMA.setImageResource(R.drawable.ic_parse)
        //endregion

        //region RecyclerView
        val linearManager = LinearLayoutManager(mContext)
        linearManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerSF.layoutManager = linearManager

        val itmDecoration = DividerItemDecoration(mContext, linearManager.orientation)
        ContextCompat.getDrawable(mContext, R.drawable.shape_divider)?.let { itmDecoration.setDrawable(it) }
        binding.recyclerSF.addItemDecoration(itmDecoration)

        setGradAdapter()
        //endregion

        //region Search
        binding.searchSF.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                gradAdapter.filter.filter(newText)

                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (gradAdapter.itemCount == 0) {
                    binding.txtNoResultSF.visibility = View.VISIBLE
                }

                binding.searchSF.clearFocus()

                return false
            }
        })

        binding.searchSF.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.lnrInfoSF.visibility = View.GONE
                binding.txtNoResultSF.visibility = View.GONE
            } else if (gradAdapter.itemCount == 0 && binding.txtNoResultSF.visibility == View.GONE) {
                binding.lnrInfoSF.visibility = View.VISIBLE
            }
        }
        //endregion
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    override fun onDetach() {
        super.onDetach()

        _context = null
        _activity = null
    }

    private fun setGradAdapter() {
        if (_binding != null && GradDataService.gradList.size != 0) {
            Log.d(TAG, "setGradAdapter: Running")

            gradAdapter = GradAdapter(GradDataService.gradList)
            binding.recyclerSF.adapter = gradAdapter
            gradAdapter.filter.filter("")
        }
    }
}