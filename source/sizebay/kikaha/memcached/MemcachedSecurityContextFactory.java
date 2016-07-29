package sizebay.kikaha.memcached;

import javax.inject.*;
import io.undertow.server.HttpServerExchange;
import kikaha.core.modules.security.*;

/**
 *
 */
@Singleton
public class MemcachedSecurityContextFactory implements SecurityContextFactory {

	@Inject MemcachedSessionStore  store;

	@Override
	public SecurityContext createSecurityContextFor( HttpServerExchange exchange, AuthenticationRule rule ) {
		return new DefaultSecurityContext( rule, exchange, store );
	}
}
