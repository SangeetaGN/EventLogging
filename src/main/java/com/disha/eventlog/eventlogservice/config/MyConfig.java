package com.disha.quickride.eventlogservice.config;


import com.disha.quickride.domain.model.commn.KafkaConfiguration;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

@Configuration
@PropertySource("classpath:KafkaTopics.properties")
@PropertySource("classpath:metrics.properties")
@EnableKafka
public class MyConfig {

    @Value("${kafka_ip}")
    private String kafka_ip;

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory(KafkaConfiguration kafkaConfiguration) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH);
        factory.getContainerProperties().setAckOnError(false);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setConcurrency(5);

        factory.setConsumerFactory(consumerFactory(kafkaConfiguration));
        return factory;
    }

    @Bean
    public KafkaConfiguration kafkaConfig()
    {

        KafkaConfiguration config = new KafkaConfiguration();
        config.setServerAddress(kafka_ip);
        config.setLingerMS("100");
        config.setMaxRequestSize("3145728");
        config.setEnableRideMgmtKafkaService(false);
        config.setRetries(3);
        config.setConcurrency(5);
        return config;
    }
    @Bean
    public ConsumerFactory<String, String> consumerFactory(KafkaConfiguration kafkaConfiguration) {

        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfiguration.getServerAddress());
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "abc");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return new DefaultKafkaConsumerFactory<>(config);
    }


}
