package externalApp;

// Main program for running ContestiaBPJ for playing and for model-checking.
// model-checking run configuration
// -Dsearch=BFS
// 

import static bp.BProgram.bp;
import static bp.BProgramControls.globalRunMode;

import bThreads.BackofficeConsumer;
import bThreads.BackofficeProduceApplication;
import bThreads.Core;

import bApplication.BApplication;
import bp.BProgram;
import bp.BThread;
import bp.RunMode;

/**
 * The main entry point to the ContestiaBPJ program.
 */
public class ContestiaBPJ implements BApplication { 
	
	/**
	 * Main program entry point
	 * 
	 * @param args
	 *            Command line parameters (ignored)
	 */

	static public void main(String arg[]) {
		try {

			BProgram.startBApplication(ContestiaBPJ.class, "ContestiaBPJ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void runBApplication() {
		
		if (globalRunMode == RunMode.MCSAFETY || globalRunMode == RunMode.MCLIVENESS) {

			// Start the MC 


			
		}else{

			// start the playing 

		}
		
		bp.add(new BackofficeProduceApplication("Prueba"), 0.6); 		
		bp.add(new Core(), 15.0);
		bp.add(new BackofficeConsumer(), 30.0)

		// Start the scenarios
		bp.startAll();
	}

}
