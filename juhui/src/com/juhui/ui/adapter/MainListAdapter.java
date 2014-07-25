package com.juhui.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.beanu.arad.Arad;
import com.beanu.arad.support.loadmore.ILoadMoreAdapter;
import com.juhui.R;
import com.juhui.entity.ECardItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 电子会员卡信息列表Adapter
 *
 * @author beanu
 */
public class MainListAdapter extends BaseAdapter {

    private List<ECardItem> list;
    private LayoutInflater mlinflater;
    private Context context;
    private ILoadMoreAdapter listener;

    private static final int VIEW_TYPE_ACTIVITY = 0;
    private static final int VIEW_TYPE_USED = 1;
    private static final int VIEW_TYPE_LOADING = 2;

    private class ViewHolder {
        public ImageView img;
        public TextView title;
        public TextView address;
        public TextView score;
        public TextView useamount;
        public TextView discount;
        public TextView distance;
    }

    public MainListAdapter(Context context, List<ECardItem> data, ILoadMoreAdapter listener) {
        this.mlinflater = LayoutInflater.from(context);
        this.list = data;
        this.listener = listener;
        this.context = context;
    }

    public void setData(List<ECardItem> data) {
        if (data != null)
            this.list = data;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) == VIEW_TYPE_ACTIVITY || getItemViewType(position) == VIEW_TYPE_USED;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        if (list == null)
            return 0;
        if (listener == null)
            return list.size();
        else {
            if (list.size() == 0) {
                return 0;
            }

            return list.size() + (
                    // show the status list row if...
                    (listener.hasMoreResults() // ...or there's another
                            // page
                            || listener.hasError()) // ...or there's an error
                            ? 1
                            : 0);

        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position >= list.size()) {
            return VIEW_TYPE_LOADING;
        } else {
            if (list.get(position).getIsHisCard() == 0) {
                return VIEW_TYPE_ACTIVITY;
            } else {
                return VIEW_TYPE_USED;
            }
        }
//        return (position >= list.size()) ? VIEW_TYPE_LOADING : VIEW_TYPE_ACTIVITY;
    }

    @Override
    public Object getItem(int position) {
        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY || getItemViewType(position) == VIEW_TYPE_USED) ? list.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY || getItemViewType(position) == VIEW_TYPE_USED) ? list.get(position).hashCode() : -1;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        if (getItemViewType(position) == VIEW_TYPE_LOADING) {

            if (view == null) {
                view = mlinflater.inflate(R.layout.list_item_stream_status, viewGroup, false);
            }

            if (listener.hasError()) {
                view.findViewById(android.R.id.progress).setVisibility(View.GONE);
                ((TextView) view.findViewById(android.R.id.text1)).setText("发生错误了");
            } else {
                view.findViewById(android.R.id.progress).setVisibility(View.VISIBLE);
                ((TextView) view.findViewById(android.R.id.text1)).setText("正在加载更多");
            }

            return view;

        } else if (getItemViewType(position) == VIEW_TYPE_ACTIVITY) {
            final ECardItem topic = list.get(position);
            if (view == null) {
                view = mlinflater.inflate(R.layout.ecard_list_item, null);
                ViewHolder vh = new ViewHolder();
                vh.img = (ImageView) view.findViewById(R.id.ecard_list_item_img);
                view.setTag(vh);
            }

            ViewHolder holder = (ViewHolder) view.getTag();

            Arad.imageLoader.load(topic.getItemImageUrl()).placeholder(R.drawable.default_img).into(holder.img);
//            holder.title.setText(topic.getItemTitle());

        } else if (getItemViewType(position) == VIEW_TYPE_USED) {
            final ECardItem topic = list.get(position);
            if (view == null) {
                view = mlinflater.inflate(R.layout.ecard_list2_item, null);
                ViewHolder vh = new ViewHolder();
                vh.img = (ImageView) view.findViewById(R.id.ecard_list2_item_img);
                view.setTag(vh);
            }

            ViewHolder holder = (ViewHolder) view.getTag();
            Arad.imageLoader.load(topic.getItemImageUrl()).placeholder(R.drawable.default_img).into(holder.img);
            //            holder.distance.setText(UIUtil.showDistance(topic.getDistance()));
        }

        return view;
    }
}
