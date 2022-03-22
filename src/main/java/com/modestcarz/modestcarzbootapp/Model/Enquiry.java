package com.modestcarz.modestcarzbootapp.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_MODEST_ENQUIRY")
public class Enquiry {
	
	private Long enqId;
	private String name;
	private String emailAddr;
	private String enqSubject;
	private String enqContent;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "TME_ID")
	public Long getEnqId() {
		return enqId;
	}
	public void setEnqId(Long enqId) {
		this.enqId = enqId;
	}
	@Column(name = "TME_NAME", nullable = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "TME_EMAIL_ADDR", nullable = true)
	public String getEmailAddr() {
		return emailAddr;
	}
	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}
	@Column(name = "TME_ENQ_SUBJECT", nullable = true)
	public String getEnqSubject() {
		return enqSubject;
	}
	public void setEnqSubject(String enqSubject) {
		this.enqSubject = enqSubject;
	}
	@Column(name = "TME_ENQ_CONTENT", nullable = true)
	public String getEnqContent() {
		return enqContent;
	}
	public void setEnqContent(String enqContent) {
		this.enqContent = enqContent;
	}
	@Override
	public String toString() {
		return "Enquiry [enqId=" + enqId + ", name=" + name + ", emailAddr=" + emailAddr + ", enqSubject=" + enqSubject
				+ ", enqContent=" + enqContent + "]";
	}

}
