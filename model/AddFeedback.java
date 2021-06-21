/**
 * 
 */
package com.exterro.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author sveluswamy
 *8:12:03 PM
 */
@Entity
public class AddFeedback {
	
	@Id @GeneratedValue
	private int fid;
	
	@Column
	private String name;
	private String description;
	private String venue;
	private String time;
	
	public int getFid() {
		return fid;
	}
	public void setFid(int fid) {
		this.fid = fid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @param name
	 * @param description
	 * @param venue
	 * @param time
	 */
	public AddFeedback(String name, String description, String venue, String time) {
		super();
		this.name = name;
		this.description = description;
		this.venue = venue;
		this.time = time;
	}

}
