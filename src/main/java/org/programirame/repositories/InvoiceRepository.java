package org.programirame.repositories;


import org.programirame.model.Invoice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface InvoiceRepository extends ElasticsearchRepository<Invoice, String> {
}
