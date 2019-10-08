package bThreads;

import static BProgram.bp;
import static bp.BProgram.labelNextVerificationState;
import static bp.eventSets.EventSetConstants.none;
import static bp.eventSets.EventsOfClass;


import events.CoreQueueMessagePublish;
import events.CoreApplicationSave;
import events.BackofficeQueueMessagePublish;
import bp.BThread;
import bp.exceptions.BPJException;
/**
 * A scenario that make the process of management of message of "create_application"
 *  received in core queue, saving application in core_db and publishing message
 * "application_created" in backoffice queue
 */
@SuppressWarnings("serial")
public class Core extends BThread {

	private String application;


	@Override
	public void runBThread() throws BPJException {
//		interruptingEvents = new EventSet(gameOver);

		labelNextVerificationState("0");
		// Wait for the CoreMessagePublish event
        bp.bSync(none, EventOfClass(CoreQueueMessagePublish), none);
        application = bp.lastEvent.application_name;

		markNextVerificationStateAsHot();
		labelNextVerificationState("1");
		// Request the Core DB save the application
		bp.bSync(new CoreApplicationSave(application, "CONFIRMED"), none, none);

		markNextVerificationStateAsHot();
		labelNextVerificationState("2");
		// Request the Backoffice queue publish the message
		bp.bSync(new BackofficeQueueMessagePublish(application, "CREATE_APPLICATION"), none, none);

		labelNextVerificationState("3");
		// Finel state for model-checking
        bp.bSync(none, none, none);
		
	}

	/**
	 * @param application
	 */
	public Core() {
		super();

		this.setName("Core()");
	}

	}

}