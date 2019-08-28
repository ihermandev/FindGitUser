package com.herman.illia.findgituser.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.herman.illia.findgituser.R
import com.herman.illia.findgituser.adapter.UserAdapter
import com.herman.illia.findgituser.utils.InjectorUtils
import com.herman.illia.findgituser.utils.RecyclerSwipeToDeleteCallback
import com.herman.illia.findgituser.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.feature_search.*
import kotlinx.android.synthetic.main.fragment_added.*

class AddedFragment : BaseFragment() {

    lateinit var mUserViewModel: UserViewModel
    private lateinit var mAdapter: UserAdapter
    private var itemTouchHelper: ItemTouchHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_added, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initView()
    }

    private fun initView() {
        val factory = InjectorUtils.provideUserViewModelFactory(requireContext())
        mUserViewModel = ViewModelProviders.of(this, factory).get(UserViewModel::class.java)
        mUserViewModel.getAllUsersList().observe(viewLifecycleOwner, Observer {
            mAdapter.addUsers(it)
            if (it.isNotEmpty()) {
                showDeleteAll()
            } else {
                hideDeleteAll()
            }
        })

    }

    private fun showDeleteAll() {
        btn_delete_all.visibility = View.VISIBLE
        btn_delete_all.setOnClickListener {
            askAboutDeleteAll()
        }
    }

    private fun hideDeleteAll() {
        btn_delete_all.visibility = View.GONE
    }

    private fun initAdapter() {
        mAdapter = UserAdapter(arrayListOf(), null, false)
        base_recycler.layoutManager = LinearLayoutManager(context)
        base_recycler.visibility = View.VISIBLE
        base_recycler.adapter = mAdapter
        initSwipeToDelete(base_recycler)
    }

    private fun initSwipeToDelete(recyclerView: RecyclerView?) {
        val swipeToDeleteCallback = object : RecyclerSwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(@NonNull viewHolder: RecyclerView.ViewHolder, i: Int) {
                val position = viewHolder.adapterPosition
                val item = mAdapter.getData().get(position)
                mAdapter.removeUser(position)
                item.let { mUserViewModel.deleteUser(it) }

            }
        }
        itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper?.attachToRecyclerView(recyclerView)
    }

    private fun askAboutDeleteAll() {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setMessage(getString(R.string.popup_title))
            ?.setPositiveButton(getString(R.string.popup_positive)) { _, _ ->
                mUserViewModel.deleteAllUsers()
            }
            ?.setNegativeButton(getString(R.string.popup_negative)) { _, _ ->

            }
            ?.show()
    }

}