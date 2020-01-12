// IMyCounterService.aidl
package com.keelim.timechecker.interfaces;

interface IMyCounterService { //aidl 다시 알아볼 것

    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    int getCount();
}
