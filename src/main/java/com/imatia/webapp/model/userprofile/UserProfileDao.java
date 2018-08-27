package com.imatia.webapp.model.userprofile;

import com.imatia.webapp.model.util.dao.GenericDao;
import com.imatia.webapp.model.util.exceptions.InstanceNotFoundException;

public interface UserProfileDao extends GenericDao<UserProfile, Long> {
	
	
	/**
     * Returns an UserProfile by login name (not user identifier)
     *
     * @param loginName the user identifier
     * @return the UserProfile
     */
	public UserProfile findByUserName(String userName) throws InstanceNotFoundException;
}
