package ${rootpackage}.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
@Table(name = "employee")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	@Column(name="EMP_ID")
	private Long empid;
	@NotEmpty
	@Size(min=2, max=40, message="FirstName should have at last 2 charaters")
	@Column(name="FIRSTNAME")
	private String firstname;
	@NotEmpty
	@Size(min=2, max=40, message="LastName should have at last 2 charaters")
	@Column(name="LASTNAME")
	private String lastname;
	@Email(message="emailAddress formatter is not correct")
	@Column(name="EMAILADDRESS")
	private String emailaddress;
	@Column(name="CALCGRP_ID")
	private String calcgroup;
	@NotEmpty(message="SIN can't empty")
	@Column(name="SIN")
	private String sin;	
    
	@ManyToOne
	@JoinColumn(name="PAYGRP_ID")
	private PayGroup payGroup;

	public PayGroup getPayGroup() {
		return payGroup;
	}

	public void setPayGroup(PayGroup payGroup) {
		this.payGroup = payGroup;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getFirstname() {
		return firstname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getLastname() {
		return lastname;
	}
	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	
	public String getEmailaddress() {
		return emailaddress;
	}
	public void setCalcgroup(String calcgroup) {
		this.calcgroup = calcgroup;
	}
	
	public String getCalcgroup() {
		return calcgroup;
	}
	public void setSin(String sin) {

	   this.sin = sin;
	}
	
	public String getSin() {
		return sin;
	}

	public Long getEmpid() {
		return empid;
	}

	public void setEmpid(Long empid) {
		this.empid = empid;
	}


}