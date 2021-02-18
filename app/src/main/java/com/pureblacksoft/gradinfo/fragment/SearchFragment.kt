package com.pureblacksoft.gradinfo.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pureblacksoft.gradinfo.R
import com.pureblacksoft.gradinfo.activity.MainActivity
import com.pureblacksoft.gradinfo.databinding.FragmentSearchBinding

class SearchFragment : Fragment(R.layout.fragment_search)
{
    private var _context: Context? = null
    private var _activity: MainActivity? = null
    private var _binding: FragmentSearchBinding? = null

    private val mContext get() = _context!!
    private val activity get() = _activity!!
    private val binding get() = _binding!!

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
        activity.binding.imgIconMTB.setImageResource(R.drawable.ic_search_bwhite)
        activity.binding.txtTitleMTB.text = getString(R.string.Search_Title)
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
}