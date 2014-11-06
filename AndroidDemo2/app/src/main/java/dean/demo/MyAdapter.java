package dean.demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by temp on 10/20/14.
 */
public class MyAdapter
    extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{

    private List<Actor> actors;

    private Context mContext;

    public MyAdapter( Context context , List<Actor> actors)
    {
        this.mContext = context;
        this.actors = actors;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i )
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i )
    {
        Actor p = actors.get(i);
        viewHolder.mContext = mContext;
        viewHolder.mTextView.setText(p.name);
        viewHolder.mImageView.setImageDrawable(mContext.getDrawable(p.getImageResourceId(mContext)));
    }

    @Override
    public int getItemCount()
    {
        return actors == null ? 0 : actors.size();
    }

    public static class ViewHolder
        extends RecyclerView.ViewHolder
    {
        public TextView mTextView;

        public ImageView mImageView;

        public Context mContext;

        public ViewHolder( View v )
        {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.name);
            mImageView = (ImageView) v.findViewById(R.id.pic);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                ((MyActivity)mContext).startActivity(v, getPosition());
                }
            });
        }
    }
}
