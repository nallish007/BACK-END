package com.te.backendassessment;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.Data;

@Entity
@Data
public class Employee_Info {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "emp_id")
	@SequenceGenerator(initialValue = 001, name = "emp_id",allocationSize = 1)
	int Employee_ID;
	@Column(length = 50)
	String Employee_Name;
	@Column(length = 50)
	String Employee_Type;
	@Column(length = 100)
	String Email;
	@Column(length = 50)
	String Password;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "emp_info")
	List<Employee_leave> leavedata = new ArrayList<Employee_leave>();

}
