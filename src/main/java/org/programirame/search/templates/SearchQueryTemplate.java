package org.programirame.search.templates;


import java.util.List;


public abstract class SearchQueryTemplate {

    public static String buildFiltered(String searchString, List<QueryFilter> filters) {
        String filtered = "{\n ";

        filtered = filtered + "\"filtered\": {\n";
        filtered = filtered + QueryFactory.getQuery(searchString);

        if (filters.size() > 0)
            filtered = filtered +
                    ",\n" +
                    "    \"filter\": {\n" +
                    "      \"and\": [\n" +
                    getFiltersQuery(filters) +
                    "      ]\n" +
                    "    }\n" +
                    "  }\n";

        filtered = filtered + "  }\n";
        return filtered;
    }

    private static String getFiltersQuery(List<QueryFilter> filters) {

        String filteredQuery = "";

        for (QueryFilter filter : filters) {
            if(!filteredQuery.isEmpty() ) {
                filteredQuery = filteredQuery + ",\n";
            }
            filteredQuery = filteredQuery + FilterFactory.getFilterSubQuery(filter);
        }

        return filteredQuery;
    }


}
