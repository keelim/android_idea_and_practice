package com.keelim.practice8.utils

import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse

interface ConversationCallbacks {
    fun test(response: MessageResponse?)
}