package info.zak_raw.rtpitannya;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Main
		extends HttpServlet implements Comparator<Status> {
	
	//------------- Constants ----------------------------------
	private static final long serialVersionUID = 20120821L;
	
	//------------- Fields -------------------------------------
	private final TwitterFactory twitterFactory;
	
	//------------- Constructors -------------------------------
	public Main() {
		
		this.twitterFactory = new TwitterFactory();
	}
	
	//------------- Methods ------------------------------------
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response )
			throws IOException {
		
		boolean retweeted = this.retweet();
		
		response.setContentType( "text/plain" );
		response.setCharacterEncoding( "UTF-8" );
		response.getWriter().println( retweeted ? "Retweet" : "Not found" );
	}

	private boolean retweet() {
		
		Account account = new Account( "pitan" );
		long lastTweetId = account.getLastTweetId();
		boolean retweeted = false;
		try {
			ResponseList<Status> statuses =
					this.twitterFactory.getInstance().getUserTimeline( "pitan" );
			Collections.sort( statuses, this );
			
			for ( Status status : statuses ) {
				if ( lastTweetId < status.getId() ) {
					lastTweetId = status.getId();
					retweeted = this.retweet( status );
				}
			}
			
			account.updateLastTweetId( lastTweetId );
			
			return retweeted;
		}
		catch ( TwitterException e ) {
			e.printStackTrace();
			
			return false;
		}
	}
	
	private boolean retweet( Status status ) throws TwitterException {
		
		if ( status.getText().contains( "にゃー" ) ) {
			Twitter twitter = this.twitterFactory.getInstance();
			twitter.retweetStatus( status.getId() );
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public int compare( Status status1, Status status2 ) {
		
		long id1 = status1.getId();
		long id2 = status2.getId();
		
		if ( id1 < id2 ) return -1;
		if ( id2 < id1 ) return 1;
		
		return 0;
	}
	
}
