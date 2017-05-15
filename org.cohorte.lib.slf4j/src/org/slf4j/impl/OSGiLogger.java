package org.slf4j.impl;

import java.util.logging.Level;

import org.osgi.service.log.LogService;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;

/**
 * The actual logger to pass the entries to the OSGi logging service.
 * 
 * This class is based on code of http://code.google.com/p/osgi-logging/ under
 * Apache 2.0 license. Some modifications for more recent SLF4J done.
 * 
 * @author ogattaz
 * @author Florian Frankenberger
 * @see https://github.com/entrusc/slf4j-osgi
 */
public class OSGiLogger extends MarkerIgnoringBase {

	private static final long serialVersionUID = -1924980932989101490L;

	private final LogService pLogService;

	/**
	 * @param aLogService
	 */
	public OSGiLogger(LogService aLogService) {
		this.pLogService = aLogService;
		printLog(LogService.LOG_INFO,
				String.format(
						"OSGiLogger<init>: instanciated - LogService=[%s]",
						aLogService));
	}

	/**
	 * @param aOSGiLevel
	 * @return
	 */
	private Level osgiLevelToLevel(int aOSGiLevel) {
		switch (aOSGiLevel) {
		case LogService.LOG_DEBUG:
			return Level.FINE;
		case LogService.LOG_INFO:
			return Level.INFO;
		case LogService.LOG_WARNING:
			return Level.WARNING;
		case LogService.LOG_ERROR:
		default:
			return Level.SEVERE;
		}
	}

	private static final int LEVEL_LIB_LEN = Level.WARNING.getName().length();

	/**
	 * @param aOSGiLevel
	 * @return
	 */
	private String osgiLevelToLevelLib(int aOSGiLevel) {
		String wLevelLib = osgiLevelToLevel(aOSGiLevel).getName();
		while (wLevelLib.length() < LEVEL_LIB_LEN) {
			wLevelLib += ' ';
		}
		return wLevelLib;
	}

	/**
	 * @param e
	 * @return
	 */
	private String throwableToLib(Throwable e) {
		StringBuilder wSB = new StringBuilder();
		int wNbLevel = 0;
		while (e != null) {
			if (wNbLevel != 0) {
				wSB.append("; ");
			}
			String wMess = e.getLocalizedMessage();
			if (wMess != null) {
				wSB.append(String.format("%s,", wMess));
			}
			wSB.append(String.format("%s ", e.getClass().getSimpleName()));
			e = e.getCause();
			wNbLevel++;
		}
		return wSB.toString();
	}

	/**
	 * @param level
	 * @param message
	 */
	private void printLog(int level, Object message) {
		printLog(level, message, null);
	}

	/**
	 * @param level
	 * @param message
	 * @param t
	 */
	private void printLog(int level, Object message, Throwable t) {
		if (t != null) {
			System.out.format("%s | %s | %s\n", osgiLevelToLevelLib(level),
					throwableToLib(t), message);
		} else {
			System.out
					.format("%s | %s \n", osgiLevelToLevelLib(level), message);
		}
	}

