package org.programirame.search.templates;


import java.util.HashMap;


public class QueryFilter {
    private String name;
    private HashMap<String, Object> arguments;

    public QueryFilter(String name, HashMap<String, Object> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Object> getArguments() {
        return arguments;
    }

    public void setArguments(HashMap<String, Object> arguments) {
        this.arguments = arguments;
    }
}
