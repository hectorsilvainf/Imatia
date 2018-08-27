package com.imatia.webapp.model.task;

import java.util.List;

import com.imatia.webapp.model.util.dao.GenericDao;




public interface TaskDao extends GenericDao<Task, Long>{

    /**
     * Returns a list of tasks using the pattern page-by-page iterator
     *
     * @param startIndex the index of the first task to return
     * @param count the maximum number of tasks to return
     * @return the list of all tasks
     */
    public List<Task> findTasksByUserId(Long usrId,int startIndex, int count);
    
    public List<Task> findAllTasks(int startIndex,int count);
    
}
