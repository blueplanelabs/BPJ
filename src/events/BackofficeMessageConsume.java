package events;

import bp.Event;

/**
 * An event that is requested (with high priority) whenever core_queue consume a message
 */
@SuppressWarnings("serial")a
public class BackofficeMessageConsume extends Events{
	
	/**
	 * Name of the application
	 */
	public String application_name;
	
	/**
	 * Message
	 */
	public String message;


	/**
	 * Constructor.
	 * 
	 * @param application_name
	 *            Name of the application
	 * @param message
	 *            message
	 */
	public BackofficeMessageConsume(String application_name, String message) {
		super();
		this.application_name = application_name;
		this.message = message;
		this.setName("BackofficeMessageConsume(" + application_name+ "," + message + ")");
	}

	/**
	 * @see java.lang.Object#toString()
	 */


}
