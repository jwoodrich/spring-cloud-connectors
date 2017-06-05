package org.springframework.cloud.service.messaging;

import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;

public class DefaultKafkaConnectionFactory implements KafkaConnectionFactory {
	private final ConsumerFactory<?,?> consumerFactory;
	private final ProducerFactory<?,?> producerFactory;
	
	public DefaultKafkaConnectionFactory(ConsumerFactory<?, ?> consumerFactory, ProducerFactory<?, ?> producerFactory) {
		this.consumerFactory = consumerFactory;
		this.producerFactory = producerFactory;
	}
	public<K,V> ConsumerFactory<K,V> getConsumerFactory() {
		return (ConsumerFactory<K,V>)consumerFactory;
	}
	
	public<K,V> ProducerFactory<K,V> getProducerFactory() {
		return (ProducerFactory<K,V>) producerFactory;
	}	
}
