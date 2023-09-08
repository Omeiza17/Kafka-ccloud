package dev.codingstoic.kafkaccloud.config.remote;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

import static org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.admin.AdminClientConfig.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.*;

@Profile("cloud")
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.properties.ssl.endpoint.identification.algorithm}")
    private String algorithm;
    @Value("${spring.kafka.properties.sasl.mechanism}")
    private String mechanism;
    @Value("${spring.kafka.properties.sasl.jaas.config}")
    private String jaasConfig;
    @Value("${spring.kafka.properties.security.protocol}")
    private String protocol;

    @Bean
    public ProducerFactory<Integer, String> producerFactory() {

        return new DefaultKafkaProducerFactory<>(
                Map.of(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers,
                        RETRIES_CONFIG, 0,
                        BUFFER_MEMORY_CONFIG, 33554432,
                        KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class,
                        VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                        "ssl.endpoint.identification.algorithm", algorithm,
                        "sasl.mechanism", mechanism,
                        "sasl.jaas.config", jaasConfig,
                        "security.protocol", protocol
                )
        );
    }

    @Bean
    public KafkaTemplate<Integer, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic newTopic() {
        return TopicBuilder.name("hobbit").partitions(9).replicas(3).build();
    }
}
