package org.slf4j.impl;

import org.slf4j.helpers.MarkerIgnoringBase;

/**
 * Just an empty logger used for the cases where the OSGi logging service is not
 * available
 * 
 * @author ogattaz
 * @author Florian Frankenberger
 * @see https://github.com/entrusc/slf4j-osgi
 */
public class EmptyLogger extends MarkerIgnoringBase {

	private static final long serialVersionUID = 8940293564679182692L;

	/**
	 * 
	 */
	public EmptyLogger() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#isTraceEnabled()
	 */
	@Override
	public boolean isTraceEnabled() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#trace(java.lang.String)
	 */
	@Override
	public void trace(String msg) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Object)
	 */
	@Override
	public void trace(String format, Object arg) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void trace(String format, Object arg1, Object arg2) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#trace(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void trace(String format, Object... arguments) {
	}

	@Override
	public void trace(String msg, Throwable t) {
	}

	@Override
	public boolean isDebugEnabled() {
		return false;
	}

	@Override
	public void debug(String msg) {
	}

	@Override
	public void debug(String format, Object arg) {
	}

	@Override
	public void debug(String format, Object arg1, Object arg2) {
	}

	@Override
	public void debug(String format, Object... arguments) {
	}

	@Override
	public void debug(String msg, Throwable t) {
	}

	@Override
	public boolean isInfoEnabled() {
		return false;
	}

	@Override
	public void info(String msg) {
	}

	@Override
	public void info(String format, Object arg) {
	}

	@Override
	public void info(String format, Object arg1, Object arg2) {
	}

	@Override
	public void info(String format, Object... arguments) {
	}

	@Override
	public void info(String msg, Throwable t) {
	}

	@Override
	public boolean isWarnEnabled() {
		return false;
	}

	@Override
	public void warn(String msg) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Object)
	 */
	@Override
	public void warn(String format, Object arg) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void warn(String format, Object... arguments) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void warn(String format, Object arg1, Object arg2) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#warn(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void warn(String msg, Throwable t) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#isErrorEnabled()
	 */
	@Override
	public boolean isErrorEnabled() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#error(java.lang.String)
	 */
	@Override
	public void error(String msg) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Object)
	 */
	@Override
	public void error(String format, Object arg) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Object,
	 * java.lang.Object)
	 */
	@Override
	public void error(String format, Object arg1, Object arg2) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Object[])
	 */
	@Override
	public void error(String format, Object... arguments) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.slf4j.Logger#error(java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void error(String msg, Throwable t) {
	}

}
