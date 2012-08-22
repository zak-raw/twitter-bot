package info.zak_raw.rtpitannya;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Test extends HttpServlet {

	private static final long serialVersionUID = 20120821L;
	
	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response )
			throws ServletException, IOException {
		
		response.setContentType( "text/plain" );
		
		TwitterFactory factory = new TwitterFactory();
		Twitter twitter = factory.getInstance();
		try {
			twitter.updateStatus( "テスト" );
			response.getWriter().println( "OK" );
		}
		catch ( TwitterException e ) {
			e.printStackTrace();
			response.getWriter().println( e.getMessage() );
		}
	}

}
