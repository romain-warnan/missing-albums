package fr.plaisance.arn.bean;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class YearAdapter extends XmlAdapter<String, Integer> {

    @Override
    public Integer unmarshal(String string) throws Exception {
        return Integer.valueOf(string.substring(0, 4));
    }

    @Override
    public String marshal(Integer year) throws Exception {
        return String.valueOf(year);
    }
}
