package gibran.com.br.movie_db_consumer.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import gibran.com.br.movie_db_consumer.R;

/**
 * Created by gibranlyra on 05/02/18.
 */

public abstract class BaseFragment<T extends BaseContractPresenter> extends Fragment implements BaseContractView<T> {
    protected abstract void reloadFragment();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    public void showShotError() {
        Snackbar.make(getActivity().findViewById(R.id.rootLayout), R.string.generic_error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.snackbar_try_again, v -> reloadFragment())
                .show();
    }
}
