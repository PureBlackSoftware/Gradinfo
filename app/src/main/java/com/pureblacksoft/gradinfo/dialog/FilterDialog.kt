package com.pureblacksoft.gradinfo.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.pureblacksoft.gradinfo.R
import com.pureblacksoft.gradinfo.adapter.ButtonAdapter
import com.pureblacksoft.gradinfo.databinding.DialogFilterBinding

class FilterDialog(context: Context) : Dialog(context), AdapterView.OnItemSelectedListener
{
    companion object {
        //region Degree Id
        const val ID_DEGREE_ALL = 0
        const val ID_DEGREE_CMP_ENG = 1
        const val ID_DEGREE_CMP_ENG_EE = 2
        const val ID_DEGREE_MED_ENG = 3
        const val ID_DEGREE_ENV_ENG = 4
        const val ID_DEGREE_ELC_ENG = 5
        const val ID_DEGREE_ELC_ENG_EE = 6
        const val ID_DEGREE_IND_ENG = 7
        const val ID_DEGREE_CVL_ENG = 8
        const val ID_DEGREE_MEC_ENG = 9
        const val ID_DEGREE_MEC_ENG_EE = 10
        const val ID_DEGREE_TEX_ENG = 11
        //endregion

        //region Year Id
        const val ID_YEAR_ALL = 0
        const val ID_YEAR_2010 = 1
        const val ID_YEAR_2011 = 2
        const val ID_YEAR_2012 = 3
        const val ID_YEAR_2013 = 4
        const val ID_YEAR_2014 = 5
        const val ID_YEAR_2015 = 6
        const val ID_YEAR_2016 = 7
        const val ID_YEAR_2017 = 8
        const val ID_YEAR_2018 = 9
        const val ID_YEAR_2019 = 10
        const val ID_YEAR_2020 = 11
        const val ID_YEAR_2021 = 12
        //endregion

        var currentDegreeId = ID_DEGREE_ALL
        var currentYearId = ID_YEAR_ALL

        var onCancel: (() -> Unit)? = null
        var onApply: (() -> Unit)? = null
    }

    private lateinit var binding: DialogFilterBinding
    private val degreeList = mutableListOf<String>()
    private val yearList = mutableListOf<String>()
    private var selectedDegreeId = ID_DEGREE_ALL
    private var selectedYearId = ID_YEAR_ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //region Spinner
        //region Degree
        binding.spnDegreeFD.onItemSelectedListener = this

        degreeList.add(context.getString(R.string.Filter_Degree_All))
        degreeList.add(context.getString(R.string.Filter_Degree_Cmp_Eng))
        degreeList.add(context.getString(R.string.Filter_Degree_Cmp_Eng_EE))
        degreeList.add(context.getString(R.string.Filter_Degree_Med_Eng))
        degreeList.add(context.getString(R.string.Filter_Degree_Env_Eng))
        degreeList.add(context.getString(R.string.Filter_Degree_Elc_Eng))
        degreeList.add(context.getString(R.string.Filter_Degree_Elc_Eng_EE))
        degreeList.add(context.getString(R.string.Filter_Degree_Ind_Eng))
        degreeList.add(context.getString(R.string.Filter_Degree_Cvl_Eng))
        degreeList.add(context.getString(R.string.Filter_Degree_Mec_Eng))
        degreeList.add(context.getString(R.string.Filter_Degree_Mec_Eng_EE))
        degreeList.add(context.getString(R.string.Filter_Degree_Tex_Eng))

        val degreeAdapter = ButtonAdapter(context, degreeList)
        binding.spnDegreeFD.adapter = degreeAdapter
        //endregion

        //region Year
        binding.spnYearFD.onItemSelectedListener = this

        yearList.add(context.getString(R.string.Filter_Year_All))
        yearList.add(context.getString(R.string.Filter_Year_2010))
        yearList.add(context.getString(R.string.Filter_Year_2011))
        yearList.add(context.getString(R.string.Filter_Year_2012))
        yearList.add(context.getString(R.string.Filter_Year_2013))
        yearList.add(context.getString(R.string.Filter_Year_2014))
        yearList.add(context.getString(R.string.Filter_Year_2015))
        yearList.add(context.getString(R.string.Filter_Year_2016))
        yearList.add(context.getString(R.string.Filter_Year_2017))
        yearList.add(context.getString(R.string.Filter_Year_2018))
        yearList.add(context.getString(R.string.Filter_Year_2019))
        yearList.add(context.getString(R.string.Filter_Year_2020))
        yearList.add(context.getString(R.string.Filter_Year_2021))

        val yearAdapter = ButtonAdapter(context, yearList)
        binding.spnYearFD.adapter = yearAdapter
        //endregion
        //endregion

        //region Button
        binding.txtDegreeFD.setOnClickListener {
            binding.spnDegreeFD.performClick()
        }

        binding.txtYearFD.setOnClickListener {
            binding.spnYearFD.performClick()
        }

        binding.txtCancelFD.setOnClickListener {
            onCancel?.invoke()
        }

