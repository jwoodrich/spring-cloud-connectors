package org.springframework.cloud.localconfig;

import org.springframework.cloud.service.common.KafkaServiceInfo;

/**
 * @author Jason Woodrich
 */
public class KafkaServiceInfoCreator extends LocalConfigServiceInfoCreator<KafkaServiceInfo> {
	private static final String DEFAULT_ENCODING="UTF-8";
	public KafkaServiceInfoCreator() {
		super(KafkaServiceInfo.KAFKA_SCHEME);
	}

	@Override
	public KafkaServiceInfo createServiceInfo(String id, String uri) {
		return new KafkaServiceInfo(id, uri, DEFAULT_ENCODING);
	}
}
