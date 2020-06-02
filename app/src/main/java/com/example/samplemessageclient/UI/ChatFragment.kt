package com.example.samplemessageclient.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.samplemessageclient.R
import com.example.samplemessageclient.data.MessengerRepository
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import kotlin.concurrent.fixedRateTimer
import kotlin.coroutines.CoroutineContext

class ChatFragment : Fragment(), CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main

    companion object {
        const val USERNAME = "username"
    }

    private lateinit var username: String
    private val repository = MessengerRepository()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username =
            arguments?.getString(USERNAME) ?: throw IllegalArgumentException("No username passed")
        val adapter = MessagesAdapter(username)
        messageList.adapter = adapter

        fixedRateTimer(daemon = true, initialDelay = 0, period = 1000) {
            launch {
                val lastMessages = repository.getLastMessages(adapter.lastMessageId).await()
                if (lastMessages?.isNotEmpty() == true) {
                    adapter.addMessages(lastMessages)
                }

            }
        }
        send.setOnClickListener {
            val messageText = inputMessage.text.toString()

            if(messageText.isNotBlank()){
                launch {
                    repository.postMessage(username,messageText).await()
                }
            }
        }
    }
}