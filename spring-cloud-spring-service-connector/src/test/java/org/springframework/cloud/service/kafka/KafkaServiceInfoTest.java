package org.springframework.cloud.service.kafka;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.cloud.service.common.AmqpServiceInfo;
import org.springframework.cloud.service.common.KafkaServiceInfo;

/**
 * 
 * @author Jason Woodrich
 *
 */
public class KafkaServiceInfoTest {
	@Test
	public void uriBasedParsing() {
		KafkaServiceInfo serviceInfo = new KafkaServiceInfo("id", "kafka://myhost:12345/?bootstrap%2Eservers=otherhost&client%2Eid=client1");
		
		assertEquals("myhost", serviceInfo.getHost());
		assertEquals(12345, serviceInfo.getPort());
		assertEquals("otherhost", serviceInfo.getConfig().get("bootstrap.servers"));
		assertEquals("client1", serviceInfo.getConfig().get("client.id"));
		
	}

	@Test(expected=IllegalArgumentException.class)
	public void missingScheme() {
		new KafkaServiceInfo("id",  "://myuser:mypass@:12345");
	}

	@Test
	public void kafkaSchemeAccepted() {
		KafkaServiceInfo serviceInfo = new KafkaServiceInfo("id",  "kafka://myhost:12345");
		assertEquals("kafka", serviceInfo.getScheme());
	}

	@Test(expected=IllegalArgumentException.class)
	public void missingHostAndBootstrapServers() {
		new KafkaServiceInfo("id",  "kafka://:12345");
	}
	public void missingHostAndPortButHasBootstrapServers() {
		new KafkaServiceInfo("id",  "kafka://:/?bootstrap%2Eservers=thathost%3A54321");
	}
}
