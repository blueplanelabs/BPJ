package xyz.blueplane.externalApp;

// Main program for running ContestiaBPJ for playing and for model-checking.
// model-checking run configuration
// -Dsearch=BFS
// 

import static bp.BProgram.bp;
import static bp.BProgramControls.globalRunMode;
import xyz.blueplane.bThreads.BackofficeProduceApplication;
import xyz.blueplane.bThreads.Core;
import xyz.blueplane.bThreads.BackofficeConsumer;
import xyz.blueplane.bThreads.LivenessCheck;
import xyz.blueplane.environment.InitialEvents;
import bApplication.BApplication;
import bp.BProgram;
import bp.RunMode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The main entry point to the ContestiaBPJ program.
 */
public class ContestiaBPJ implements BApplication { 

    private static Log log = LogFactory.getLog(ContestiaBPJ.class);
    
	/**
	 * Main program entry point
	 * 
	 * @param args
	 *            Command line parameters (ignored)
	 */

	static public void main(String arg[]) {
		try {

			BProgram.startBApplication(ContestiaBPJ.class, "xyz");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runBApplication() {
		String application = "Prueba";
		if (globalRunMode == RunMode.MCLIVENESS) {
			bp.add(new LivenessCheck(), 100.0);
		}
		bp.add(new InitialEvents(application), 1.001);
		bp.add(new BackofficeProduceApplication(application), 1.002); 		
		bp.add(new Core(), 1.003);
		bp.add(new BackofficeConsumer(), 1.004);

		// Start the scenarios
		bp.setBThreadEpsilon(1.0);
		bp.startAll();
	}

}
