package com.den.model.old;

/**
 * Created by Den on 23.10.2016.
 */
public class VkMeta {
    private String source;
    private int portion;
    private int cities;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getPortion() {
        return portion;
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public int getCities() {
        return cities;
    }

    public void setCities(int cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "VkMeta{" +
                "source='" + source + '\'' +
                ", portion=" + portion +
                ", cities=" + cities +
                '}';
    }
}
