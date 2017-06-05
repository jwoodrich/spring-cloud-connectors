package org.springframework.cloud.localconfig;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.UriBasedServiceInfo;
import org.springframework.cloud.service.common.KafkaServiceInfo;
import org.springframework.cloud.service.common.MongoServiceInfo;

public class LocalConfigConnectorKafkaServiceTest extends AbstractLocalConfigConnectorWithUrisTest {

	@Test
	public void serviceCreation() {
		List<ServiceInfo> services = connector.getServiceInfos();
		ServiceInfo service = getServiceInfo(services, "kafka");
		assertNotNull(service);
		assertTrue(service instanceof KafkaServiceInfo);
		assertUriParameters((KafkaServiceInfo) service);
		assertEquals("10.25.35.45:9876",((KafkaServiceInfo)service).getConfig().get("bootstrap.servers"));
	}
	
}
