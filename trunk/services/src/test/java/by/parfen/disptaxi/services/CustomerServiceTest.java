package by.parfen.disptaxi.services;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import by.parfen.disptaxi.AbstractServiceTest;
import by.parfen.disptaxi.datamodel.Customer;
import by.parfen.disptaxi.datamodel.UserProfile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public class CustomerServiceTest extends AbstractServiceTest {

	@Inject
	private CustomerService customerService;
	@Inject
	private UserProfileService userProfileService;

	@Test
	public void createUserAndCustomer() {

		final UserProfile profile = createUserProfile();
		final Customer customer = createCustomer();
		customerService.create(customer, profile);

		final Customer createdCustomer = customerService.get(customer.getId());
		Assert.assertNotNull(createdCustomer);
		// TODO check equals

		customerService.delete(customer);
		Assert.assertNull(customerService.get(customer.getId()));

		userProfileService.delete(profile);
		Assert.assertNull(userProfileService.get(profile.getId()));
	}

	// @Test
	// public void uniqueConstraintsTest() {
	// final UserProfile profile = createUserProfile();
	// final String email = randomString("email");
	// final UserAccount account = createUserAccount();
	// account.setEmail(email);
	// profile.setUserAccount(account);
	// customerService.createNewUser(profile, account);
	//
	// final UserProfile duplicateProfile = createUserProfile();
	// final UserAccount duplicateAccount = createUserAccount();
	// duplicateAccount.setEmail(email);
	// duplicateProfile.setUserAccount(duplicateAccount);
	// try {
	// customerService.createNewUser(duplicateProfile, duplicateAccount);
	// Assert.fail("Not unique login can't be saved.");
	// } catch (final PersistenceException e) {
	// // expected
	// }
	//
	// // should be saved now
	// duplicateAccount.setEmail(randomString("email"));
	// customerService.createNewUser(duplicateProfile, duplicateAccount);
	// }

}
