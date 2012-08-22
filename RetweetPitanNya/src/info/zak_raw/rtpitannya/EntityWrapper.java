package info.zak_raw.rtpitannya;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

abstract class EntityWrapper {

	//------------- Fields -------------------------------------
	private final Key key;
	private Entity entity;
	
	//------------- Constructors -------------------------------
	public EntityWrapper( String kind, String name ) {
		
		this.key = KeyFactory.createKey( kind, name );
	}
	
	//------------- Methods ------------------------------------
	public abstract Entity createDefaultEntity( Key key );
	
	private Entity getEntity() {
		
		if ( this.entity == null ) {
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			try {
				this.entity = datastore.get( this.key );
			}
			catch ( EntityNotFoundException e ) {
				this.entity = this.createDefaultEntity( this.key );
				datastore.put( this.entity );
			}
		}
		
		return this.entity;
	}
	
	public String getString( String propertyName ) {
		
		Object value = this.getEntity().getProperty( propertyName );
		
		return ( value == null ) ? "" : value.toString();
	}
	
	public long getLong( String propertyName ) {
		
		Object value = this.getEntity().getProperty( propertyName );
		
		if ( value instanceof Number ) {
			return ( (Number) value ).longValue();
		}
		else {
			return 0;
		}
	}
	
	public void update( String propertyName, Object value ) {
		
		this.getEntity().setProperty( propertyName, value );
		DatastoreServiceFactory.getDatastoreService().put( this.getEntity() );
	}
	
}
