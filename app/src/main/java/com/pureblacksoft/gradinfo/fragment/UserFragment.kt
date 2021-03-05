package com.pureblacksoft.gradinfo.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pureblacksoft.gradinfo.R
import com.pureblacksoft.gradinfo.activity.MainActivity
import com.pureblacksoft.gradinfo.activity.PrefActivity
import com.pureblacksoft.gradinfo.databinding.FragmentUserBinding

class UserFragment : Fragment(R.layout.fragment_user)
{
    private var _context: Context? = null
    private var _activity: MainActivity? = null
    private var _binding: FragmentUserBinding? = null

    private val mContext get() = _context!!
    private val activity get() = _activity!!
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)

        _context = requireContext()
        _activity = requireActivity() as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //region Toolbar
        activity.binding.imgToolbarIconMA.setImageResource(R.drawable.ic_user_bwhite)
        activity.binding.txtToolbarTitleMA.text = getString(R.string.User_Title)
        activity.binding.imgToolbarButtonMA.setImageResource(R.drawable.ic_pref_bwhite)
        //endregion

        //region Button
        activity.binding.imgToolbarButtonMA.setOnClickListener {
            val intent = Intent(mContext, PrefActivity::class.java)
            mContext.startActivity(intent)
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
}