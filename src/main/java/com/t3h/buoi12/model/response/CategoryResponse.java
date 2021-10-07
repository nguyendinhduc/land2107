package com.t3h.buoi12.model.response;

import java.util.List;

public class CategoryResponse {
    private String categoryName;
    private List<ContentCovid> contentCovids;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ContentCovid> getContentCovids() {
        return contentCovids;
    }

    public void setContentCovids(List<ContentCovid> contentCovids) {
        this.contentCovids = contentCovids;
    }
}
