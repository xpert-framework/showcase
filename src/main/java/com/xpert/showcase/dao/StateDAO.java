package com.xpert.showcase.dao;

import com.xpert.persistence.dao.BaseDAO;
import com.xpert.showcase.model.State;
import javax.ejb.Local;

/**
 *
 * @author #Author
 */
@Local
public interface StateDAO extends BaseDAO<State> {
    
}
