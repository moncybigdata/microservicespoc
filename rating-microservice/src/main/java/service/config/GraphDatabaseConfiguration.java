package service.config;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
//import org.springframework.data.neo4j.rest.SpringCypherRestGraphDatabase;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.ConnectionFactory;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 *
 *
 * Manages the configuration for a Neo4j graph database server
 */
@EnableNeo4jRepositories(basePackages = "service.data")
@EnableTransactionManagement(mode = AdviceMode.PROXY)
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties
public class GraphDatabaseConfiguration extends Neo4jConfiguration {

    @Value("${neo4j.uri}")
    private String url;

    @Value("${neo4j.username}")
    private String username;

    @Value("${neo4j.password}")
    private String password;

    @Autowired
    Environment environment;

    @Value("${spring.application.name}")
    private String clientId;

    public GraphDatabaseConfiguration() {
        super();
        setBasePackage("service.data", "service.config");
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Autowired(required = true)
    @Override
    public void setGraphDatabaseService(GraphDatabaseService graphDatabaseService) {
        super.setGraphDatabaseService(graphDatabaseService);
    }

    @Bean
    public static YamlPropertySourceLoader yamlPropertySourceLoader()             {
        return new YamlPropertySourceLoader();
    }

    @Bean(destroyMethod = "shutdown")
    public GraphDatabaseService graphDatabaseService() {
        if(Arrays.asList(environment.getActiveProfiles()).contains("cloud")) {
            // Connect to external Neo4j server
            //setGraphDatabaseService(new SpringCypherRestGraphDatabase(url, username, password));
            return new GraphDatabaseFactory().newEmbeddedDatabase("user.db");
        } else {
            // Connect to local ephemeral database
            return new GraphDatabaseFactory().newEmbeddedDatabase("user.db");
        }

        //return getGraphDatabaseService();
    }

    @Bean
    public ConnectionFactory jmsConnectionFactory(MessageStoreDetails details) throws UnsupportedEncodingException {
        JmsConnectionFactory jmsConnectionFactory = new JmsConnectionFactory(details.getUrlString());
        jmsConnectionFactory.setUsername(details.getUsername());
        jmsConnectionFactory.setPassword(details.getPassword());
        jmsConnectionFactory.setClientID(clientId);
        //jmsConnectionFactory.setReceiveLocalOnly(true);
        return new CachingConnectionFactory(jmsConnectionFactory);
    }
//
//    @Bean
//    public JmsTemplate jmsTemplate(ConnectionFactory jmsConnectionFactory) {
//        JmsTemplate returnValue = new JmsTemplate();
//        returnValue.setConnectionFactory(jmsConnectionFactory);
//        return returnValue;
//    }

    @Bean
    public JmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory returnValue = new DefaultJmsListenerContainerFactory();
        returnValue.setConnectionFactory(connectionFactory);
        return returnValue;
    }

    @Bean
    public JmsListenerContainerFactory topicJmsListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory returnValue = new DefaultJmsListenerContainerFactory();
        returnValue.setConnectionFactory(connectionFactory);
        returnValue.setSubscriptionDurable(Boolean.TRUE);
        return returnValue;
    }

}
