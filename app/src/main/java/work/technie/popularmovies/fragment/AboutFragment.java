package work.technie.popularmovies.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import work.technie.popularmovies.R;

/**
 * Created by anupam on 14/12/16.
 */

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.about, container, false);
        TextView txtView = (TextView) rootView.findViewById(R.id.about_text);
        txtView.setMovementMethod(LinkMovementMethod.getInstance());
        return rootView;
    }
}
