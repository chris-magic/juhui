package com.beanu.arad.support;

import com.beanu.arad.Arad;
import com.beanu.arad.error.AradException;
import com.beanu.arad.http.INetResult;
import com.beanu.arad.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.IOException;
import java.util.Map;

public abstract class IDao {

    protected INetResult mResult;

    public IDao(INetResult activity) {
        this.mResult = activity;
    }

    /**
     * 得到结果后，对结果处理逻辑
     *
     * @param result
     * @param requestCode
     * @throws java.io.IOException
     */
    public abstract void onRequestSuccess(JsonNode result, int requestCode) throws IOException;


    /**
     * get请求网络，本方法提供结果的分发
     *
     * @param params
     */
    protected void getRequest(String url, Map<String, String> params) {
        _getRequest(url, params, 0);
    }

    /**
     * get请求网络，带有请求编号
     *
     * @param params
     */
    protected void getRequestForCode(String url, Map<String, String> params, int requestCode) {
        _getRequest(url, params, requestCode);
    }


    private void _getRequest(String url, Map<String, String> params, final int requestCode) {

        RequestParams ajaxParams = new RequestParams(params);
        Arad.http.get(url, ajaxParams, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                try {
                    if (Arad.app.config.httpConfig != null) {
                        JsonNode node = Arad.app.config.httpConfig.handleResult(responseBody);
                        onRequestSuccess(node, requestCode);
                    }
                    mResult.onSuccess(requestCode);
                } catch (AradException e) {
                    mResult.onFaild(e.getError_code(), e.getMessage());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                mResult.onFaild("mResult" + statusCode, responseBody);
            }
        });

    }

    /**
     * POST 请求
     *
     * @param requestCode 自定义这是第几个post请求，用于结果的区分
     */
    public void postRequest(String url, RequestParams ajaxParams, final int requestCode) {

        Arad.http.post(url, ajaxParams, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                try {
                    if (Arad.app.config.httpConfig != null) {
                        JsonNode node = Arad.app.config.httpConfig.handleResult(responseBody);
                        onRequestSuccess(node, requestCode);
                    }
                    mResult.onSuccess(requestCode);
                } catch (AradException e) {
                    mResult.onFaild(e.getError_code(), e.getMessage());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                mResult.onFaild("mResult" + statusCode, responseBody);
            }
        });

    }


}
