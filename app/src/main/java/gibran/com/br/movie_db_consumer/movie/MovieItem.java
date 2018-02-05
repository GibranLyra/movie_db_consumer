package gibran.com.br.movie_db_consumer.movie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import gibran.com.br.movie_db_consumer.AppContext;
import gibran.com.br.movie_db_consumer.R;
import gibran.com.br.movie_db_consumer.base.BaseRecyclerItem;
import gibran.com.br.moviedbservice.model.Movie;

/**
 * Created by gibranlyra on 24/08/17.
 */

public class MovieItem extends BaseRecyclerItem<Movie, MovieItem, MovieItem.ViewHolder> {

    public MovieItem(Movie item) {
        super(item);
    }

    //The unique ID for this type of item
    @Override
    public int getType() {
        return R.id.movie_item_id;
    }

    //The layout to be used for this type of item
    @Override
    public int getLayoutRes() {
        return R.layout.movie_item;
    }

    //The logic to bind your data to the view
    @Override
    public void bindView(ViewHolder viewHolder, List<Object> payloads) {
        //call super so the selection is already handled for you
        super.bindView(viewHolder, payloads);

        //bind our data
        Context context = viewHolder.itemView.getContext();
        Movie movie = getModel();
        viewHolder.titleView.setText(movie.getTitle());
        // TODO: 05/02/18 implement images
        if (!TextUtils.isEmpty(movie.getPosterPath())) {
            String imageUrl = String.format("%s%s%s",
                    AppContext.getInstance().getConfiguration().getImages().getBaseUrl(),
                    AppContext.getInstance().getConfiguration().getImages().getPosterSizes().get(0),
                    movie.getPosterPath());
            Glide.with(context)
                    .setDefaultRequestOptions(AppContext.getInstance().getGlideRequestOptions())
                    .load(imageUrl)
                    .into(viewHolder.imageView);
        } else {
            Glide.with(context)
                    .load(R.drawable.placeholder)
                    .into(viewHolder.imageView);
        }
    }

    //reset the view here for better performance
    @Override
    public void unbindView(ViewHolder holder) {
        super.unbindView(holder);
        holder.imageView.setImageDrawable(null);
        holder.titleView.setText(null);
        holder.viewCountView.setText(null);
        holder.createdAtView.setText(null);
    }

    //Init the viewHolder for this Item
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    //The viewHolder used for this item. This viewHolder is always reused by the RecyclerView so scrolling is blazing fast
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_item_image)
        ImageView imageView;
        @BindView(R.id.movie_item_title)
        TextView titleView;
        @BindView(R.id.movie_item_view_count)
        TextView viewCountView;
        @BindView(R.id.movie_image_created_at)
        TextView createdAtView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
