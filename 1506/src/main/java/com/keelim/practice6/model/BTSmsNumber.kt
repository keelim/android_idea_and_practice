package com.keelim.practice6.model

// 반장님이 넣었던 기능 (아두이노 기계 조립해서 심박수 체크한뒤 문자 보냄)
// 근데 공모전에 넣을땐 해당 기능 지웠음
class BTSmsNumber(
    var userID: String,
    var smsNum1: String,
    var numsName: String,
    smsText: String
) {

    var smsText: String? = null

    init {
        smsNum1 = smsText
    }
}