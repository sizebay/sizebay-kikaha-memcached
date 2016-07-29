package sizebay.kikaha.memcached;

import java.io.IOException;
import java.net.InetSocketAddress;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.inject.*;
import kikaha.config.Config;
import net.spy.memcached.MemcachedClient;

/**
 *
 */
@Singleton
public class MemcachedClientProducer {

	@Inject
	Config config;

	MemcachedClient client;

	@PostConstruct
	public void loadCredentials() throws IOException {
		final InetSocketAddress address = new InetSocketAddress(
				config.getString("server.aws.memcached.endpoint"),
				config.getInteger("server.aws.memcached.port")
		);
		client = new MemcachedClient( address );
	}

	@Produces
	public MemcachedClient produceS3Client(){
		return client;
	}
}
