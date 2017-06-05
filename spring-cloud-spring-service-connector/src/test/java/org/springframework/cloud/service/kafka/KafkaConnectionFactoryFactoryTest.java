package org.springframework.cloud.service.kafka;

import org.mockito.Mock;
import org.springframework.cloud.service.AbstractCloudServiceConnectorFactoryTest;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.cloud.service.common.KafkaServiceInfo;
import org.springframework.cloud.service.messaging.KafkaConnectionFactory;
import org.springframework.cloud.service.messaging.KafkaConnectionFactoryFactory;

/**
 * 
 * @author Jason Woodrich, shamelessly copied and pasted from existing test cases
 *
 */
public class KafkaConnectionFactoryFactoryTest extends AbstractCloudServiceConnectorFactoryTest<KafkaConnectionFactoryFactory<?,?>, KafkaConnectionFactory, KafkaServiceInfo> {
	@Mock KafkaConnectionFactory mockConnector;
	
	public KafkaConnectionFactoryFactory<?,?> createTestCloudServiceConnectorFactory(String id, ServiceConnectorConfig config) {
		return new KafkaConnectionFactoryFactory<Object,Object>(id, config);
	}
	
	public Class<KafkaConnectionFactory> getConnectorType() {
		return KafkaConnectionFactory.class;
	}
	
	public KafkaConnectionFactory getMockConnector() {
		return mockConnector;
	}
	
	public KafkaServiceInfo getTestServiceInfo(String id) {
		return new KafkaServiceInfo(id, "host", 0, null);
	}
}
