package com.den.model.old;

import java.util.List;

public class VkResult {

    private VkMeta meta;
    private List<VkResponse> response;

    public VkMeta getMeta() {
        return meta;
    }

    public void setMeta(VkMeta meta) {
        this.meta = meta;
    }

    public List<VkResponse> getResponse() {
        return response;
    }

    public void setResponse(List<VkResponse> response) {
        this.response = response;
    }
}
