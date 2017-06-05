package org.springframework.cloud.service.messaging;

import org.springframework.cloud.service.AbstractCloudServiceConnectorFactory;
import org.springframework.cloud.service.ServiceConnectorConfig;

/**
 * Spring factory bean for Kafka service.
 *
 * @author Jason Woodrich
 *
 */
public class KafkaConnectionFactoryFactory<K,V> extends AbstractCloudServiceConnectorFactory<KafkaConnectionFactory> {
	public KafkaConnectionFactoryFactory(String serviceId, ServiceConnectorConfig serviceConnectorConfiguration) {
		super(serviceId, KafkaConnectionFactory.class, serviceConnectorConfiguration);
	}
}
