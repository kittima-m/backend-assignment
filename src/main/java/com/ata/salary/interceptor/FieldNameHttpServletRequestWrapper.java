package com.ata.salary.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class FieldNameHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> modifiedParams;

    public FieldNameHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        modifiedParams = new HashMap<>(request.getParameterMap());
    }

    @Override
    public String getParameter(String name) {
        String[] values = modifiedParams.get(name);
        return (values != null && values.length > 0) ? values[0] : null;
    }

    @Override
    public String[] getParameterValues(String name) {
        return modifiedParams.get(name);
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(modifiedParams.keySet());
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return modifiedParams;
    }

    public void addParameter(String name, String[] values) {
        modifiedParams.put(name, values);
    }

}
