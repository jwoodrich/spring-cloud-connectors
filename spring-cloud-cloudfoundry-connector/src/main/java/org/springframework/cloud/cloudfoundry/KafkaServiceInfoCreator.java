package org.springframework.cloud.cloudfoundry;

import java.util.Map;

import org.springframework.cloud.service.common.KafkaServiceInfo;

/**
 * 
 * @author Jason Woodrich
 *
 */
public class KafkaServiceInfoCreator  extends CloudFoundryServiceInfoCreator<KafkaServiceInfo> {
	private static final String DEFAULT_ENCODING="UTF-8";
	public KafkaServiceInfoCreator() {
		// the literal in the tag is CloudFoundry-specific
		super(new Tags("kafka"), KafkaServiceInfo.KAFKA_SCHEME);
	}

	public KafkaServiceInfo createServiceInfo(Map<String,Object> serviceData) {
		String id = getId(serviceData);
		String uri = getUriFromCredentials(getCredentials(serviceData));
		return new KafkaServiceInfo(id, uri, DEFAULT_ENCODING);
	}

}
