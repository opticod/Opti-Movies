package work.technie.popularmovies.utils;

import android.view.View;

/**
 * Created by anupam on 19/12/16.
 */

public class ViewHolderUtil {

    /**
     * to handle click listener
     */
    public interface SetOnClickListener {
        void onItemClick(int position, View itemView);
    }
}

