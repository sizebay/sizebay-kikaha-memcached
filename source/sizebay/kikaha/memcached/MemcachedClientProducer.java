package sizebay.kikaha.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.*;
import kikaha.config.Config;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;

/**
 *
 */
@Slf4j
@Singleton
public class MemcachedClientProducer {

	@Inject
	Config config;

	MemcachedClient client;

	@PostConstruct
	public void loadCredentials() throws IOException {
		final String host = config.getString("server.aws.memcached.endpoint", "127.0.0.1");
		final int port = config.getInteger("server.aws.memcached.port");
		log.info( "Starting connection to Memcached: " + host + ":" + port );

		final InetSocketAddress address = new InetSocketAddress( host, port );
		client = new MemcachedClient( address );
	}

	@Produces
	public MemcachedClient produceS3Client(){
		return client;
	}
}
