package com.petprog.android.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;
    private Map<UUID, Integer> mappingCrime = new HashMap<>();

    public static CrimeLab get(Context context) {

        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;

    }

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();

        // A fully loaded layer with 100 crimes
        for (int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            mappingCrime.put(crime.getId(), i);
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            crime.setRequiresPolice(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {

        if(mappingCrime.containsKey(id))
            return mCrimes.get(mappingCrime.get(id));
        return null;
    }

}
