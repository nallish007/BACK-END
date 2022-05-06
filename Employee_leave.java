package com.te.backendassessment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Employee_leave {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int Employee_ID;
	@Column(length = 50)
	String Leave_Date;
	@Column(length = 50)
	String Leave_Status;
	@ManyToOne(cascade = CascadeType.ALL)
	Employee_Info emp_info;

	@Override
	public String toString() {
		return " [ Leave_Date=" + Leave_Date + ", Leave_Status=" + Leave_Status + "]";
	}

}
