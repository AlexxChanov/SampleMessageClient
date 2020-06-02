package com.example.samplemessageclient.UI

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.samplemessageclient.R
import com.example.samplemessageclient.data.MessengerRepository
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RegisterFragment : Fragment(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.Main
    private val messengerRepository = MessengerRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            loader.visibility = View.GONE
        buttonRegister.setOnClickListener {

            val username = inputUserName.text.toString()
            val firstname = inputFirstName.text.toString()
            val lastname = inputLastName.text.toString()

            if (username.isNotBlank() && firstname.isNotBlank() && lastname.isNotBlank()) {
                launch {
                    buttonRegister.visibility = View.GONE
                    loader.visibility = View.VISIBLE
                    messengerRepository.register(username, firstname, lastname)
                        .await()
                        ?.also {

                            fragmentManager?.beginTransaction()
                                ?.replace(R.id.navHost, ChatFragment().also {
                                    it.arguments = Bundle().also {
                                        it.putString(ChatFragment.USERNAME, username);
                                    }
                                })
                                ?.addToBackStack(null)
                                ?.commit()
                        }
                        ?.run {
                            Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show()
                        }
                }
                buttonRegister.visibility = View.VISIBLE
                loader.visibility = View.GONE
            } else {
                (AnimatorInflater.loadAnimator(context,R.animator.error_animation) as AnimatorSet).apply {
                    setTarget(buttonRegister)
                    start()
                }
                (AnimatorInflater.loadAnimator(context,R.animator.color_animation) as AnimatorSet).apply {
                    setTarget(registerLayout)
                    start()
                }


//                ObjectAnimator.ofFloat(buttonRegister, "translationY", 0f, 20f, -20f, 0f)
//                    .apply {
//                        duration = 500
//                        start()
//                    }
//                ObjectAnimator.ofArgb(registerLayout,"backgroundColor", Color.WHITE,Color.RED,Color.WHITE).apply {
//                    duration = 1000
//                start()
//                }
//                AnimatorSet().apply {
//                    playSequentially(
//                        ObjectAnimator.ofFloat(buttonRegister, "translationY", 0f, 20f, -20f, 0f)
//                            .apply {
//                                duration = 500
//                            }, AnimatorSet().apply {
//                            playTogether(ObjectAnimator.ofFloat(
//                                buttonRegister,
//                                "scaleX",
//                                1f,
//                                1.5f,
//                                1f
//                            ).apply {
//                                duration = 500
//                            },
//                                ObjectAnimator.ofFloat(buttonRegister, "scaleY", 1f, 1.5f, 1f)
//                                    .apply {
//                                        duration = 500
//                                    })
//
//                        }


                }


            }
        }
    }

