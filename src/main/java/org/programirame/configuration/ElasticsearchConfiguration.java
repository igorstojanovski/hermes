package org.programirame.configuration;


import org.elasticsearch.client.Client;
import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Configuration
@EnableConfigurationProperties(ElasticsearchProperties.class)
@Setting(settingPath = "/settings/elasticsearch-settings.json")
public class ElasticsearchConfiguration implements DisposableBean {


    @Autowired
    private ElasticsearchProperties properties;

    private NodeClient client;

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate() {
        return new ElasticsearchTemplate(esClient());
    }

    @Bean
    public Client esClient() {
        try {

            NodeBuilder nodeBuilder = new NodeBuilder();
            nodeBuilder
                    .clusterName(this.properties.getClusterName())
                    .local(false);

            nodeBuilder
                    .settings()
                    .put("http.enabled", true);

            this.client = (NodeClient) nodeBuilder.node().client();
//            client.admin().indices().prepareCreate("hermes")
//                    .setSettings(ImmutableSettings.settingsBuilder().loadFromSource(jsonBuilder()
//                            .startObject()
//                            .startObject("analysis")
//                            .startObject("analyzer")
//                            .startObject("steak")
//                            .field("type", "custom")
//                            .field("tokenizer", "enGram")
//                            .field("filter", new String[]{"snowball", "standard", "lowercase"})
//                            .endObject()
//                            .endObject()
//                            .endObject()
//                            .endObject().string()))
//                    .execute().actionGet();

            return this.client;
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    @Override
    public void destroy() throws Exception {
        if (this.client != null) {
            this.client.close();
        }
    }
}
