package com.flexe.postservice.configuration;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flexe.postservice.entity.posts.PostInteraction;
import com.flexe.postservice.entity.user.UserNode;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    private <T> ConsumerFactory<String, T> createConsumerFactory(Class<T> clazz){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "flexe-interaction-service");
        ObjectMapper om = new ObjectMapper();
        JavaType type = om.getTypeFactory().constructType(clazz);
        return new DefaultKafkaConsumerFactory<>(configProps,new StringDeserializer(), new JsonDeserializer<>(type, om, false));
    }

    private <T> ConcurrentKafkaListenerContainerFactory<String, T> createKafkaListenerContainerFactory(Class<T> clazz){
        ConcurrentKafkaListenerContainerFactory<String, T> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createConsumerFactory(clazz));
        return factory;
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserNode> kafkaUserListenerContainerFactory(){
        return createKafkaListenerContainerFactory(UserNode.class);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PostInteraction> kafkaPostInteractionListenerContainerFactory(){
        return createKafkaListenerContainerFactory(PostInteraction.class);
    }

}