package com.lovejoy777.showcase.fragments;

import android.support.v4.app.Fragment;

public abstract class AbsBackButtonFragment extends Fragment {

    //If false - activity ignore back button
    public boolean onBackButton() {
        return true;
    }

}
