package org.openmrs.module.rheapocadapterflushqueues;

public class FlushQueuesResult {
	
	private boolean status = false;
	private int count = 0;
	private int countArchive = 0;
	private int countProcessing = 0;
	private int countError = 0;
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

	public int getCountBackEntered() {
		return countBackEntered;
	}

	public void setCountBackEntered(int countBackEntered) {
		this.countBackEntered = countBackEntered;
	}

	private int countBackEntered = 0;	

	public boolean getStatus() {
		return status;
	}
	
	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
