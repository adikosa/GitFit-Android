package com.gitfit.android.ui.home.profile

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.gitfit.android.R
import com.gitfit.android.databinding.FragmentProfileBinding
import com.gitfit.android.ui.base.BaseFragment
import com.gitfit.android.utils.debug
import com.gitfit.android.utils.navigateWithoutComingBack
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment(), ProfileNavigator {

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileViewModel.setNavigator(this)

        profileViewModel.mIsLoading.observe(this, Observer {
            setLoading(it)
        })
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

        inflateMenu()
    }

    private fun inflateMenu() {
        toolbar.inflateMenu(R.menu.profile_menu)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.action_info -> profileViewModel.onActionInfoMenuClick()
                R.id.action_settings -> profileViewModel.onActionSettingsMenuClick()
                else -> debug("Wrong menu option selected")
            }

            true
        }
    }

    override fun openProfileAlertDialog() {
        val profileAlertDialog = AlertDialog.Builder(context!!)
            .setItems(arrayOf("Sign Out")) { _, _ ->
                profileViewModel.signOut()
            }.create()

        profileAlertDialog.show()
    }

    override fun openInfoAlertDialog() {
        val infoAlertDialog = AlertDialog.Builder(context!!)
            .setTitle("GitFit Authors")
            .setMessage("Patryk Marciszek-Kosieradzki\nAdrian Twarowski")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.cancel()
            }
            .create()

        infoAlertDialog.show()
    }

    override fun openSettings() {
        val settingsAlertDialog = AlertDialog.Builder(context!!)
            .setTitle("Not Implemented Yet")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.cancel()
            }
            .create()

        settingsAlertDialog.show()
    }

    override fun navigateToLoginFragment() {
        findNavController().navigateWithoutComingBack(R.id.nav_graph_auth)
    }
}