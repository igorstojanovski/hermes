package org.programirame.repositories;


import org.programirame.model.Client;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ClientRepository extends ElasticsearchRepository<Client, String> {

    @Query("{\n" +
            "  \"multi_match\": {\n" +
            "    \"query\": \"?0\",\n" +
            "    \"type\": \"phrase_prefix\",\n" +
            "    \"fields\": [\n" +
            "      \"name\",\n" +
            "      \"taxNumber\",\n" +
            "      \"uid\",\n" +
            "      \"externalId\",\n" +
            "      \"invoice.invoiceId\"\n" +
            "    ]\n" +
            "  }\n" +
            "}")
    List<Client> searchClient(String name);



    @Query("{\n" +
            "  \"filtered\": {\n" +
            "    \"query\": {\n" +
            "      \"multi_match\": {\n" +
            "        \"query\": \"?0\",\n" +
            "        \"type\": \"phrase_prefix\",\n" +
            "        \"fields\": [\n" +
            "          \"name\",\n" +
            "          \"taxNumber\",\n" +
            "          \"uid\",\n" +
            "          \"externalId\",\n" +
            "          \"invoice.invoiceId\"\n" +
            "        ]\n" +
            "      }\n" +
            "    },\n" +
            "    \"filter\": {\n" +
            "      \"and\": [\n" +
            "        {\n" +
            "          \"term\": {\n" +
            "            \"invoice.invoiceDueDate\": \"?1\"\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}")
    List<Client> searchByInvoiceDueDate(String query, Long date);
}
