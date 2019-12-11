package com.gitfit.android.ui.home.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.gitfit.android.R
import com.gitfit.android.databinding.FragmentProfileBinding
import com.gitfit.android.ui.base.BaseFragment
import com.gitfit.android.utils.navigateWithoutComingBack
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(), ProfileNavigator {

    private val profileViewModel: ProfileViewModel by viewModel()
    lateinit var alertDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel.setNavigator(this)

        profileViewModel.mIsLoading.observe(this, Observer {
            setLoading(it)
        })

        alertDialog = AlertDialog.Builder(context!!)
            .setItems(arrayOf("Sign Out")) { _, _ ->
                profileViewModel.signOut()
            }.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_profile, container, false
            )
        binding.viewModel = profileViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mLinesOfCodeEditText.addTextChangedListener { profileViewModel.update() }
        mCupsOfCoffeeEditText.addTextChangedListener { profileViewModel.update() }
    }

    override fun openAlertDialog() {
        val alertDialog = AlertDialog.Builder(context!!)
            .setItems(arrayOf("Sign Out")) { _, _ ->
                profileViewModel.signOut()
            }.create()

        alertDialog.show()
    }

    override fun navigateToLoginFragment() {
        findNavController().navigateWithoutComingBack(R.id.nav_graph_auth)
    }
}