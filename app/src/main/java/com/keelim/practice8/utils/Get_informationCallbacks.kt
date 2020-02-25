package com.keelim.practice8.utils

import com.keelim.practice8.model.SeoulCulturedetail
import com.keelim.practice8.model.SeoulEducationdetail
import com.keelim.practice8.model.SeoulInstitutiondetail
import com.keelim.practice8.model.SeoulSportdetail

interface Get_informationCallbacks {
    fun onError()
    fun onSuccess_culture(information: List<SeoulCulturedetail?>?)
    fun onSuccess_sport(information: List<SeoulSportdetail?>?)
    fun onSuccess_education(information: List<SeoulEducationdetail?>?)
    fun onSuccess_institution(information: List<SeoulInstitutiondetail?>?)
}