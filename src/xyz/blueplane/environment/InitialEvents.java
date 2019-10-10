package xyz.blueplane.environment;

import static bp.BProgram.labelNextVerificationState;
import static bp.BProgram.markNextVerificationStateAsHot;
import static bp.eventSets.EventSetConstants.none;


import xyz.blueplane.events.ApplicationCreation;

import bp.BThread;
import bp.exceptions.BPJException;
/**
 * A scenario to throw the initial event
 */
@SuppressWarnings("serial")
public class InitialEvents extends BThread {

	private String application;

	@Override
	public void runBThread() throws BPJException {

		
		// Request for the application creation event
		bp.bSync(new ApplicationCreation(application), none, none); 

	
		
	}


	/**
	 * @param application
	 */
	public InitialEvents(String application) {
		super();
		this.application=application;


		this.setName("InitialEvents(" + application + ")");
	}

}	