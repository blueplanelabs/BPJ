package xyz.blueplane.bThreads;

import static bp.BProgramControls.globalRunMode;
import static bp.BProgram.labelNextVerificationState;
import static bp.BProgram.markNextVerificationStateAsBad;
import static bp.eventSets.EventSetConstants.none;
import static bp.eventSets.EventSetConstants.all;
import xyz.blueplane.events.ApplicationCreation;
import xyz.blueplane.events.BackofficeApplicationSave;
import xyz.blueplane.events.ApplicationCommit;
import xyz.blueplane.events.CoreQueueMessagePublish;
import bp.BProgram;
import bp.BThread;
import bp.RunMode;
import bp.eventSets.EventsOfClass;
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

		//markNextVerificationStateAsHot();
		labelNextVerificationState("0");

		// Request for the application creation event
		bp.bSync(none, new EventsOfClass(ApplicationCreation.class), none);

		//markNextVerificationStateAsHot();
		labelNextVerificationState("1");

		// Request the Backoffice DB save the application
		bp.bSync(new BackofficeApplicationSave(application, "PENDING"), none, none);

		//markNextVerificationStateAsHot();
		labelNextVerificationState("2");
		// Request the core queue publish the message
		bp.bSync(new CoreQueueMessagePublish(application, "CREATE_APPLICATION"), none, none);

		//markNextVerificationStateAsHot();
		labelNextVerificationState("3");
		// Forces backtracking after all b-threads reach the next state
		if (globalRunMode == RunMode.MCSAFETY || globalRunMode == RunMode.MCLIVENESS) {
			//bp.pruneAtNextVerificationState();
		}
		
		// Request the Backoffice DB commit the applicaton
		bp.bSync(new ApplicationCommit(application), none, none);

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