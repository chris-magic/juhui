package com.juhui.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.juhui.MyActivity;
import com.juhui.R;

/**
 * 左边菜单栏
 *
 * @author beanu
 */

public class LeftMenuFragment extends Fragment implements AdapterView.OnItemClickListener {


    ListView left_list;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_left, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        left_list = ((ListView) view.findViewById(R.id.left_list));

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] names = getResources().getStringArray(R.array.menu_names);
        int[] drawables = {R.drawable.left_menu_coupon, R.drawable.left_menu_ecard, R.drawable.left_menu_qr};
        MenuAdapter adapter = new MenuAdapter(names, drawables);
        left_list.setAdapter(adapter);
        // switchCategory(0);
        left_list.setOnItemClickListener(this);
        left_list.setItemChecked(0, true);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switchCategory(i);
    }

    public void switchCategory(int position) {

        switch (position) {
            case 0:
                ((MyActivity) getActivity()).showFragment(MyActivity.Fragments.coupons);
                break;
            case 1:
                ((MyActivity) getActivity()).showFragment(MyActivity.Fragments.ecard);
                break;
            case 2:
                // ((MainActivity) getActivity()).showFragment(Fragments.mycard);
                ((MyActivity) getActivity()).showFragment(MyActivity.Fragments.share);
                break;
            case 3:
                // ((MainActivity) getActivity()).showFragment(Fragments.freshNews);
                break;
        }
        // drawButtonsBackground(position);

    }


    private class MenuAdapter extends BaseAdapter {

        String[] names;
        int[] drawables;
        LayoutInflater mlinflater;

        public MenuAdapter(String[] names, int[] drawables) {
            this.names = names;
            this.mlinflater = LayoutInflater.from(getActivity());
            this.drawables = drawables;
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mlinflater.inflate(R.layout.biz_navigation_item_layout, null);
            TextView text = (TextView) view.findViewById(R.id.biz_navi_icon);
            text.setText(names[position]);
            text.setCompoundDrawablesWithIntrinsicBounds(drawables[position], 0, 0, 0);
            return view;
        }
    }


}
