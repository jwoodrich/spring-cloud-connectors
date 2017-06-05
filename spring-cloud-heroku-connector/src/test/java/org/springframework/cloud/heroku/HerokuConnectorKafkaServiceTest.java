package org.springframework.cloud.heroku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.common.KafkaServiceInfo;

/**
 * @author Ramnivas Laddad
 */
public class HerokuConnectorKafkaServiceTest extends AbstractHerokuConnectorTest {
	private static final String DEFAULT_ENCODING="US-ASCII";
	private static final String CLIENT_ID="Groot";
	private static final String BOOTSTRAP_SERVERS="10.10.11.11:1234,10.10.12.12:5678";
	private static final String URL_TEMPLATE = "kafka://$hostname:$port/?bootstrap%2Eservers=$bootstrap&client%2Eid=$clientId";

	@Test
	public void kafkaServiceCreation() {
		for (String kafkaEnv : new String[]{"KAFKA_URL", "KAFKA_PLAINTEXT_URL"}) {
			Map<String, String> env = new HashMap<String, String>();
			String kafkaUrl = getKafkaServiceUrl();
			env.put(kafkaEnv, kafkaUrl);
			System.out.println("env="+env);
			when(mockEnvironment.getEnv()).thenReturn(env);

			List<ServiceInfo> serviceInfos = testCloudConnector.getServiceInfos();
			ServiceInfo serviceInfo = getServiceInfo(serviceInfos, kafkaEnv.substring(0, kafkaEnv.length() - 4));
			assertNotNull(serviceInfo);
			assertTrue(serviceInfo instanceof KafkaServiceInfo);
			assertKafkaServiceInfo((KafkaServiceInfo) serviceInfo);
		}
	}

	private String getKafkaServiceUrl() {
		try {
			return URL_TEMPLATE.replace("$hostname", hostname).
					replace("$port", Integer.toString(port)).
					replace("$clientId", CLIENT_ID).
					replace("$bootstrap", URLEncoder.encode(BOOTSTRAP_SERVERS,DEFAULT_ENCODING));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		
	}
	protected void assertKafkaServiceInfo(KafkaServiceInfo serviceInfo) {
		assertEquals(hostname, serviceInfo.getHost());
		assertEquals(port, serviceInfo.getPort());
		assertEquals(BOOTSTRAP_SERVERS, serviceInfo.getConfig().get("bootstrap.servers"));
		assertEquals(CLIENT_ID, serviceInfo.getConfig().get("client.id"));
	}

}
