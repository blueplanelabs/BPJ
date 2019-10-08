package xyz.blueplane.events;

import bp.Event;

/**
 * An event that is requested (with high priority) whenever backoffice_db commit an application
 */
@SuppressWarnings("serial")
public class ApplicationCommit extends Event {
	
	/**
	 * Name of the application
	 */
	public String application_name;


	/**
	 * Constructor.
	 * 
	 * @param application_name
	 *            Name of the application commited
	 */
	public ApplicationCommit(String application_name) {
		super();
		this.application_name = application_name;
		this.setName("ApplicationCommit(" + application_name+ ")");
	}

	/**
	 * @see java.lang.Object#toString()
	 */


}
