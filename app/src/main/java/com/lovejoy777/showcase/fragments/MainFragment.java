package com.lovejoy777.showcase.fragments;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lovejoy777.showcase.R;

public class MainFragment extends AbsBackButtonFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_main, null);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));

        CardView card1 = (CardView) root.findViewById(R.id.CardView_freethemes1);
        CardView card2 = (CardView) root.findViewById(R.id.CardView_paidthemes2);


       final NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);


        // CARD 1
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = new Bundle();
                data.putString("type", "Free");
                navigationView.getMenu().getItem(1).setChecked(true);

                Fragment fragment = new LayerListFragment();
                fragment.setArguments(data);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();

            }
        }); // end card1

        // CARD 2
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = new Bundle();
                data.putString("type", "Paid");
                navigationView.getMenu().getItem(2).setChecked(true);

                Fragment fragment = new LayerListFragment();
                fragment.setArguments(data);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main, fragment).commit();

            }
        }); // end card2

        return root;
    }

}
