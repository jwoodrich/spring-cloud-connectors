package org.springframework.cloud.service.messaging;

import java.util.Map;

import org.springframework.cloud.service.MapServiceConnectorConfig;

/**
 * Class to hold configuration values for a Kafka connection
 *
 * @author Jason Woodrich
 */
public class KafkaConnectionFactoryConfig extends MapServiceConnectorConfig {

	public KafkaConnectionFactoryConfig(Map<String, Object> properties) {
		super(properties);
	}
}
