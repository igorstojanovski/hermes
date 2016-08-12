package org.programirame.importer;

import org.programirame.model.Address;
import org.programirame.model.Client;
import org.programirame.model.ClientType;
import org.programirame.model.Invoice;
import org.programirame.repositories.ClientRepository;
import org.programirame.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.*;

@Service
public class ImportData {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    public void importClient() {

        Client client = new Client("STB00001", "Stopanska Banka", "", "15594615232645", "459987411", new ClientType("Original", "Original Number"), "stb@stb.com");

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(new Invoice("INVSTB2", "STB00001",asDate(LocalDate.of(2016, Month.MAY, 1)),
                asDate(LocalDate.of(2016, Month.MAY, 15)), BigDecimal.valueOf(14000), BigDecimal.valueOf(14000)));

        invoices.add(new Invoice("INVSTB3", "STB00001",asDate(LocalDate.of(2016, Month.JUNE, 1)),
                asDate(LocalDate.of(2016, Month.JUNE, 15)), BigDecimal.valueOf(14000), BigDecimal.valueOf(14000)));

        invoices.add(new Invoice("INVSTB14", "STB00001",asDate(LocalDate.of(2016, Month.JULY, 1)),
                asDate(LocalDate.of(2016, Month.JULY, 15)), BigDecimal.valueOf(14000), BigDecimal.valueOf(14000)));

        client.setInvoice(invoices);
        client.setAddress(Collections.singletonList(new Address("Skopje", "1000", "ul. 10 br. 8 Jurumleri", "Macedonia")));
        clientRepository.save(client);

        Client zegin = new Client("PROK11", "ProKredit Banka", "", "5646546546546", "218944416", new ClientType("Company", "Original Number"), "pro@pro.com");

        ArrayList<Invoice> zeginInvoices = new ArrayList<>();
        zeginInvoices.add(new Invoice("JUNE00012", "PROK11", asDate(LocalDate.of(2016, Month.MAY, 1)),
                asDate(LocalDate.of(2016, Month.MAY, 15)), BigDecimal.valueOf(14020), BigDecimal.valueOf(14000)));

        zeginInvoices.add(new Invoice("JUNE00013", "PROK11",  asDate(LocalDate.of(2016, Month.JUNE, 1)),
                asDate(LocalDate.of(2016, Month.JUNE, 15)), BigDecimal.valueOf(20058), BigDecimal.valueOf(1000)));

        zeginInvoices.add(new Invoice("MAY00014", "PROK11",  asDate(LocalDate.of(2016, Month.JULY, 1)),
                asDate(LocalDate.of(2016, Month.JULY, 15)), BigDecimal.valueOf(12598), BigDecimal.ZERO));

        zegin.setInvoice(zeginInvoices);
        zegin.setAddress(Collections.singletonList(new Address("Bitola", "1000", "ul. 10 br. 8 Jurumleri", "Macedonia")));
        clientRepository.save(zegin);

        Client three = new Client("ZG002", "ZEGIN", "", "564546456", "95648744416", new ClientType("Company", "Company"), "igorce@outlook.com");
        three.setInvoice(getInvoices("ZG002"));
        three.setAddress(Collections.singletonList(new Address("Skopje", "1000", "ul. 10 br. 8 Jurumleri", "Macedonia")));
        clientRepository.save(three);


        Client four = new Client("KB001", "Komercijalna Banka", "", "99361324586412", "7770000222000", new ClientType("Company", "Company"), "igorce@gmail.com");
        four.setInvoice(getInvoices("KB001"));
        four.setAddress(Collections.singletonList(new Address("Bitola", "1000", "ul. 10 br. 8 Jurumleri", "Macedonia")));
        clientRepository.save(four);


        Client five = new Client("ALFA01", "Alfa Banka", "", "666008888777", "20000001025", new ClientType("Company", "Company"), "igorce_mkd@yahoo.com");
        five.setInvoice(getInvoices("ALFA01"));
        five.setAddress(Collections.singletonList(new Address("Struga", "1000", "ul. 10 br. 8 Jurumleri", "Macedonia")));
        clientRepository.save(five);

//        final List<IndexQuery> indexQueries = new ArrayList<>();
//
//        final IndexQuery indexQuery1 = new IndexQuery();
//        indexQuery1.setId(client.getExternalId());
//        indexQuery1.setObject(client);
//
//        final IndexQuery indexQuery2 = new IndexQuery();
//        indexQuery2.setId(zegin.getExternalId());
//        indexQuery2.setObject(zegin);
//
//        final IndexQuery indexQuery3 = new IndexQuery();
//        indexQuery3.setId(three.getExternalId());
//        indexQuery3.setObject(three);
//
//        indexQueries.add(indexQuery1);
//        indexQueries.add(indexQuery2);
//        indexQueries.add(indexQuery3);
//
//        elasticsearchTemplate.putMapping(Client.class);
//        elasticsearchTemplate.bulkIndex(indexQueries);
//        elasticsearchTemplate.refresh(Client.class, true);
    }


    public List<Invoice> getInvoices(String clientId) {
        Random r = new Random();
        int c = r.nextInt(9)+1;

        RandomString randomString = new RandomString(7);
        List<Invoice> invoices = new ArrayList<>();

        for(int i = c; i>0; i--) {

            LocalDate invoiceDate = LocalDate.now().atStartOfDay().plusMonths(-i + 1).toLocalDate();
            Instant invoiceDateEpoch = invoiceDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

            LocalDate dueDate = LocalDate.now().atStartOfDay().plusMonths( -i+1 ).plusDays(r.nextInt(15)).toLocalDate();
            Instant dueDateEpoch = dueDate.atStartOfDay(ZoneId.systemDefault()).toInstant();

            int amount = r.nextInt(20000);
            int payed = r.nextInt(amount);
            invoices.add(new Invoice(randomString.nextString(), clientId, new Date(invoiceDateEpoch.toEpochMilli()),
                    new Date(dueDateEpoch.toEpochMilli()), BigDecimal.valueOf(amount), BigDecimal.valueOf(payed)));
        }
        invoiceRepository.save(invoices);
        return invoices;
    }

    public static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
