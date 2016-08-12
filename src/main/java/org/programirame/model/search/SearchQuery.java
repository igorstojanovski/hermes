package org.programirame.model.search;


import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "searchQuery"
})
public class SearchQuery {

    @JsonProperty("searchQuery")
    private String searchQuery;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The searchQuery
     */
    @JsonProperty("searchQuery")
    public String getSearchQuery() {
        return searchQuery;
    }

    /**
     *
     * @param searchQuery
     * The searchQuery
     */
    @JsonProperty("searchQuery")
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
