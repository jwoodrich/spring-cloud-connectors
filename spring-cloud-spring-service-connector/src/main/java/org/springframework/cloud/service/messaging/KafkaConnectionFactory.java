package org.springframework.cloud.service.messaging;

import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;

public interface KafkaConnectionFactory {
	<K,V> ConsumerFactory<K,V> getConsumerFactory();
	<K,V> ProducerFactory<K,V> getProducerFactory();
}
