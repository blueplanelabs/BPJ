package bThreads;

import static BProgram.bp;
import static bp.BProgram.labelNextVerificationState;
import static bp.BProgram.markNextVerificationStateAsHot;;
import static bp.eventSets.EventSetConstants.none;


import events.ApplicationCreation;
import events.BackofficeApplicationSave;
import events.ApplicationCommit;
import events.CoreQueueMessagePublish;
import bp.BThread;
import bp.exceptions.BPJException;
/**
 * A scenario that make the process of application creation in the backoffice (save application in backoffice db
 * publish message in core queue and commit application in backoffice db
 */
@SuppressWarnings("serial")
public class BackofficeProduceApplication extends BThread {

	private String application;


	@Override
	public void runBThread() throws BPJException {
//		interruptingEvents = new EventSet(gameOver);

		markNextVerificationStateAsHot();
		labelNextVerificationState("0");

		// Request for the application creation event
		bp.bSync(new ApplicationCreation(application), none, none);

		markNextVerificationStateAsHot();
		labelNextVerificationState("1");

		// Request the Backoffice DB save the application
		bp.bSync(new BackofficeApplicationSave(application, "PENDING"), none, none);

		markNextVerificationStateAsHot();
		labelNextVerificationState("2");
		// Request the core queue publish the message
		bp.bSync(new CoreQueueMessagePublish(application, "CREATE_APPLICATION"), none, none);

		markNextVerificationStateAsHot();
		labelNextVerificationState("3");
		// Request the Backoffice DB commit the applicaton
		bp.bSync(new BackofficeCommit(application), none, none);

		labelNextVerificationState("4");
		// Final state for model-checking
		bp.bSync(none, none, none);
		
	}

	/**
	 * @param application
	 */
	public BackofficeProduceApplication(String application) {
		super();
		this.application = application;

		this.setName("BackofficeProduceApplication(" + application + ")");
	}

	}

}