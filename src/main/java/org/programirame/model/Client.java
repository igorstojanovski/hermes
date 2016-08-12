package org.programirame.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@Setting(settingPath = "/settings/elasticsearch-settings.json")
@Document(indexName = "hermes", type = "client", shards = 1, replicas = 0, refreshInterval = "-1")
public class Client {

    @Id
    private String externalId;
    private String name;
    private String surname;
    private String taxNumber;
    private String uid;
    private String email;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Invoice> invoice;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Payment> payments;


    @Field(type = FieldType.Nested, includeInParent = true)
    private ClientType type;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Address> address;

    public Client() {
    }

    public Client(String externalId, String name, String surname, String taxNumber, String uid, ClientType type, String email) {
        this.externalId = externalId;
        this.name = name;
        this.surname = surname;
        this.taxNumber = taxNumber;
        this.uid = uid;
        this.type = type;
        this.email = email;
    }


    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<Invoice> getInvoice() {
        return invoice;
    }

    public void setInvoice(List<Invoice> invoice) {
        this.invoice = invoice;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }
}
