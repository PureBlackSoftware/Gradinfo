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
import com.pureblacksoft.gradinfo.databinding.FragmentHomeBinding
import com.pureblacksoft.gradinfo.service.GradDataService

class HomeFragment : Fragment(R.layout.fragment_home)
{
    companion object {
        private const val TAG = "HomeFragment"
    }

    private var _context: Context? = null
    private var _activity: MainActivity? = null
    private var _binding: FragmentHomeBinding? = null

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //region Toolbar
        activity.binding.imgToolbarIconMA.setImageResource(R.drawable.ic_home)
        activity.binding.txtToolbarTitleMA.text = getString(R.string.Home_Title)
        activity.binding.imgToolbarButtonMA.setImageResource(R.drawable.ic_filter)
        //endregion

        //region RecyclerView
        val linearManager = LinearLayoutManager(mContext)
        linearManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerHF.layoutManager = linearManager

        val itmDecoration = DividerItemDecoration(mContext, linearManager.orientation)
        ContextCompat.getDrawable(mContext, R.drawable.shape_divider)?.let { itmDecoration.setDrawable(it) }
        binding.recyclerHF.addItemDecoration(itmDecoration)

        setGradAdapter()
        //endregion

        //region Event
        MainActivity.onSuccessfulService = {
            setGradAdapter()
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
            binding.recyclerHF.adapter = gradAdapter
        }
    }
}