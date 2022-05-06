package com.te.backendassessment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Employee {

	public static void employee(Employee_Info user) {

		System.out.println("==================================");
		Scanner scan = new Scanner(System.in);
		System.out.println("WELCOME " + user.getEmployee_Name().toUpperCase());
		while (true) {
			System.out.println("1->leave status");
			System.out.println("2->apply for leave ");
			System.out.println("3->back ");
			int s = scan.nextInt();
			switch (s) {
			case 1:
				Employee.leavestatus(user);
				break;
			case 2:
				Employee.applyleave(user);
				break;
			case 3:
				return;

			default:
				try {
					throw new InvalidinputException("INVALID ENTRY");
				} catch (InvalidinputException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	public static void leavestatus(Employee_Info user) {

		System.out.println(">>>>>>>>LEAVE_STATUS>>>>>>>>");
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employ");
		EntityManager em = emf.createEntityManager();
		Employee_Info info = em.find(Employee_Info.class, user.getEmployee_ID());
		Iterator<Employee_leave> itr = info.getLeavedata().iterator();
		while (itr.hasNext()) {
			System.out.println(itr.next());
		}
	}

	@SuppressWarnings("resource")
	public static void applyleave(Employee_Info user) {
		Scanner scan = new Scanner(System.in);
		Employee_leave el = new Employee_leave();
		List<Employee_leave> al = new ArrayList<Employee_leave>();
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employ");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tr = em.getTransaction();
		Employee_Info info = em.find(Employee_Info.class, user.getEmployee_ID());
		al = info.getLeavedata();
		System.out.println("enter the date(dd/mm/yyyy)");
		el.setLeave_Date(scan.next());
		el.setLeave_Status("pending");
		el.setEmp_info(info);
		info.getLeavedata().add(el);
 
		em.persist(info);
		tr.begin();
		tr.commit();

	}
}
