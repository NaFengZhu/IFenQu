package com.ifenqu.app.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public class SysOrderAgentModel implements Serializable {
    private long total;
    private List<SysOrderModel> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<SysOrderModel> getRows() {
        return rows;
    }

    public void setRows(List<SysOrderModel> rows) {
        this.rows = rows;
    }
}
