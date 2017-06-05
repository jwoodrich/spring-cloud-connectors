package org.springframework.cloud.service.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.service.ServiceInfo.ServiceLabel;
import org.springframework.cloud.service.UriBasedServiceInfo;

/**
 *
 * @author Jason Woodrich
 *
 */
@ServiceLabel("kafka")
public class KafkaServiceInfo extends UriBasedServiceInfo {
	private static final String DEFAULT_ENCODING="US-ASCII";
	private Map<String,Object> config=new HashMap<String,Object>();
	public static final String KAFKA_SCHEME = "kafka";

	public KafkaServiceInfo(String id, String host, int port, Map<String,Object> config) {
		super(id, KAFKA_SCHEME, host, port, null, null, null);
		if (config!=null) {
			this.config.putAll(config);
		}
	}
	public KafkaServiceInfo(String id, String uri) {
		this(id,uri,DEFAULT_ENCODING);
		validate();
	}
	protected void validate() {
		if (getHost()==null && getConfig().get("bootstrap.servers")==null) {
			throw new IllegalArgumentException("Host or bootstrap.servers is required!");
		}
	}

	public KafkaServiceInfo(String id, String uri, String encoding) {
		super(id, uri);
		try {
			parseQuery(getQuery(),encoding);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Encoding "+encoding+" is not supported.",e);
		}
	}
	public void parseQuery(String query, String encoding) throws UnsupportedEncodingException {
		if (query!=null) {
		    String[] pairs = query.split("&");
		    for (String pair : pairs) {
		        int idx = pair.indexOf("=");
		        config.put(URLDecoder.decode(pair.substring(0, idx), encoding), URLDecoder.decode(pair.substring(idx + 1), encoding));
		    }
		}
	}

	@ServiceProperty(category = "connection")
	public Map<String, Object> getConfig() {
		return config;
	}

	public void setConfig(Map<String, Object> config) {
		this.config = config;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + "[ uri=" + getScheme() + "://" + getHost() + ":" + getPort()+" config="+getConfig()+" ]";
	}
}
