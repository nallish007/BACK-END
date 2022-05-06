package com.te.backendassessment;

import java.util.Scanner;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class App {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int s = 0;

		while (true) {
			System.out.println("====LEAVE RECORDS====");
			System.out.println("1.REGISTER_EMPLOYEE");
			System.out.println("2.LOGIN");
			System.out.println("3.EXIT");
			s = scan.nextInt();
			switch (s) {
			case 1:
				App.Register();
				break;
			case 2:
				Employee_Info user = App.login();

				if (user != null) {
					switch (user.getEmployee_Type()) {
					case "manager":
						Manager.manager(user);
						break;
					case "employee":
						Employee.employee(user);
						break;

					}
				}

				break;
			case 3:
				return;

			default:
				try {
					throw new InvalidinputException("*****INVALID ENTRY*****");
				} catch (InvalidinputException e) {
					System.out.println(e.getMessage());
				}

			}

		}
	}

	public static void Register() {
		Scanner scan = new Scanner(System.in);
		Employee_Info emp = new Employee_Info();
		System.out.println("Enter the employee name");
		emp.setEmployee_Name(scan.nextLine());
		System.out.println("select the Employee_Type \n1->manager\n2->employee");
		switch (scan.nextInt()) {
		case 1:
			emp.setEmployee_Type("manager");
			break;
		case 2:
			emp.setEmployee_Type("employee");
			break;
		default:
			try {
				throw new InvalidinputException("-----INVALID ENTRY-----");
			} catch (InvalidinputException e) {
				System.out.println(e.getMessage());
				return;
			}
		}

		System.out.println("Enter the Email");
		emp.setEmail(scan.next());
		System.out.println("Enter the Passcode");
		emp.setPassword(scan.next());
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employ");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tr = em.getTransaction();
		tr.begin();
		em.persist(emp);
		tr.commit();

	}

	public static Employee_Info login() {

		Scanner scan = new Scanner(System.in);
		System.out.println(" enter the mailid");
		String emailverify = scan.next();
		int id = -1;
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("employ");
		EntityManager em = emf.createEntityManager();

		Query query = em.createQuery(" select Employee_ID from Employee_Info where Email=:text");
		try {
			id = (int) query.setParameter("text", emailverify).getResultList().get(0);
			Employee_Info user = em.getReference(Employee_Info.class, id);

			while (true) {
				System.out.println("Enter the passcode");
				System.out.println("Enter  5 to exit");
				String pass = scan.next();
				if (pass.compareTo("5") == 0) {
					break;
				}
				if (user.getPassword().compareTo(pass) == 0) {

					System.out.println("****verification successfull****");
					return user;
				} else {
					try {
						throw new InvalidinputException("***passcode incorrect***");
					} catch (InvalidinputException e) {
						System.out.println(e.getMessage());
					}
				}
			}

		} catch (IndexOutOfBoundsException e) {

			System.out.println("email id doesn't excist");
		}
		return null;

	}
}
