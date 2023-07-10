package com.api.viewBean;

public class TotalThroughputBean {
    private String port;
    private int import_weight;
    private int export_weight;
    private int total_weight;

    public TotalThroughputBean() {
    }

    public TotalThroughputBean(String port, int import_weight, int export_weight, int total_weight) {
        this.port = port;
        this.import_weight = import_weight;
        this.export_weight = export_weight;
        this.total_weight = total_weight;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getImport_weight() {
        return import_weight;
    }

    public void setImport_weight(int import_weight) {
        this.import_weight = import_weight;
    }

    public int getExport_weight() {
        return export_weight;
    }

    public void setExport_weight(int export_weight) {
        this.export_weight = export_weight;
    }

    public int getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(int total_weight) {
        this.total_weight = total_weight;
    }
}
