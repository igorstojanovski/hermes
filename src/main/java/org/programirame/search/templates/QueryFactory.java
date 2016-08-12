package org.programirame.search.templates;

/**
 * Created by igorce on 12.08.16..
 */
public class QueryFactory {

    public static String getQuery(String searchString) {
        String filterSubQuery;

        if (searchString.isEmpty()) {
            filterSubQuery =
                    "    \"query\": {\n" +
                            "      \"match_all\": {}\n" +
                            "    }\n";
        } else {
            filterSubQuery =
                    "    \"query\": {\n" +
                            "      \"multi_match\": {\n" +
                            "        \"query\": \"" + searchString + "\",\n" +
                            "        \"type\": \"phrase_prefix\",\n" +
                            "        \"fields\": [\n" +
                            "          \"name\",\n" +
                            "          \"taxNumber\",\n" +
                            "          \"uid\",\n" +
                            "          \"externalId\",\n" +
                            "          \"invoice.invoiceId\"\n" +
                            "        ]\n" +
                            "      }\n" +
                            "    }\n";
        }

        return filterSubQuery;
    }
}
