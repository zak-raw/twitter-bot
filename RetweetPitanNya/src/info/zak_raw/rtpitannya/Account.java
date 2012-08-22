package info.zak_raw.rtpitannya;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;

class Account extends EntityWrapper {

	//------------- Constants ----------------------------------
	private static final String KIND = "info.zak_raw.rtpitannya.accounts";
	private static final String LAST_TWEET_ID = "last_tweet_id";
	
	//------------- Constructors -------------------------------
	public Account( String name ) {
		
		super( KIND, name );
	}
	
	//------------- Methods ------------------------------------
	@Override
	public Entity createDefaultEntity( Key key ) {
		
		Entity entity = new Entity( key );
		entity.setProperty( LAST_TWEET_ID, 0 );
		
		return entity;
	}

	public long getLastTweetId() {
		
		return this.getLong( LAST_TWEET_ID );
	}
	
	public void updateLastTweetId( long lastTweetId ) {
		
		this.update( LAST_TWEET_ID, lastTweetId );
	}
	
}
