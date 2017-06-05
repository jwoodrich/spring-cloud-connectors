package org.springframework.cloud.service.messaging;

import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;

/**
 * Factory for obtaining Spring-Kafka factories.
 * @author Jason Woodrich
 *
 */
public interface KafkaConnectionFactory {
	/**
	 * Retrieves a factory for creating Kafka consumers.  
	 * @param <K> the key type.
	 * @param <V> the value type.
	 * @return The consumer factory 
	 */
	<K,V> ConsumerFactory<K,V> getConsumerFactory();
	/**
	 * Retrieves a factory for creating Kafka producers. 
	 * @param <K> the key type.
	 * @param <V> the value type.
	 * @return The producer factory
	 */
	<K,V> ProducerFactory<K,V> getProducerFactory();
}
