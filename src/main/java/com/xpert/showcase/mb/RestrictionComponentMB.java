package com.xpert.showcase.mb;

import com.xpert.faces.primefaces.LazyDataModelImpl;
import com.xpert.persistence.query.JoinBuilder;
import com.xpert.persistence.query.Restrictions;
import com.xpert.showcase.dao.PersonDAO;
import com.xpert.showcase.model.Person;
import com.xpert.showcase.model.Status;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author ayslan.sousa
 */
@ManagedBean
@ViewScoped
public class RestrictionComponentMB implements Serializable{
    
    
    @EJB
    private PersonDAO personDAO;
    
    private LazyDataModelImpl<Person> dataModelPerson;
    private LazyDataModelImpl<Person> dataModelWithRestriction;
    private LazyDataModelImpl<Person> dataModelPersonWithJoin;
    
    
    @PostConstruct
    public void init(){
        
        //data model simple
        dataModelPerson = new LazyDataModelImpl("name", personDAO);
        
        //data model with pre defined restriction
        dataModelWithRestriction = new LazyDataModelImpl("name", personDAO);
        Restrictions restrictions = new Restrictions();
        restrictions.add("status", Status.ACTIVE);
        dataModelWithRestriction.setRestrictions(restrictions);
        
        //data model with Join Builder
        dataModelPersonWithJoin = new LazyDataModelImpl("p.name", personDAO);
        JoinBuilder joinBuilder = new JoinBuilder("p");
        joinBuilder.leftJoinFetch("p.group", "g");
        dataModelPersonWithJoin.setJoinBuilder(joinBuilder);
    }

    public LazyDataModelImpl<Person> getDataModelPerson() {
        return dataModelPerson;
    }

    public void setDataModelPerson(LazyDataModelImpl<Person> dataModelPerson) {
        this.dataModelPerson = dataModelPerson;
    }

    public LazyDataModelImpl<Person> getDataModelWithRestriction() {
        return dataModelWithRestriction;
    }

    public void setDataModelWithRestriction(LazyDataModelImpl<Person> dataModelWithRestriction) {
        this.dataModelWithRestriction = dataModelWithRestriction;
    }

    public LazyDataModelImpl<Person> getDataModelPersonWithJoin() {
        return dataModelPersonWithJoin;
    }

    public void setDataModelPersonWithJoin(LazyDataModelImpl<Person> dataModelPersonWithJoin) {
        this.dataModelPersonWithJoin = dataModelPersonWithJoin;
    }
    
    
    
    
}
