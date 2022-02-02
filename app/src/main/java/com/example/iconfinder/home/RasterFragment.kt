package com.example.iconfinder.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.example.iconfinder.R

class RasterFragment : DialogFragment() {

    private var list: Array<String>? = null
    private var listener: DialogItemClicked? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeActivity) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            arguments?.let { bundle ->
                list = bundle.getStringArray(RASTER_SIZE_LIST)
            }

            val builder = AlertDialog.Builder(it)
            builder.setTitle(R.string.pick_raster_size)
                    .setItems(list, DialogInterface.OnClickListener { dialog, which ->
                        listener?.rasterSelected(list?.get(which) ?: "")
                })

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface DialogItemClicked {
        fun rasterSelected(raster: String)
    }

    companion object {

        private const val RASTER_SIZE_LIST = "rasterList"

        fun newInstance(list: Array<String>) =
            RasterFragment().apply {
                arguments = Bundle().apply {
                    putStringArray(RASTER_SIZE_LIST, list)
                }
            }
    }
}