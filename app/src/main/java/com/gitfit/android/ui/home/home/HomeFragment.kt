package com.gitfit.android.ui.home.home

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gitfit.android.R
import com.gitfit.android.databinding.FragmentHomeBinding
import com.gitfit.android.ui.base.BaseFragment
import com.gitfit.android.utils.navigateWithoutComingBack
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment(), HomeNavigator {

    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.setNavigator(this)

        homeViewModel.mIsLoading.observe(this, Observer {
            setLoading(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home, container, false
            )
        binding.viewModel = homeViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeViewModel.init()
        homeViewModel.progress.observe(this, Observer {
            val animator = ObjectAnimator.ofInt(mArcProgress, "progress", 0, it)
            animator.apply {
                interpolator = DecelerateInterpolator()
                target = mArcProgress
                duration = 500
            }.start()
        })
    }

    override fun openNewActivityDialog() {
        findNavController().navigate(R.id.action_navigation_home_to_navigation_add_activity)
    }

    override fun navigateToLoginFragment() {
        findNavController().navigateWithoutComingBack(R.id.nav_graph_auth)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        homeViewModel.stopLoading()
    }

    override fun onDetach() {
        super.onDetach()

        homeViewModel.stopLoading()
    }

    override fun onDestroy() {
        super.onDestroy()

        homeViewModel.stopLoading()
    }

    override fun onStop() {
        super.onStop()

        homeViewModel.stopLoading()
    }
}