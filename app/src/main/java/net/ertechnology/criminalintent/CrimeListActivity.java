package net.ertechnology.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by fulano on 7/24/14.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
