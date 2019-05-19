package demo.util;

import java.security.SecureRandom;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
public class SessionListener implements HttpSessionListener {

	private static SecureRandom SECURE_RANDOM = new SecureRandom(); // better also use SecureRandom guidelines of re-seeding etc. for stronger PRNG
	
	public static final String SENSITIVE_ACTIONS_TOKEN = "SENSITIVE_ACTIONS_TOKEN";
	
    /**
     * Default constructor. 
     */
    public SessionListener() {
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event) {
	    	System.out.println("Session created");
	    	byte[] bytes = new byte[32];
	    	SECURE_RANDOM.nextBytes(bytes);
	    	final String secret = bytesToHex(bytes);
	    	event.getSession().setAttribute(SENSITIVE_ACTIONS_TOKEN, secret);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent event) {
    }
	
    
    final protected static char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
