package xyz.blueplane.events;

import bp.Event;

/**
 * An event that is requested (with high priority) whenever core_queue publish a message
 */
@SuppressWarnings("serial")
public class BackofficeQueueMessagePublish extends Event {
	
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
	public BackofficeQueueMessagePublish(String application_name, String message) {
		super();
		this.application_name = application_name;
		this.message = message;
		this.setName("BackofficeQueueMessagePublish(" + application_name+ "," + message + ")");
	}

	/**
	 * @see java.lang.Object#toString()
	 */


}
