package org.cohorte.lib.slf4j;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.impl.OSGiLogFactory;

/**
 * @author ogattaz
 * 
 */
public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {

		Bundle wBundle = context.getBundle();
		System.out.printf("%50s | Bundle=[%50s][%s] started\n","Activator.start()",
				wBundle.getSymbolicName(), wBundle.getVersion());

		/*
		 * You can then use the resulting .jar in your project along with the
		 * normal slf4j lib. Also this line has to be called in the OSGi
		 * Activator:
		 * 
		 * so that the adapter can forward the log messages to the LogService.
		 * 
		 * @see https://github.com/entrusc/slf4j-osgi
		 */
		OSGiLogFactory.initOSGi(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {

		System.out.printf("%50s | Bundle=[%50s] stopped\n","Activator.stop()", context
				.getBundle().getSymbolicName());
	}
}
