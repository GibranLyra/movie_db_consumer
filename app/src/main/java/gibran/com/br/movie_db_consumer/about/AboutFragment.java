package gibran.com.br.movie_db_consumer.about;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import gibran.com.br.movie_db_consumer.R;

/**
 * Created by gibranlyra on 06/02/18 for movie_db_consumer.
 */

public class AboutFragment extends Fragment {

    private static final String ABOUT_IMAGE_URL = "https://avatars0.githubusercontent.com/u/5739609?s=460&v=4";

    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(this)
                .load(ABOUT_IMAGE_URL)
                .into((ImageView) view.findViewById(R.id.aboutImage));
    }
}
