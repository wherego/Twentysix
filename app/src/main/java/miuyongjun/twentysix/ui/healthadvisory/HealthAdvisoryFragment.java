package miuyongjun.twentysix.ui.healthadvisory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import miuyongjun.twentysix.R;

/**
 * Created by miuyongjun on 16/4/29.
 */
public class HealthAdvisoryFragment extends Fragment {

    public HealthAdvisoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_health, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
