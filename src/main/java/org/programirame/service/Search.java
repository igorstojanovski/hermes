package org.programirame.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WrapperQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.programirame.model.Client;
import org.programirame.model.search.AggreagateBucket;
import org.programirame.model.search.ClientSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;


@Service
public class Search {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private NodeClient client;

    public ClientSearchResult search(String queryString) {
        WrapperQueryBuilder query = QueryBuilders.wrapperQuery(queryString);

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query)
                .addAggregation(terms("cities").field("client.address.city")).build();

        final ClientSearchResult clientSearchResult = elasticsearchTemplate
                .query(searchQuery, new ResultsExtractor<ClientSearchResult>() {
                    @Override
                    public ClientSearchResult extract(SearchResponse response) {
                        ObjectMapper mapper = new ObjectMapper();
                        SearchHit[] hits = response.getHits().getHits();
                        ClientSearchResult clientSearchResult = new ClientSearchResult();
                        List<Client> clients = new ArrayList<>();

                        for(SearchHit hit:hits) {
                            try {
                                clients.add(mapper.readValue(hit.getSourceAsString(), Client.class));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        List<AggreagateBucket> aggregateBuckets = extractAggregateInfo(response.getAggregations());

                        clientSearchResult.setBuckets(aggregateBuckets);
                        clientSearchResult.setClients(clients);

                        return clientSearchResult;
                    }

                    private List<AggreagateBucket> extractAggregateInfo(Aggregations aggregations) {
                        List<AggreagateBucket> aggregates = new ArrayList<>();

                        Terms terms = (Terms) aggregations.asMap().get("cities");

                        aggregates.addAll(
                                terms.getBuckets()
                                        .stream()
                                        .map(bucket -> new AggreagateBucket(bucket.getKey(), bucket.getDocCount()))
                                        .collect(Collectors.toList())
                        );

                        return aggregates;
                    }
                });


        return clientSearchResult;
    }

    public ClientSearchResult searchClient(String searchString) {

//        QueryBuilder builder = nestedQuery("invoice", boolQuery().must(termQuery("invoice.clientId", searchString)));
        WrapperQueryBuilder query = QueryBuilders.wrapperQuery("{\n" +
                "  \"multi_match\": {\n" +
                "    \"query\": \""+searchString+"\",\n" +
                "    \"type\": \"phrase_prefix\",\n" +
                "    \"fields\": [\n" +
                "      \"name\",\n" +
                "      \"taxNumber\",\n" +
                "      \"uid\",\n" +
                "      \"externalId\",\n" +
                "          \"address.city\",\n" +
                "      \"invoice.invoiceId\"\n" +
                "    ]\n" +
                "  }\n" +
                "}");

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query)
                .addAggregation(terms("cities").field("client.address.city")).build();

        final ClientSearchResult clientSearchResult = elasticsearchTemplate
                .query(searchQuery, new ResultsExtractor<ClientSearchResult>() {
                    @Override
                    public ClientSearchResult extract(SearchResponse response) {
                        ObjectMapper mapper = new ObjectMapper();
                        SearchHit[] hits = response.getHits().getHits();
                        ClientSearchResult clientSearchResult = new ClientSearchResult();
                        List<Client> clients = new ArrayList<>();

                        for(SearchHit hit:hits) {
                            try {
                                clients.add(mapper.readValue(hit.getSourceAsString(), Client.class));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        List<AggreagateBucket> aggregateBuckets = extractAggregateInfo(response.getAggregations());

                        clientSearchResult.setBuckets(aggregateBuckets);
                        clientSearchResult.setClients(clients);

                        return clientSearchResult;
                    }

                    private List<AggreagateBucket> extractAggregateInfo(Aggregations aggregations) {
                        List<AggreagateBucket> aggregates = new ArrayList<>();

                        Terms terms = (Terms) aggregations.asMap().get("cities");

                        aggregates.addAll(
                                        terms.getBuckets()
                                        .stream()
                                        .map(bucket -> new AggreagateBucket(bucket.getKey(), bucket.getDocCount()))
                                        .collect(Collectors.toList())
                                );

                        return aggregates;
                    }
                });


        return clientSearchResult;
    }

    public List<Client> searchInvoicesByInvoiceId(String invoiceId) {

        WrapperQueryBuilder query = QueryBuilders.wrapperQuery("{\n" +
                "  \"query\": {\n" +
                "    \"nested\": {\n" +
                "      \"path\": \"invoice\",\n" +
                "      \"score_mode\": \"avg\",\n" +
                "      \"query\": {\n" +
                "        \"bool\": {\n" +
                "          \"must\": [\n" +
                "            {\n" +
                "              \"multi_match\": {\n" +
                "                \"query\": \""+invoiceId+"\",\n" +
                "                \"type\": \"phrase_prefix\",\n" +
                "                \"fields\": [\n" +
                "                  \"invoice.invoiceId\"\n" +
                "                ]\n" +
                "              }\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
        return elasticsearchTemplate.queryForList(searchQuery, Client.class);
    }


    public ClientSearchResult searchByInvoiceDueDate(String searchString, Long date) {

        WrapperQueryBuilder query = QueryBuilders.wrapperQuery("{\n" +
                "  \"filtered\": {\n" +
                "    \"query\": {\n" +
                "      \"multi_match\": {\n" +
                "        \"query\": \""+searchString+"\",\n" +
                "        \"type\": \"phrase_prefix\",\n" +
                "        \"fields\": [\n" +
                "          \"name\",\n" +
                "          \"taxNumber\",\n" +
                "          \"uid\",\n" +
                "          \"externalId\",\n" +
                "          \"address.city\",\n" +
                "          \"invoice.invoiceId\"\n" +
                "        ]\n" +
                "      }\n" +
                "    }" +
                ",\n" +
                "    \"filter\": {\n" +
                "      \"and\": [\n" +
                "        {\n" +
                "          \"term\": {\n" +
                "            \"invoice.invoiceDueDate\": "+date+"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}");

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
        ClientSearchResult clientSearchResult = new ClientSearchResult();
        clientSearchResult.setClients(elasticsearchTemplate.queryForList(searchQuery, Client.class));
        return clientSearchResult;
    }

    public ClientSearchResult searchAllByInvoiceDueDate(Long date) {

        WrapperQueryBuilder query = QueryBuilders.wrapperQuery("{\n" +
                "  \"filtered\": {\n" +
                "    \"query\": {\n" +
                "      \"match_all\": {}\n" +
                "    },\n" +
                "    \"filter\": {\n" +
                "      \"and\": [\n" +
                "        {\n" +
                "          \"term\": {\n" +
                "            \"invoice.invoiceDueDate\": "+date+"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }\n" +
                "}");

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(query).build();
        ClientSearchResult clientSearchResult = new ClientSearchResult();
        clientSearchResult.setClients(elasticsearchTemplate.queryForList(searchQuery, Client.class));
        return clientSearchResult;
    }
}
