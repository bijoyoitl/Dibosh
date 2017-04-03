package com.optimalbd.dibosh.Ulility;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by engrb on 28-Jan-17.
 */

public class BirthDayYearCalculate {

    public static int getAge(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if(dateOfBirth!= null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if(born.after(now)) {
//                throw new IllegalArgumentException("Can't be born in the future");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            age+=1;
            if(now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR))  {
                age-=1;
            }
        }
        return age;
    }
}
