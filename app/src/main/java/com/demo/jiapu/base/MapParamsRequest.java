package com.demo.jiapu.base;

import java.util.Map;
import java.util.TreeMap;

/**
 * CaoPengFei
 * 2018/11/29
 *
 * @ClassName: MapParamsRequest
 * @Description:
 */

public abstract class MapParamsRequest {

    protected TreeMap<String, Object> params;

    public MapParamsRequest() {
        this.params = new TreeMap<>();
    }

    public Map<String, Object> params() {
        params.clear();
        putParams();
        return params;
    }

    protected abstract void putParams();
}
