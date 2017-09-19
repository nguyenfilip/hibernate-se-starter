package cz.nguyen;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.*;
import javax.persistence.spi.LoadState;

import cz.nguyen.entity.Department;
import cz.nguyen.entity.Employee;
import cz.nguyen.entity.Gender;
import org.hibernate.Session;
import org.hibernate.jpa.internal.util.PersistenceUtilHelper;

public class Main {
	private static EntityManagerFactory emf;

	public static void main(String[] args) throws SQLException {

		emf = Persistence.createEntityManagerFactory("default");

		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Employee e = new Employee();
		e.setName("Marek");
		e.setBirthDate(LocalDateTime.now());
		em.persist(e);
		em.getTransaction().commit();
		em.close();

		emf.close();
	}




//	persistEmployee("Filip", "Engineering", em);
//	persistEmployee("Martin", "Marketing", em);
//	persistEmployee("Jirka", "HR", em);
//	persistEmployee("Honza", "Engineering", em);
//	persistEmployee("Jana", "Accounting", em);
//	persistEmployee("Lucka", "Engineering", em);
//	persistEmployee("Petra", "Engineering", em);
	private static Random rand = new Random();
	public static void persistEmployee(String name,  String departmentName,  EntityManager em) {
		Department d = null;

		try {
			d = em.createQuery("select d from Department d where d.name = :name", Department.class)
					.setParameter("name", departmentName).getSingleResult();
		} catch (NoResultException noResult) {
			d = new Department();
			d.setName(departmentName);
			em.persist(d);
		}

		Employee e = new Employee();
		e.setName(name);
		e.setMonthlySalary(rand.nextInt(10) * 1000 + 20000);

		d.addEmployee(e);

		em.persist(d);
		em.persist(e);

	}

}
