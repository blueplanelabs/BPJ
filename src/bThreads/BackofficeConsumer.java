package bThreads;

import static BProgram.bp;
import static bp.BProgram.labelNextVerificationState;
import static bp.eventSets.EventSetConstants.none;
import static bp.eventSets.EventsOfClass;


import events.BackofficeQueueMessagePublish;;
import events.BackofficeApplicationSave;
import bp.BThread;
import bp.exceptions.BPJException;
/**
 * A scenario that make the process of management of message of "create_application"
 *  received in backoffice queue, saving application in backoffice_db
 */
@SuppressWarnings("serial")
public class BackofficeConsumer extends BThread {

	private String application;


	@Override
	public void runBThread() throws BPJException {
//		interruptingEvents = new EventSet(gameOver);

		labelNextVerificationState("0");
		// Wait for the BackofficeMessagePublish event
        bp.bSync(none, EventOfClass(BackofficeQueueMessagePublish), none);
        application = bp.lastEvent.application_name;

		markNextVerificationStateAsHot();
		labelNextVerificationState("1");
		// Request the Backoffice DB save the application
		bp.bSync(new BackofficeApplicationSave(application, "CONFIRMED"), none, none);

		labelNextVerificationState("2");
		// Finel state for model-checking
        bp.bSync(none, none, none);
		
	}

	/**
	 * @param application
	 */
	public BackofficeConsumer() {
		super();

		this.setName("Backoffice()");
	}

	}

}