
package com.zbjdl.boss.admin.frame.utils.web;


public class ExceptionInfo {
	private Throwable exception;
	private String exceptionCode;
	private String exceptionId;
	private String exceptionClassInfo;
	private String exceptionMessage;
	private String exceptionText;
	private boolean systemError = true;
	
	
	public String getExceptionClassInfo() {
		return exceptionClassInfo;
	}
	public void setExceptionClassInfo(String exceptionClassInfo) {
		this.exceptionClassInfo = exceptionClassInfo;
	}
	public Throwable getException() {
		return exception;
	}
	public void setException(Throwable exception) {
		this.exception = exception;
	}
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	public String getExceptionId() {
		return exceptionId;
	}
	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public boolean isSystemError() {
		return systemError;
	}
	public void setSystemError(boolean systemError) {
		this.systemError = systemError;
	}
	public String getExceptionText() {
		return exceptionText;
	}
	public void setExceptionText(String exceptionText) {
		this.exceptionText = exceptionText;
	}
}
