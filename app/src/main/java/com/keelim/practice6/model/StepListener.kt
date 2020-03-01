package com.keelim.practice6.model

interface StepListener {
    //This interface will listen to alerts about steps being detected (이 인터페이스는 걸음 수가 감지 됐다는 것을 들을 것)
    fun step(timeNs: Long)
}