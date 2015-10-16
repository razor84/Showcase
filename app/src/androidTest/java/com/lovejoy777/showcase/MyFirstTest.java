package com.lovejoy777.showcase;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.lovejoy777.showcase.activities.DetailActivity;
import com.robotium.solo.Solo;

public class MyFirstTest extends ActivityInstrumentationTestCase2<MainActivity1> {

    Solo solo;

    public MyFirstTest() {
        super(MainActivity1.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testGoToLayersList() {

        assertTrue(solo.waitForDialogToClose());

        openCompatNavigationDrawer();


        solo.clickOnText(getActivity().getString(R.string.freecard1header), 2);

        //Robotium doesn't like quick dialogs - waitToOpen + waitToClose doesn't work
        solo.waitForDialogToClose();

        final RecyclerView recyclerView = (RecyclerView) solo.getView(R.id.cardList);

        RecyclerView.Adapter adapter = recyclerView.getAdapter();

        int a = 0;
        View view;

        assertTrue(recyclerView.getAdapter().getItemCount() > 0);


        while ((view = recyclerView.getChildAt(a)) != null) {
            solo.clickOnView(view);
            solo.waitForActivity(DetailActivity.class);
            solo.goBack();
            a++;
        }

        for (int i = 0; i < adapter.getItemCount(); i++) {

            final int finalI = i;
            getInstrumentation().runOnMainSync(new Runnable() {
                @Override
                public void run() {
                    recyclerView.scrollToPosition(finalI);
                }
            });

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            solo.clickOnView(recyclerView.getChildAt(getLastChild(recyclerView)));
            solo.waitForActivity(DetailActivity.class);
            solo.goBack();

        }


    }

    public int getLastChild(RecyclerView recyclerView) {

        int a = 0;

        while (recyclerView.getChildAt(a + 1) != null) {
            a++;
        }

        return a;
    }

    public void openCompatNavigationDrawer() {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                ((DrawerLayout) solo.getView(R.id.drawer_layout))
                        .openDrawer(GravityCompat.START);
            }
        });
    }

}