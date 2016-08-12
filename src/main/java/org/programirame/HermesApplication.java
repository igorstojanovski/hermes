package org.programirame;

import org.programirame.importer.ImportData;
import org.programirame.model.Client;
import org.programirame.model.Invoice;
import org.programirame.repositories.ClientRepository;
import org.programirame.service.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration(exclude= { ElasticsearchAutoConfiguration.class })
public class HermesApplication {

    @Autowired
    ImportData importer;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    Search search;

    public static void main(String[] args) {
        SpringApplication.run(HermesApplication.class, args);

    }

    @PostConstruct
    public void importData() {
        importer.importClient();
    }
}
