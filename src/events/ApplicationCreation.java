package events;

import bp.Event;

/**
 * An event that is requested (with high priority) whenever backoffice_producer creates an application
 */
@SuppressWarnings("serial")
public class ApplicationCreation extends Event {

	/**
	 * Name of the application
	 */
	public String application_name;


	/**
	 * Constructor.
	 * 
	 * @param application_name
	 *            Name of the application created
	 */
	public ApplicationCreation(String application_name) {
		super();
		this.application_name = application_name;
		this.setName("ApplicationCreation(" + application_name+ ")");
	}

	/**
	 * @see java.lang.Object#toString()
	 */

}
