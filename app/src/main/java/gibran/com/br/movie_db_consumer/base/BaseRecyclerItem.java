package gibran.com.br.movie_db_consumer.base;

import android.support.v7.widget.RecyclerView;

import com.mikepenz.fastadapter.items.AbstractItem;

/**
 * Created by gibranlyra on 05/02/18.
 */
public abstract class BaseRecyclerItem<T, item extends AbstractItem<?, ?>, viewHolder
        extends RecyclerView.ViewHolder> extends AbstractItem<item, viewHolder> {

    private T item;

    public BaseRecyclerItem(T item) {
        this.item = item;
    }

    public T getModel() {
        return item;
    }
}

