package xyz.blueplane.bThreads;

import static bp.BProgramControls.globalRunMode;
import static bp.BProgram.labelNextVerificationState;
import static bp.BProgram.markNextVerificationStateAsHot;
import static bp.eventSets.EventSetConstants.none;
import static bp.eventSets.EventSetConstants.all;
import bp.eventSets.EventsOfClass;
import xyz.blueplane.events.BackofficeConsumerFinished;
import xyz.blueplane.events.BackofficeQueueMessagePublish;
import xyz.blueplane.events.BackofficeApplicationSave;
import bp.BThread;
import bp.RunMode;
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
        bp.bSync(none, new EventsOfClass(BackofficeQueueMessagePublish.class), none);
        if (bp.lastEvent != null) { 
          System.out.println("lastEvent: " + bp.lastEvent);
          application = ((BackofficeQueueMessagePublish) bp.lastEvent).application_name;
        }
		//markNextVerificationStateAsHot();
		labelNextVerificationState("1");		
		// Request the Backoffice DB save the application
		BackofficeApplicationSave event = new BackofficeApplicationSave(application, "CONFIRMED");
		bp.bSync(event, none, none);		
		
		//event.run();
		/*
		if (!coreDb.queryConfirmedApplications().includes(backofficeDb.queryConfirmedApplications())) {
			markNextVerificationStateAsHot();
		}
		*/
		
		
		labelNextVerificationState("2");		
		// Forces backtracking after all b-threads reach the next state
		if (globalRunMode == RunMode.MCSAFETY || globalRunMode == RunMode.MCLIVENESS) {
			bp.pruneAtNextVerificationState("BackofficeConsumer finished");
		}		
		bp.bSync(new BackofficeConsumerFinished(), none, none);
		
		// Finel state for model-checking
        bp.bSync(none, none, all);
		
	}

	/**
	 * @param application
	 */
	public BackofficeConsumer() {
		super();

		this.setName("BackofficeConsumer()");
	}

}