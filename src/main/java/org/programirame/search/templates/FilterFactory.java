package org.programirame.search.templates;


public class FilterFactory {

    public static String getFilterSubQuery(QueryFilter queryFilter) {
        String filterSubQuery = "";

        if ("DueDateIn".equals(queryFilter.getName())) {
            filterSubQuery =
                    "        {\n" +
                            "          \"term\": {\n" +
                            "            \"invoice.invoiceDueDate\": " + queryFilter.getArguments().get("date") + "\n" +
                            "          }\n" +
                            "        }\n";
        } else if ("City".equals(queryFilter.getName())) {
            filterSubQuery =
                    "        {\n" +
                            "          \"term\": {\n" +
                            "            \"address.city\": \"" +
                            queryFilter.getArguments().get("city").toString().toLowerCase() +
                            "\"\n" +
                            "          }\n" +
                            "        }\n";
        }

        return filterSubQuery;
    }

}
