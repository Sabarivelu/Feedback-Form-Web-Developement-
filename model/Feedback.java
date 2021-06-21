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
 *6:54:43 PM
 */
@Entity
public class Feedback {

	/**
	 * @param args
	 */
	@Id @GeneratedValue
	private int fid;
	
	@Column
	private String name;
	private int rating;
	
	/**
	 * @param name
	 * @param rating
	 * @param feedback
	 */
	public Feedback(String name, int rating, String feedback) {
		super();
		this.name = name;
		this.rating = rating;
		this.feedback = feedback;
	}
	
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
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	private String feedback;
}
