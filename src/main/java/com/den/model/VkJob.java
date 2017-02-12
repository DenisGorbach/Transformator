package com.den.model;

/**
 * Created by Den on 13.11.2016.
 */
public class VkJob {
    private long idVkJob;
    private String nameVkJob;

    public long getIdVkJob() {
        return idVkJob;
    }

    public void setIdVkJob(long idVkJob) {
        this.idVkJob = idVkJob;
    }

    public String getNameVkJob() {
        return nameVkJob;
    }

    public void setNameVkJob(String nameVkJob) {
        this.nameVkJob = nameVkJob;
    }

    @Override
    public String toString() {
        return "VkJob{" +
                "idVkJob=" + idVkJob +
                ", nameVkJob='" + nameVkJob + '\'' +
                '}';
    }
}