	/**
	 * Logs to OSGi
	 * 
	 * @param level
	 * @param message
	 * @param t
	 */
	private final void internalLog(int level, Object message, Throwable t) {


		try {
			if (t != null) {
				pLogService.log(null, level, message.toString(), t);
			} else {
				pLogService.log(null, level, message.toString());
			}
		} catch (Exception exc) {
			/*
			 * Service may have become invalid, just ignore any error until the
			 * log service reference is updated by the log factory.
			 */
			if (level <= LogService.LOG_INFO) {
				printLog(level, message, t);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#isTraceEnabled()
	 */
	@Override
	public boolean isTraceEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#trace(java.lang.String)
	 */
	@Override
	public void trace(String msg) {
		internalLog(LogService.LOG_DEBUG, msg, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Object)
	 */
	@Override
	public void trace(String format, Object arg) {
		String msgStr = MessageFormatter.format(format, arg).getMessage();
		internalLog(LogService.LOG_DEBUG, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void trace(String format, Object arg1, Object arg2) {
		String msgStr = MessageFormatter.format(format, arg1, arg2)
				.getMessage();
		internalLog(LogService.LOG_DEBUG, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void trace(String format, Object... argArray) {
		String msgStr = MessageFormatter.arrayFormat(format, argArray)
				.getMessage();
		internalLog(LogService.LOG_DEBUG, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void trace(String msg, Throwable t) {
		internalLog(LogService.LOG_DEBUG, msg, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#isDebugEnabled()
	 */
	@Override
	public boolean isDebugEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#debug(java.lang.String)
	 */
	@Override
	public void debug(String msg) {
		internalLog(LogService.LOG_DEBUG, msg, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#debug(java.lang.String, java.lang.Object)
	 */
	@Override
	public void debug(String format, Object arg) {
		String msgStr = MessageFormatter.format(format, arg).getMessage();
		internalLog(LogService.LOG_DEBUG, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#debug(java.lang.String, java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void debug(String format, Object arg1, Object arg2) {
		String msgStr = MessageFormatter.format(format, arg1, arg2)
				.getMessage();
		internalLog(LogService.LOG_DEBUG, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#debug(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void debug(String format, Object... argArray) {
		String msgStr = MessageFormatter.arrayFormat(format, argArray)
				.getMessage();
		internalLog(LogService.LOG_DEBUG, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#debug(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void debug(String msg, Throwable t) {
		internalLog(LogService.LOG_DEBUG, msg, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#isInfoEnabled()
	 */
	@Override
	public boolean isInfoEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#info(java.lang.String)
	 */
	@Override
	public void info(String msg) {
		internalLog(LogService.LOG_INFO, msg, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#info(java.lang.String, java.lang.Object)
	 */
	@Override
	public void info(String format, Object arg) {
		String msgStr = MessageFormatter.format(format, arg).getMessage();
		internalLog(LogService.LOG_INFO, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#info(java.lang.String, java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void info(String format, Object arg1, Object arg2) {
		String msgStr = MessageFormatter.format(format, arg1, arg2)
				.getMessage();
		internalLog(LogService.LOG_INFO, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#info(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void info(String format, Object... argArray) {
		String msgStr = MessageFormatter.arrayFormat(format, argArray)
				.getMessage();
		internalLog(LogService.LOG_INFO, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#info(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void info(String msg, Throwable t) {
		internalLog(LogService.LOG_INFO, msg, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#isWarnEnabled()
	 */
	@Override
	public boolean isWarnEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#warn(java.lang.String)
	 */
	@Override
	public void warn(String msg) {
		internalLog(LogService.LOG_WARNING, msg, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Object)
	 */
	@Override
	public void warn(String format, Object arg) {
		String msgStr = MessageFormatter.format(format, arg).getMessage();
		internalLog(LogService.LOG_WARNING, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Object,
	 * java.lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void warn(String format, Object arg1, Object arg2) {
		String msgStr = MessageFormatter.format(format, arg1, arg2)
				.getMessage();
		internalLog(LogService.LOG_WARNING, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void warn(String format, Object... argArray) {
		String msgStr = MessageFormatter.arrayFormat(format, argArray)
				.getMessage();
		internalLog(LogService.LOG_WARNING, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warn(String msg, Throwable t) {
		internalLog(LogService.LOG_WARNING, msg, t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#isErrorEnabled()
	 */
	@Override
	public boolean isErrorEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#error(java.lang.String)
	 */
	@Override
	public void error(String msg) {
		internalLog(LogService.LOG_ERROR, msg, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Object)
	 */
	@Override
	public void error(String format, Object arg) {
		String msgStr = MessageFormatter.format(format, arg).getMessage();
		internalLog(LogService.LOG_ERROR, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void error(String format, Object arg1, Object arg2) {
		String msgStr = MessageFormatter.format(format, arg1, arg2)
				.getMessage();
		internalLog(LogService.LOG_ERROR, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void error(String format, Object... argArray) {
		String msgStr = MessageFormatter.arrayFormat(format, argArray)
				.getMessage();
		internalLog(LogService.LOG_ERROR, msgStr, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void error(String msg, Throwable t) {
		internalLog(LogService.LOG_ERROR, msg, t);
	}
}
