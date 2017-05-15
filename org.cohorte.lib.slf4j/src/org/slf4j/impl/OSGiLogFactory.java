package org.slf4j.impl;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * OSGiLogFactory
 * 
 * @author ogattaz
 * @author Florian Frankenberger
 * @see https://github.com/entrusc/slf4j-osgi
 */
public class OSGiLogFactory implements ILoggerFactory {

	private static volatile BundleContext bundleContext;

	private static volatile Logger logger = new EmptyLogger();

	private static final ServiceListener sServiceListener = new ServiceListener() {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.osgi.framework.ServiceListener#serviceChanged(org.osgi.framework
		 * .ServiceEvent)
		 */
		@Override
		public void serviceChanged(final ServiceEvent se) {
			switch (se.getType()) {
			case ServiceEvent.REGISTERED:
				onServiceAvailable((LogService) bundleContext.getService(se
						.getServiceReference()));
				break;
			case ServiceEvent.UNREGISTERING:
				onServiceLost();
				break;
			default:
				// doesn't matter - but checkstyle wants it
				break;
			}
		}

	};

	/**
	 * @param context
	 */
	public static void initOSGi(final BundleContext context) {

		System.out.printf("%50s | Bundle=[%50s]\n", "OSGiLogFactory.init()",
				context.getBundle().getSymbolicName());

		bundleContext = context;
		final String serviceName = LogService.class.getName();
		ServiceReference<?> controlReference = context
				.getServiceReference(serviceName);
		if (controlReference != null) {
			LogService wLogService = (LogService) context
					.getService(controlReference);
			wLogService
					.log(LogService.LOG_INFO,
							String.format(
									"OSGiLogFactory.initOSGi(): retreive ServiceReference [%s]\n",
									controlReference));
			onServiceAvailable(wLogService);
		}

		try {

			System.out.printf("%50s | service listener on [%s]\n",
					"OSGiLogFactory.init()", serviceName);

			context.addServiceListener(sServiceListener, "(objectclass="
					+ serviceName + ")");
		} catch (InvalidSyntaxException ex) {
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * @param logService
	 */
	private static void onServiceAvailable(final LogService logService) {
		logger = new OSGiLogger(logService);
		logger.info(
				"OSGiLogFactory.onServiceAvailable(): set logger as [%s]\n",
				logger);
	}

	/**
     * 
     */
	private static void onServiceLost() {
		logger = new EmptyLogger();
		logger.info("OSGiLogFactory.onServiceLost(): set logger as [%s]\n",
				logger);
	}

	public OSGiLogFactory() {
		super();
		System.out.printf("OSGiLogFactory.<init>: instanciated\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.ILoggerFactory#getLogger(java.lang.String)
	 */
	@Override
	public Logger getLogger(final String name) {
		return logger;
	}

}
