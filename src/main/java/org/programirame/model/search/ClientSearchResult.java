package org.programirame.model.search;

import org.programirame.model.Client;

import java.util.List;

public class ClientSearchResult {

    private List<Client> clients;
    private List<AggreagateBucket> buckets;

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<AggreagateBucket> getBuckets() {
        return buckets;
    }

    public void setBuckets(List<AggreagateBucket> buckets) {
        this.buckets = buckets;
    }
}
