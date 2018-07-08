package com.issuetracker.model;

public class TicketPojo {
	String ticketID = "";

	String title = "";

	String start ="";

	String description ="";

	String severity ="";
	
	String dueDate ="";

	String assignedTo ="";
	
	boolean allDay =false;
	
	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
 
	public String getTicketID() {
		return ticketID;
	}

	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	@Override
	public String toString() {
		return "TicketPojo [ticketID=" + ticketID + ", title=" + title + ", start=" + start + ", description="
				+ description + ",  assignedTo=" + assignedTo + ", allDay=" + allDay + "]";
	}

}
