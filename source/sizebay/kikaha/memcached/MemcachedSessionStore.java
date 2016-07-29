package sizebay.kikaha.memcached;

import java.util.concurrent.ExecutionException;
import javax.inject.*;
import io.undertow.server.HttpServerExchange;
import kikaha.core.modules.security.*;
import net.spy.memcached.MemcachedClient;

/**
 * A {@link SessionStore} that persists {@link Session} on a Memcached database.
 */
@Singleton
public class MemcachedSessionStore implements SessionStore {

	static final int EXPIRES_IN_30_DAYS = 60*60*24*30;

	final SessionCookie cookie = new SessionCookie("JSESSIONID");

	@Inject MemcachedClient memcached;

	@Override
	public Session createOrRetrieveSession(HttpServerExchange exchange) {
		final String sessionId = cookie.retrieveSessionIdFrom( exchange, SessionIdGenerator::generate );
		Session session = getSessionFromCache( sessionId );
		if ( session == null ) {
			session = new DefaultSession(sessionId);
			if ( !insert( sessionId, session ) )
				session = getSessionFromCache( sessionId );
			cookie.attachSessionCookie( exchange, session.getId() );
		}
		return session;
	}

	private Session getSessionFromCache(String sessionId) {
		return (Session)memcached.get( sessionId );
	}

	private boolean insert(String sessionId, Session session){
		try {
			return memcached.add( sessionId, EXPIRES_IN_30_DAYS, session ).get();
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		} catch (ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void flush(Session session) {
		try {
			memcached.set( session.getId(), EXPIRES_IN_30_DAYS, session ).get();
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		} catch (ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void invalidateSession(Session session) {
		memcached.delete( session.getId() );
	}
}
