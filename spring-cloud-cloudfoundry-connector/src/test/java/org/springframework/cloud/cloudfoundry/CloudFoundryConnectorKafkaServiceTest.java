package org.springframework.cloud.cloudfoundry;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.hamcrest.CoreMatchers;

import org.junit.Test;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.common.KafkaServiceInfo;

/**
 * @author Jason Woodrich, shamelessly copied from existing tests
 */
public class CloudFoundryConnectorKafkaServiceTest extends AbstractCloudFoundryConnectorTest {
	private static final String DEFAULT_ENCODING="UTF-8";
	private static final String CLIENT_ID=username;
	private static final String BOOTSTRAP_SERVERS="10.10.11.11:1234,10.10.12.12:5678";
	@Test
	public void kafkaServiceCreation() throws Exception {
		when(mockEnvironment.getEnvValue("VCAP_SERVICES"))
				.thenReturn(getServicesPayload(
						getKafkaServicePayload("kafka-1", hostname, port, CLIENT_ID, BOOTSTRAP_SERVERS),
						getKafkaServicePayload("kafka-2", hostname, port, CLIENT_ID, BOOTSTRAP_SERVERS)));

		List<ServiceInfo> serviceInfos = testCloudConnector.getServiceInfos();
		assertServiceFoundOfType(serviceInfos, "kafka-1", KafkaServiceInfo.class);
		assertServiceFoundOfType(serviceInfos, "kafka-2", KafkaServiceInfo.class);
		KafkaServiceInfo serviceInfo = (KafkaServiceInfo)getServiceInfo(serviceInfos, "kafka-1");
		assertThat(serviceInfo.getHost(),equalTo(hostname));
		assertThat(serviceInfo.getPort(),equalTo(port));		
		assertThat((String)serviceInfo.getConfig().get("bootstrap.servers"),equalTo(BOOTSTRAP_SERVERS));
	}
	

	@Test
	public void kafkaServiceCreationNoLabelNoTags() throws UnsupportedEncodingException {
		when(mockEnvironment.getEnvValue("VCAP_SERVICES"))
				.thenReturn(getServicesPayload(
						getKafkaServicePayloadNoLabelNoTags("kafka-1", hostname, port, CLIENT_ID, BOOTSTRAP_SERVERS),
						getKafkaServicePayloadNoLabelNoTags("kafka-2", hostname, port, CLIENT_ID, BOOTSTRAP_SERVERS)));

		List<ServiceInfo> serviceInfos = testCloudConnector.getServiceInfos();
		assertServiceFoundOfType(serviceInfos, "kafka-1", KafkaServiceInfo.class);
		assertServiceFoundOfType(serviceInfos, "kafka-2", KafkaServiceInfo.class);
	}

	private String getKafkaServicePayload(String serviceName, String hostname, int port,
										  String client, String bootstrap) throws UnsupportedEncodingException {
		return getKafkaServicePayload("test-kafka-info.json", 
				serviceName, hostname, port, CLIENT_ID, BOOTSTRAP_SERVERS);
	}

	private String getKafkaServicePayloadNoLabelNoTags(String serviceName,
													   String hostname, int port,
													   String clientId, String bootstrap) throws UnsupportedEncodingException {
		return getKafkaServicePayload("test-kafka-info-no-label-no-tags.json",
				serviceName, hostname, port, CLIENT_ID, BOOTSTRAP_SERVERS);
	}

	private String getKafkaServicePayload(String payloadFile, String serviceName,
										  String hostname, int port, String clientId, 
										  String bootstrap) throws UnsupportedEncodingException {
		String payload = readTestDataFile(payloadFile);
		payload = payload.replace("$serviceName", serviceName);
		payload = payload.replace("$hostname", hostname);
		payload = payload.replace("$port", Integer.toString(port));
		payload = payload.replace("$clientId", clientId!=null?URLEncoder.encode(clientId,DEFAULT_ENCODING):"");
		payload = payload.replace("$bootstrap", bootstrap!=null?URLEncoder.encode(bootstrap,DEFAULT_ENCODING):"");
		return payload;
	}
}
