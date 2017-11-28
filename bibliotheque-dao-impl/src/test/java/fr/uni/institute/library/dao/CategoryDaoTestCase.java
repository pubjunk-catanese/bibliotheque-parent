package fr.uni.institute.library.dao;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.uni.institute.library.business.inventory.Category;
import fr.uni.institute.library.dao.CategoryDao;
import fr.uni.institute.library.dao.DaoException;
import fr.uni.institute.library.dao.jdbc.CategoryDaoJdbc;

public class CategoryDaoTestCase {
	
	private static long resultatAttendu;
	private static CategoryDao categoryDao;
	private static Connection connection;
	private static Category categoryAttendu;

	@Before
	public void setUp() throws Exception {
		System.out.println("Définition des conditions initiales");
		resultatAttendu = 11;
		System.out.println("Initialisation de la connexion ");
		Class.forName("com.mysql.jdbc.Driver");
	    connection = DriverManager.getConnection("jdbc:mysql://192.168.25.100:3306/uni_library_db", "root", "admin");
		categoryDao = new CategoryDaoJdbc(connection);
		categoryAttendu = new Category(1, "Music");
		
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("Libé©ration des resources");
		categoryDao = null;
		connection.close();
		connection = null;
	}

	@Test(timeout=5)
	public void testResearchAllCategories() {
		System.out.println("Réccupération de la liste des categories ");
		try {
			Collection<Category> liste = categoryDao.researchAllCategories();
			assertNotNull(liste);
			assertEquals(resultatAttendu, liste.size(),0);
		} catch (DaoException e) {
			fail(e.getMessage());
		}
		
	}
	
	
	
	@Test
	public void testResearchCategoryById() {
		System.out.println("Réccupération d'une categorie ");
		try {
			Category categorieCalculee = categoryDao.researchCategoryById(categoryAttendu.getId());
			assertNotNull(categorieCalculee);
			assertEquals(categoryAttendu.getName(), categorieCalculee.getName() );
		} catch (DaoException e) {
			fail(e.getMessage());
		}
		
	}

}
