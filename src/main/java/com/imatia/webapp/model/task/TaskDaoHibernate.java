package com.imatia.webapp.model.task;

import java.util.List;



import org.springframework.stereotype.Repository;

import com.imatia.webapp.model.util.dao.GenericDaoHibernate;




@Repository("taskDao")
public class TaskDaoHibernate extends
		GenericDaoHibernate<Task, Long> implements TaskDao {

	@SuppressWarnings("unchecked")
	public List<Task> findTasksByUserId(Long usrId, int startIndex, int count) {
		  return getSession().createQuery(
		        	"SELECT a FROM Task a WHERE a.userTask.usrId = :usrId " +
		        	"ORDER BY a.taskId").setParameter("usrId",usrId).setFirstResult(startIndex).
				  setMaxResults(count).getResultList();

	}
	
	@SuppressWarnings("unchecked")
	public List<Task> findAllTasks(int startIndex,int count){
		return getSession().createQuery(
				"SELECT a FROM Task a ORDER BY a.taskId").setFirstResult(startIndex).
				setMaxResults(count).getResultList();
	}
	

}