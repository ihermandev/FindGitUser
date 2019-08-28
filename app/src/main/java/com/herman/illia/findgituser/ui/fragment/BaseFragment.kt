package com.herman.illia.findgituser.ui.fragment

import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Created by illia on 8/27/2019.
 */
abstract class BaseFragment : Fragment() {
    open fun showToast(msg: String) {
        Toast.makeText(requireContext(),msg,Toast.LENGTH_SHORT).show()
    }
}