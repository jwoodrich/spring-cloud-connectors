package org.springframework.cloud.heroku;

import org.springframework.cloud.service.common.KafkaServiceInfo;

/**
 *
 * @author Jason Woodrich
 *
 */
public class KafkaServiceInfoCreator extends HerokuServiceInfoCreator<KafkaServiceInfo> {
	private static final String DEFAULT_ENCODING="UTF-8";
	public KafkaServiceInfoCreator() {
		super(KafkaServiceInfo.KAFKA_SCHEME);
	}

	@Override
	public KafkaServiceInfo createServiceInfo(String id, String uri) {
		return new KafkaServiceInfo(HerokuUtil.computeServiceName(id), uri, DEFAULT_ENCODING);
	}

	@Override
	public String[] getEnvPrefixes() {
		return new String[]{ "KAFKA_URL", "KAFKA_PLAINTEXT_URL" };
	}
}
