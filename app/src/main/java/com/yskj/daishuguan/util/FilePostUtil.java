package com.yskj.daishuguan.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * CaoPengFei
 * 2018/12/27
 *
 * @ClassName: FilePostUtil
 * @Description:
 */

public class FilePostUtil {

    /**
     * 将文件路径数组封装为{@link List<MultipartBody.Part>}
     *
     * @param key       对应请求正文中name的值。目前服务器给出的接口中，所有图片文件使用<br>
     *                  同一个name值，实际情况中有可能需要多个
     * @param filePaths 文件路径数组
     * @param imageType 文件类型
     */
    public static List<MultipartBody.Part> files2Parts(String key,
                                                       String[] filePaths, MediaType imageType) {
        List<MultipartBody.Part> parts = new ArrayList<>(filePaths.length);
        for (String filePath : filePaths) {
            File file = new File(filePath);
            // 根据类型及File对象创建RequestBody（okhttp的类）
            RequestBody requestBody = RequestBody.create(imageType, file);
            // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
            MultipartBody.Part part ;
            if (filePath.equals("filePathFront")){
                part = MultipartBody.Part.
                        createFormData("fileFront", file.getName(), requestBody);
            }else {
                part = MultipartBody.Part.
                        createFormData("fileBack", file.getName(), requestBody);
            }

            // 添加进集合
            parts.add(part);
        }
        return parts;
    }

    public static List<MultipartBody.Part> filesOneParts(String key,
                                                         File filePaths, MediaType imageType) {
        List<MultipartBody.Part> parts = new ArrayList<>(1);
        // 根据类型及File对象创建RequestBody（okhttp的类）
        RequestBody requestBody = RequestBody.create(imageType, filePaths);
        // 将RequestBody封装成MultipartBody.Part类型（同样是okhttp的）
        MultipartBody.Part part = MultipartBody.Part.
                createFormData(key, filePaths.getName(), requestBody);
        // 添加进集合
        parts.add(part);

        return parts;
    }

    /**
     * 其实也是将File封装成RequestBody，然后再封装成Part，<br>
     * 不同的是使用MultipartBody.Builder来构建MultipartBody
     *
     * @param key       同上
     * @param filePaths 同上
     * @param imageType 同上
     */
    public static MultipartBody filesToMultipartBody(String key,
                                                     String[] filePaths,
                                                     MediaType imageType) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (String filePath : filePaths) {
            File file = new File(filePath);
            RequestBody requestBody = RequestBody.create(imageType, file);
            if (filePath.equals("filePathFront")){
                builder.addFormDataPart("fileFront", file.getName(), requestBody);
            }else {
                builder.addFormDataPart("fileBack", file.getName(), requestBody);
            }

        }
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }


    public static  RequestBody getRequestBody(HashMap<String, String> hashMap) {
        StringBuffer data = new StringBuffer();
        if (hashMap != null && hashMap.size() > 0) {
            Iterator iter = hashMap.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                data.append(key).append("=").append(val).append("&");
            }
        }
        String jso = data.substring(0, data.length() - 1);
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/octet-stream; charset=utf-8"),jso);

        return requestBody;
    }


}
