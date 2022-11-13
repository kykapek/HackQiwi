package ru.teamview.hackqiwi.ui.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.fragment.findNavController
import ru.teamview.hackqiwi.HackQiwiApp
import ru.teamview.hackqiwi.R
import ru.teamview.hackqiwi.databinding.FragmentSmsBinding
import ru.teamview.hackqiwi.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var prefs: SharedPreferences? = null

    private lateinit var mBinding: FragmentSplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = requireContext().getSharedPreferences(
            resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSplashBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.motionLayout.startLayoutAnimation()
        mBinding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                //startActivity(Intent(this@MainActivity, SecondActivity::class.java))
                initLogic()
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) { }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) { }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) { }
        })


    }

    private fun initLogic() {
        if (HackQiwiApp.getInstance().getJwt() != "") {
            findNavController().navigate(R.id.mainFragment)
        } else {
            findNavController().navigate(R.id.authFragment)
        }
    }
}