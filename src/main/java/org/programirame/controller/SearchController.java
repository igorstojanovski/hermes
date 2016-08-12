package org.programirame.controller;

import org.programirame.model.Client;
import org.programirame.model.search.ClientSearchResult;
import org.programirame.model.search.SearchQuery;
import org.programirame.repositories.ClientRepository;
import org.programirame.search.templates.QueryFilter;
import org.programirame.search.templates.SearchQueryTemplate;
import org.programirame.service.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    Search search;

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping(method = RequestMethod.POST, path = "/clients")
    public ResponseEntity<ClientSearchResult> searchClients(@RequestBody SearchQuery query) {
        System.out.println("SEARCH QUERY IS: " + query.getSearchQuery());

        ClientSearchResult results;
        String simpleQuery = "";
        String dueDateInValue = "";
        if (query.getSearchQuery().contains("=")) {
            String searchQuery = query.getSearchQuery();
            String[] splinters = searchQuery.split(",");

            List<QueryFilter> filters = new ArrayList<>();

            for(String splinter:splinters) {
                if(splinter.contains("DueDateIn") && splinter.contains("=")) {
                    dueDateInValue = splinter.split("=")[1];

                    LocalDate dueDate = LocalDate.now().atStartOfDay().plusDays(Integer.valueOf(dueDateInValue)).toLocalDate();
                    Instant dueDateEpoch = dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

                    HashMap<String, Object> parameters = new HashMap<>();
                    parameters.put("date", dueDateEpoch.toEpochMilli());

                    filters.add(new QueryFilter("DueDateIn", parameters));

                } else if(splinter.contains("City") && splinter.contains("=")) {
                    String city = splinter.split("=")[1];

                    HashMap<String, Object> parameters = new HashMap<>();
                    parameters.put("city", city);

                    filters.add(new QueryFilter("City", parameters));
                } else {
                    simpleQuery = simpleQuery+" "+splinter;
                }
            }

            String automaticQuery = SearchQueryTemplate.buildFiltered(simpleQuery, filters);
            System.out.println(automaticQuery);
            results = search.search(automaticQuery);


        } else {
            results = search.searchClient(query.getSearchQuery());
        }

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/clients/{id}")
    public ResponseEntity<Client> searchClientById(@PathVariable String id) {
        System.out.println("SEARCHING BY ID: " + id);

        Client result = clientRepository.findOne(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
