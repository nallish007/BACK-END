package com.te.backendassessment;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Manager {

	public static void manager(Employee_Info user) {
		System.out.println("==================================");
		Scanner scan = new Scanner(System.in);
		System.out.println("WELCOME " + user.getEmployee_Name().toUpperCase());
		while (true) {
			System.out.println("1->show the leave requests");
			System.out.println("2->Approve/Reject leave request ");
			System.out.println("3->back ");
			int s = scan.nextInt();
			switch (s) {
			case 1:
				Manager.leaverequests(user);
				break;
			case 2:
				Manager.changestatus(user);
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

	public static void leaverequests(Employee_Info user) {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employ");
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery(" from Employee_leave where Leave_Status='pending'");

		List<Employee_leave> li = query.getResultList();
		Iterator<Employee_leave> itr = li.iterator();
		Employee_leave emp_Le = new Employee_leave();
		while (itr.hasNext()) {
			emp_Le = itr.next();
			System.out.println("Employee_ID=" + emp_Le.getEmp_info().getEmployee_ID() + emp_Le);
		}

	}

	public static void changestatus(Employee_Info user) {
		Scanner scan = new Scanner(System.in);
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employ");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tr = em.getTransaction();
		tr.begin();
		System.out.println("Enter the employee_id");
		int id = scan.nextInt();
		Employee_Info Info = em.find(Employee_Info.class, id);
		int nextindex = Info.getLeavedata().size();
		int index = 0;
		while (index < nextindex - 1) {
			Employee_leave next = Info.getLeavedata().get(index);
			if (next.getLeave_Status().compareTo("pending") == 0) {
				System.out.println(next.getLeave_Date());
				System.out.println("\n1->to approve\n2->to reject");
				int s = scan.nextInt();
				switch (s) {
				case 1:
					Info.getLeavedata().get(index).setLeave_Status("Approved");

					break;
				case 2:
					Info.getLeavedata().get(index).setLeave_Status("Rejected");

					break;

				default:
					try {
						throw new InvalidinputException("INVALID ENTRY");
					} catch (InvalidinputException e) {
						System.out.println(e.getMessage());
					}
				}

			}

			index++;

		}
		em.persist(Info);
		tr.commit();

	}

}
