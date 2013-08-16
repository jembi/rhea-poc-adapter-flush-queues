package org.openmrs.module.rheapocadapterflushqueues;

public class FlushQueuesResult {
	
	private boolean status;
	private int countArchive = 0;
	private int countProcessing = 0;
	private int countError = 0;
	private int totalArchive = 0;
	private int totalProcessing = 0;
	private int totalError = 0;
	
	public int getCountArchive() {
		return countArchive;
	}

	public void setCountArchive(int countArchive) {
		this.countArchive = countArchive;
	}

	public int getCountProcessing() {
		return countProcessing;
	}

	public void setCountProcessing(int countProcessing) {
		this.countProcessing = countProcessing;
	}

	public int getCountError() {
		return countError;
	}

	public void setCountError(int countError) {
		this.countError = countError;
	}
	
	public int getTotalArchive() {
		return totalArchive;
	}

	public void setTotalArchive(int totalArchive) {
		this.totalArchive = totalArchive;
	}

	public int getTotalProcessing() {
		return totalProcessing;
	}

	public void setTotalProcessing(int totalProcessing) {
		this.totalProcessing = totalProcessing;
	}

	public int getTotalError() {
		return totalError;
	}

	public void setTotalError(int totalError) {
		this.totalError = totalError;
	}

	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}


}
