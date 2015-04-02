package by.parfen.disptaxi.dataaccess.impl;

import org.springframework.stereotype.Repository;

import by.parfen.disptaxi.dataaccess.UserProfileDao;
import by.parfen.disptaxi.datamodel.UserProfile;

@Repository
public class UserProfileDaoImpl extends AbstractDaoImpl<Long, UserProfile> implements UserProfileDao {

	protected UserProfileDaoImpl() {
		super(UserProfile.class);
	}
}