        binding.txtApplyFD.setOnClickListener {
            currentDegreeId = selectedDegreeId
            currentYearId = selectedYearId

            onApply?.invoke()
        }
        //endregion
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedDegreeId = binding.spnDegreeFD.selectedItemPosition
        binding.txtDegreeFD.text = degreeList[binding.spnDegreeFD.selectedItemPosition]

        selectedYearId = binding.spnYearFD.selectedItemPosition
        binding.txtYearFD.text = yearList[binding.spnYearFD.selectedItemPosition]

        /*
        when (binding.spnDegreeFD.selectedItemPosition) {
            0 -> {
                selectedDegreeId = ID_DEGREE_ALL
                binding.txtDegreeFD.text = context.getString(R.string.Filter_Degree_All)
            }
            1 -> {
                selectedDegreeId = ID_DEGREE_CMP_ENG
                binding.txtDegreeFD.text = context.getString(R.string.Filter_Degree_Cmp_Eng)
            }
            2 -> {
                selectedDegreeId = ID_DEGREE_CMP_ENG_EE
                binding.txtDegreeFD.text = context.getString(R.string.Filter_Degree_Cmp_Eng_EE)
            }
            3 -> {
                selectedDegreeId = ID_DEGREE_MED_ENG
                binding.txtDegreeFD.text = context.getString(R.string.Filter_Degree_Med_Eng)
            }
            4 -> {
                selectedDegreeId = ID_DEGREE_ENV_ENG
                binding.txtDegreeFD.text = context.getString(R.string.Filter_Degree_Env_Eng)
            }
            5 -> {
                selectedDegreeId = ID_DEGREE_ELC_ENG
                binding.txtDegreeFD.text = context.getString(R.string.Filter_Degree_Elc_Eng)
            }
            6 -> {
                selectedDegreeId = ID_DEGREE_ELC_ENG_EE
                binding.txtDegreeFD.text = context.getString(R.string.Filter_Degree_Elc_Eng_EE)
            }
            7 -> {
                selectedDegreeId = ID_DEGREE_IND_ENG
                binding.txtDegreeFD.text = context.getString(R.string.Filter_Degree_Ind_Eng)
            }
            8 -> {
                selectedDegreeId = ID_DEGREE_CVL_ENG
                binding.txtDegreeFD.text = context.getString(R.string.Filter_Degree_Cvl_Eng)
            }
            9 -> {
                selectedDegreeId = ID_DEGREE_MEC_ENG
                binding.txtDegreeFD.text = context.getString(R.string.Filter_Degree_Mec_Eng)
            }
            10 -> {
                selectedDegreeId = ID_DEGREE_MEC_ENG_EE
                binding.txtDegreeFD.text = context.getString(R.string.Filter_Degree_Mec_Eng_EE)
            }
            11 -> {
                selectedDegreeId = ID_DEGREE_TEX_ENG
                binding.txtDegreeFD.text = context.getString(R.string.Filter_Degree_Tex_Eng)
            }
        }

        when (binding.spnYearFD.selectedItemPosition) {
            0 -> {
                selectedYearId = ID_YEAR_ALL
                binding.txtYearFD.text = context.getString(R.string.Filter_Year_All)
            }
            1 -> {
                selectedYearId = ID_YEAR_2010
                binding.txtYearFD.text = context.getString(R.string.Filter_Year_2010)
            }
            2 -> {
                selectedYearId = ID_YEAR_2011
                binding.txtYearFD.text = context.getString(R.string.Filter_Year_2011)
            }
            3 -> {
                selectedYearId = ID_YEAR_2012
                binding.txtYearFD.text = context.getString(R.string.Filter_Year_2012)
            }
            4 -> {
                selectedYearId = ID_YEAR_2013
                binding.txtYearFD.text = context.getString(R.string.Filter_Year_2013)
            }
            5 -> {
                selectedYearId = ID_YEAR_2014
                binding.txtYearFD.text = context.getString(R.string.Filter_Year_2014)
            }
            6 -> {
                selectedYearId = ID_YEAR_2015
                binding.txtYearFD.text = context.getString(R.string.Filter_Year_2015)
            }
            7 -> {
                selectedYearId = ID_YEAR_2016
                binding.txtYearFD.text = context.getString(R.string.Filter_Year_2016)
            }
            8 -> {
                selectedYearId = ID_YEAR_2017
                binding.txtYearFD.text = context.getString(R.string.Filter_Year_2017)
            }
            9 -> {
                selectedYearId = ID_YEAR_2018
                binding.txtYearFD.text = context.getString(R.string.Filter_Year_2018)
            }
            10 -> {
                selectedYearId = ID_YEAR_2019
                binding.txtYearFD.text = context.getString(R.string.Filter_Year_2019)
            }
            11 -> {
                selectedYearId = ID_YEAR_2020
                binding.txtYearFD.text = context.getString(R.string.Filter_Year_2020)
            }
            12 -> {
                selectedYearId = ID_YEAR_2021
                binding.txtYearFD.text = context.getString(R.string.Filter_Year_2021)
            }
        }
        */
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun show() {
        super.show()

        binding.spnDegreeFD.setSelection(currentDegreeId)
        binding.spnYearFD.setSelection(currentYearId)
    }
}