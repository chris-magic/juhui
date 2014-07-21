package com.juhui;

import com.beanu.arad.AradApplication;
import com.beanu.arad.AradApplicationConfig;

/**
 * 全局入口
 * Created by beanu on 14-7-21.
 */
public class JhApplication extends AradApplication {

    @Override
    protected AradApplicationConfig appConfig() {
        return new AradApplicationConfig();
    }
}
