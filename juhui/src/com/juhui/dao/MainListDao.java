package com.juhui.dao;

import com.beanu.arad.http.INetResult;
import com.beanu.arad.support.IDao;
import com.fasterxml.jackson.databind.JsonNode;
import com.juhui.entity.ECardItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainListDao extends IDao {

    private Map<String, String> param;

    private List<ECardItem> mCouponList;
    private final int PageSize = 20;

    private boolean isNext = true;

    public boolean isNext() {
        if (mCouponList.size() < 5)
            return false;
        return isNext;
    }


    public MainListDao(INetResult activity) {
        super(activity);
        param = new HashMap<String, String>();
        param.put("op", "sss");
        param.put("cityID", "1");
        param.put("radius", "5000");
        mCouponList = new ArrayList<ECardItem>();
    }

    /**
     * 下一页
     *
     * @param
     */
    public void nextPage() {

    }

    public void pulltorefresh() {

    }

    public List<ECardItem> getECardList() {
        //TODO TEST
        if (mCouponList.size() == 0) {
            for (int i = 0; i < 10; i++) {
                ECardItem item = new ECardItem();
                item.setItemImageUrl("http://b.hiphotos.baidu.com/image/w%3D230/sign=40cd7bd557fbb2fb342b5f117f4b2043/e850352ac65c10384a60bd4ab0119313b07e891f.jpg");
                mCouponList.add(item);
            }
        }


        return mCouponList;
    }


    @Override
    public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {

    }
}
