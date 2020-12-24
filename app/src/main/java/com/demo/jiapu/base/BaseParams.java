package com.demo.jiapu.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: BaseParams
 * @Description:
 */

public class BaseParams {

    private static Map<String, String> paramMap = new HashMap<>();
    static{

    }
    public static Map<String,Object> getParams(Map<String, Object> businessParamMap) {

        businessParamMap.putAll(paramMap);
        //排序
        List<String> keys = new ArrayList<>(businessParamMap.keySet());
        Collections.sort(keys);
        return  businessParamMap;
    }
}
