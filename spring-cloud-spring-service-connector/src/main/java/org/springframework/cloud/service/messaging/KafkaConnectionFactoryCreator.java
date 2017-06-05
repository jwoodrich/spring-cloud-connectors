package org.springframework.cloud.service.messaging;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.springframework.cloud.service.AbstractServiceConnectorCreator;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.cloud.service.common.KafkaServiceInfo;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

/**
 * 
 * @author Jason Woodrich
 */
public class KafkaConnectionFactoryCreator extends	AbstractServiceConnectorCreator<KafkaConnectionFactory, KafkaServiceInfo> {
	@Override
	public KafkaConnectionFactory create(KafkaServiceInfo serviceInfo, ServiceConnectorConfig serviceConnectorConfiguration) {
		Map<String,Object> properties=createKafkaProperties(serviceInfo);
		ConsumerFactory<?,?> consumer=createKafkaConsumerFactory(properties);
		ProducerFactory<?,?> producer=createKafkaProducerFactory(properties);
		return new DefaultKafkaConnectionFactory(consumer,producer);
	}
	/**
	 * Creates Kafka connection properties from a KafkaServiceInfo.
	 * @param serviceInfo 
	 * @return
	 */
	protected Map<String, Object> createKafkaProperties(KafkaServiceInfo serviceInfo) {
		Map<String,Object> ret=new HashMap<String,Object>(serviceInfo.getConfig());
		// If the service info already has a bootstrap.servers then prepend that with the host:port of the service url.
		if (ret.containsKey(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG)) {
			ret.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, serviceInfo.getHost()+":"+serviceInfo.getPort()+","+ret.get(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG));
		} else {
			ret.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, serviceInfo.getHost()+":"+serviceInfo.getPort());
		}
		return ret;
	}

	/**
	 * Creates a Spring-Kafka ConsumerFactory.
	 * @param properties The connection properties.
	 * @return
	 */
	protected ConsumerFactory<?,?> createKafkaConsumerFactory(Map<String, Object> properties) {
		return new DefaultKafkaConsumerFactory(properties);
	}
	
	/**
	 * Creates a Spring-Kafka ProducerFactory.
	 * @param properties The connection properties.
	 * @return
	 */
	protected ProducerFactory<?,?> createKafkaProducerFactory(Map<String, Object> properties) {
		return new DefaultKafkaProducerFactory(properties);
	}
}
