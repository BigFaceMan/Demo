package ssp.miniSpring.web;

import java.util.HashMap;
import java.util.Map;

public class ModelAndlView {
    private String view;


    private Map<String, String> Context = new HashMap<>();
    public ModelAndlView() {
    }
    public ModelAndlView(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Map<String, String> getContext() {
        return Context;
    }
}
