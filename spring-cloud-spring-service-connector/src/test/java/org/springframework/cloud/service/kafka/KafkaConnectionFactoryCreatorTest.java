package org.springframework.cloud.service.kafka;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.junit.Test;
import org.springframework.cloud.service.common.KafkaServiceInfo;
import org.springframework.cloud.service.messaging.KafkaConnectionFactory;
import org.springframework.cloud.service.messaging.KafkaConnectionFactoryCreator;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * 
 * @author Jason Woodrich (shamelessly copied and adapted from Ramnivas Laddad and Scott Frederick) 
 *
 */
public class KafkaConnectionFactoryCreatorTest {
	private static final String DEFAULT_ENCODING="UTF-8";
	private static final String TEST_HOST = "10.20.30.40";
	private static final String TEST_HOST2 = "11.21.31.41";
	private static final String TEST_HOST3 = "10.12.10.12";
	private static final int TEST_PORT = 1234;
	private static final int TEST_PORT2 = 5678;
	private static final int TEST_PORT3 = 9876;
	private static final String TEST_BOOTSTRAP1=TEST_HOST+":"+TEST_PORT;
	private static final String TEST_BOOTSTRAP2=TEST_HOST2+":"+TEST_PORT2+","+TEST_HOST3+":"+TEST_PORT3;
	private static final String TEST_BOOTSTRAP3=TEST_BOOTSTRAP1+","+TEST_BOOTSTRAP2;
	private static final String TEST_CLIENT="Groot";
	
	private static final String TEST_TOPIC="topic";

	private KafkaConnectionFactoryCreator testCreator = new KafkaConnectionFactoryCreator();

	@Test
	public void cloudCreationParamsNoMap() throws Exception {
		KafkaServiceInfo serviceInfo = new KafkaServiceInfo("id", TEST_HOST, TEST_PORT, null);
		KafkaConnectionFactory connector = testCreator.create(serviceInfo, null);
		assertNotNull(connector);
		assertEquals(TEST_BOOTSTRAP1, ((DefaultKafkaConsumerFactory<?,?>)connector.getConsumerFactory()).getConfigurationProperties().get(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG));
		assertEquals(TEST_BOOTSTRAP1, ((Map<String,Object>)ReflectionTestUtils.getField(connector.getProducerFactory(),"configs")).get(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG));
	}
	@Test
	public void cloudCreationParamsWithMap() throws Exception {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, TEST_BOOTSTRAP2);
		map.put(CommonClientConfigs.CLIENT_ID_CONFIG, TEST_CLIENT);
		KafkaServiceInfo serviceInfo = new KafkaServiceInfo("id", TEST_HOST, TEST_PORT, map);
		KafkaConnectionFactory connector = testCreator.create(serviceInfo, null);
		assertNotNull(connector);
		assertEquals(TEST_BOOTSTRAP3, ((DefaultKafkaConsumerFactory<?,?>)connector.getConsumerFactory()).getConfigurationProperties().get(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG));
		assertEquals(TEST_BOOTSTRAP3, ((Map<String,Object>)ReflectionTestUtils.getField(connector.getProducerFactory(),"configs")).get(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG));
		assertEquals(TEST_CLIENT, ((DefaultKafkaConsumerFactory<?,?>)connector.getConsumerFactory()).getConfigurationProperties().get(CommonClientConfigs.CLIENT_ID_CONFIG));
		assertEquals(TEST_CLIENT, ((Map<String,Object>)ReflectionTestUtils.getField(connector.getProducerFactory(),"configs")).get(CommonClientConfigs.CLIENT_ID_CONFIG));
	}
	@Test
	public void cloudCreationWithUriNoParams() throws Exception {
		URI uri = new URI("kafka", null, TEST_HOST, TEST_PORT, null, null, null);
		KafkaServiceInfo serviceInfo = new KafkaServiceInfo("id", uri.toString(), DEFAULT_ENCODING);
		KafkaConnectionFactory connector = testCreator.create(serviceInfo, null);
		assertNotNull(connector);
		assertEquals(TEST_BOOTSTRAP1, ((DefaultKafkaConsumerFactory<?,?>)connector.getConsumerFactory()).getConfigurationProperties().get(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG));
		assertEquals(TEST_BOOTSTRAP1, ((Map<String,Object>)ReflectionTestUtils.getField(connector.getProducerFactory(),"configs")).get(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG));
	}
	@Test
	public void cloudCreationWithUriWithParams() throws Exception {
		String query=URLEncoder.encode(CommonClientConfigs.CLIENT_ID_CONFIG, DEFAULT_ENCODING)+"="+URLEncoder.encode(TEST_CLIENT, DEFAULT_ENCODING)+"&"
				+URLEncoder.encode(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, DEFAULT_ENCODING)+"="+URLEncoder.encode(TEST_BOOTSTRAP2, DEFAULT_ENCODING);
		
		URI uri = new URI("kafka", null, TEST_HOST, TEST_PORT, null, query, null);
		KafkaServiceInfo serviceInfo = new KafkaServiceInfo("id", uri.toString(), DEFAULT_ENCODING);
		KafkaConnectionFactory connector = testCreator.create(serviceInfo, null);
		
		assertNotNull(connector);
		assertEquals(TEST_BOOTSTRAP3, ((DefaultKafkaConsumerFactory<?,?>)connector.getConsumerFactory()).getConfigurationProperties().get(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG));
		assertEquals(TEST_BOOTSTRAP3, ((Map<String,Object>)ReflectionTestUtils.getField(connector.getProducerFactory(),"configs")).get(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG));
		assertEquals(TEST_CLIENT, ((DefaultKafkaConsumerFactory<?,?>)connector.getConsumerFactory()).getConfigurationProperties().get(CommonClientConfigs.CLIENT_ID_CONFIG));
		assertEquals(TEST_CLIENT, ((Map<String,Object>)ReflectionTestUtils.getField(connector.getProducerFactory(),"configs")).get(CommonClientConfigs.CLIENT_ID_CONFIG));
	}
}
