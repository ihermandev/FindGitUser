package com.herman.illia.findgituser.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.herman.illia.findgituser.R
import com.herman.illia.findgituser.adapter.UserAdapter
import com.herman.illia.findgituser.adapter.listener.UserAddListener
import com.herman.illia.findgituser.model.ErrorModel
import com.herman.illia.findgituser.model.UserModel
import com.herman.illia.findgituser.network.ServerClient
import com.herman.illia.findgituser.network.listener.RequestUserListener
import com.herman.illia.findgituser.utils.InjectorUtils
import com.herman.illia.findgituser.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.feature_search.*
import kotlinx.android.synthetic.main.fragment_search.*
import javax.security.auth.login.LoginException

class SearchFragment : BaseFragment(), RequestUserListener {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initServerClient()
        loadSearchView(searchView = searchView)
        initViewModel()

    }

    private fun initViewModel() {
        val factory = InjectorUtils.provideUserViewModelFactory(requireContext())
        mUserViewModel = ViewModelProviders.of(this, factory).get(UserViewModel::class.java)
    }

    private fun initServerClient() {
        ServerClient.initServerClient(requireContext())
        ServerClient.registerGetUserListener(this)
    }

    private fun loadSearchView(searchView: SearchView?) {
        val manager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView?.isSubmitButtonEnabled = true
        searchView?.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mUserViewModel.getUserByLogin(query).observe(viewLifecycleOwner, Observer {
                    if (it == null) {
                        showProgress()
                        ServerClient.requestUser(query)
                    } else {
                        showToast("User already added")
                    }
                })
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.isBlank()) {
                    base_recycler.visibility = View.GONE
                }
                return true
            }
        })
    }

    private fun initAdapter(userModelData: UserModel) {
        userModelData.let {
            base_recycler.layoutManager = LinearLayoutManager(context)
            base_recycler.visibility = View.VISIBLE
            base_recycler.adapter = UserAdapter(
                dataList = arrayListOf(userModelData),
                isSearchMode = true,
                listener = addUserListener
            )
        }
    }

    private val addUserListener = object : UserAddListener {
        override fun userAdded(user: UserModel) {
            mUserViewModel.addUser(user)
            base_recycler.visibility = View.GONE
        }
    }

    override fun responseUserListener(isSuccess: Boolean, userModelData: UserModel?, code: Int?) {
        hideProgress()
        when {
            isSuccess && userModelData != null -> {
                initAdapter(userModelData)
            }
            code == 404 -> {
                showPlaceholder()
            }
            else -> {
                base_recycler.visibility = View.GONE
            }
        }
    }

    private fun showPlaceholder() {
        tv_placeholder.visibility = View.VISIBLE
        Handler().postDelayed({
            tv_placeholder.visibility = View.GONE
        }, 1000L)
    }

    override fun errorUserListener(errorModel: ErrorModel?) {
        hideProgress()
        showToast(errorModel?.errorMessageText.toString())
    }

    private fun showProgress() {
        loadingScreenProgress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        loadingScreenProgress.visibility = View.GONE
    }
}