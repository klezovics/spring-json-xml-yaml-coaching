package com.klezovich.springjsoncoaching.json.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.HashMap;
import java.util.Map;

public class ExtendableBean {
    public String name;
    private Map<String, String> properties = new HashMap<>();

    public ExtendableBean(String beanName) {
        this.name = beanName;
    }

    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }
}