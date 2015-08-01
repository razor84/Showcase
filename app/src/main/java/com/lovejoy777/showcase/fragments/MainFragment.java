package com.lovejoy777.showcase.fragments;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.*;
import com.lovejoy777.showcase.MainActivity1;
import com.lovejoy777.showcase.R;
import com.lovejoy777.showcase.UpgradeJson;

public class MainFragment extends AbsBackButtonFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        ((NavigationView) getActivity().findViewById(R.id.nav_view)).getMenu().getItem(0).setChecked(true);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));

        CardView card1 = (CardView) root.findViewById(R.id.CardView_freethemes1);
        CardView card2 = (CardView) root.findViewById(R.id.CardView_paidthemes2);


        // CARD 1
        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = new Bundle();
                data.putString("type", "Free");

                AbsBackButtonFragment fragment = new LayerListFragment();

                ((MainActivity1) getActivity()).replaceFragment(fragment);

                fragment.setArguments(data);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main, fragment).addToBackStack(null).commit();

            }
        }); // end card1

        // CARD 2
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle data = new Bundle();
                data.putString("type", "Paid");

                AbsBackButtonFragment fragment = new LayerListFragment();

                ((MainActivity1) getActivity()).replaceFragment(fragment);

                fragment.setArguments(data);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main, fragment).addToBackStack(null).commit();

            }
        }); // end card2


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new UpgradeJson(MainFragment.this.getActivity(), true).execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu);
    }

}
