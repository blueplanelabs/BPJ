package xyz.blueplane.bThreads;

import xyz.blueplane.events.BackofficeConsumerFinished;
import bp.BThread;
import bp.eventSets.EventsOfClass;
import bp.exceptions.BPJRequestableSetException;

import static bp.BProgram.labelNextVerificationState;
import static bp.BProgram.markNextVerificationStateAsHot;
import static bp.eventSets.EventSetConstants.none;
import static bp.eventSets.EventSetConstants.all;


@SuppressWarnings("serial")
public class LivenessCheck extends BThread {

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		
		
		labelNextVerificationState("0");		
		bp.bSync(none, new EventsOfClass(BackofficeConsumerFinished.class), none);		
		
		// if (!coreDb.queryConfirmedApplications().includes(backofficeDb.queryConfirmedApplications())) {
		if (true) {
			markNextVerificationStateAsHot();
		}		

		labelNextVerificationState("1");
		
		bp.bSync(none, none, all);
	}

}
