package com.lovejoy777.showcase;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;

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

    public void testFirstTest() {
        //Wait for first dialog to close (we can't capture opening)
        assertTrue(solo.waitForDialogToClose());

        solo.clickOnMenuItem("Refresh Showcase Layers");

        assertTrue(solo.waitForDialogToOpen());

        //Make sure it's correct dialog (͡°͜ʖ͡°)
        assertTrue(solo.searchText("Downloading"));

        assertTrue(solo.waitForDialogToClose());


    }

    public void testGoToLayersList() {

        assertTrue(solo.waitForDialogToClose());

        openCompatNavigationDrawer();

        solo.clickOnText(getActivity().getApplicationContext().getString(R.string.freecard1header));

        //Robotium doesn't like quick dialogs - waitToOpen + waitToClose doesn't work
        solo.waitForDialogToClose();
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