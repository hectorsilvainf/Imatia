package com.imatia.webapp.model.userprofile;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.imatia.webapp.model.util.dao.GenericDaoHibernate;
import com.imatia.webapp.model.util.exceptions.InstanceNotFoundException;



@Repository("userProfileDao")
public class UserProfileDaoHibernate extends
		GenericDaoHibernate<UserProfile,Long> implements UserProfileDao {

	public UserProfile findByUserName(String userName) throws InstanceNotFoundException {

    	UserProfile userProfile = null;
		try {
            userProfile = (UserProfile) getSession().createQuery(
    			"SELECT u FROM UserProfile u WHERE u.userName = :userName")
    			.setParameter("userName", userName)
    			.getSingleResult();
		} catch (NoResultException nre) {
			throw new InstanceNotFoundException(userName, UserProfile.class.getName());
		}
   		return userProfile;
	}


}