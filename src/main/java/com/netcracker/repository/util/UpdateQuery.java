package com.netcracker.repository.util;


public class UpdateQuery {
    private String query;
    private Object[] values;

    public UpdateQuery(String query, Object[] values) {
        this.query = query;
        this.values = values;
    }

    public String getQuery() {
        return query;
    }

    public Object[] getValues() {
        return values;
    }
}
