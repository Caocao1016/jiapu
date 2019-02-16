package com.yskj.daishuguan.base;

import com.vondear.rxtool.RxDeviceTool;
import com.yskj.daishuguan.util.MD5Utils;
import com.yskj.daishuguan.util.StringUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
        paramMap.put("merchantcode","yxTOxX");//商户编号
        paramMap.put("clienttype","android");
        paramMap.put("appid","20181019");
        paramMap.put("channelid","app");//应用市场编号
        paramMap.put("deviceid", RxDeviceTool.getDeviceIdIMEI(BaseApp.getContext()));
        paramMap.put("devicecode",RxDeviceTool.getBuildBrandModel().trim()); //操作系统编号
        paramMap.put("appversion",RxDeviceTool.getAppVersionName(BaseApp.getContext()));
    }

    public static Map<String,Object> getParams(Map<String, Object> businessParamMap) {
        businessParamMap.putAll(paramMap);
        //排序
        List<String> keys = new ArrayList<>(businessParamMap.keySet());
        Collections.sort(keys);
        //拼字符
        StringBuffer sb = new StringBuffer();
        sb.append("WDQB@2017uy760918LK0K2D");
        for (String key : keys) {
            if(key.equals("contacts"))
              continue;
            if (StringUtil.isEmpty(businessParamMap.get(key).toString())) {
                sb.append("");
            }else{
                if (key.equals("locgps")){
                    sb.append(key);
                    sb.append(businessParamMap.get(key).toString().replaceAll("%7c","|"));
                }else {
                    sb.append(key);
                    sb.append(businessParamMap.get(key));
                }

            }
        }
        sb.append("WDQB@2017uy760918LK0K2D");
        //加密
        String paramString = null;
        try {
            paramString = URLEncoder.encode(sb.toString().trim(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sign = MD5Utils.getMD5(paramString);
        businessParamMap.put("md5sign", sign);
        return  businessParamMap;
    }
}
