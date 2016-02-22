package com.xpert.showcase.mb;

import com.xpert.faces.component.restriction.RestrictionCollector;
import com.xpert.faces.primefaces.LazyDataModelImpl;
import com.xpert.faces.utils.FacesMessageUtils;
import com.xpert.persistence.query.JoinBuilder;
import com.xpert.persistence.query.Restriction;
import com.xpert.persistence.query.Restrictions;
import com.xpert.showcase.dao.PersonDAO;
import com.xpert.showcase.model.Person;
import com.xpert.showcase.model.Status;
import com.xpert.showcase.vo.PersonVO;
import java.io.Serializable;
import java.util.List;
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
public class RestrictionComponentMB implements Serializable {

    @EJB
    private PersonDAO personDAO;

    private PersonVO personVO = new PersonVO();

    private LazyDataModelImpl<Person> dataModelPerson;
    private LazyDataModelImpl<Person> dataModelOnDemand;
    private LazyDataModelImpl<Person> dataModelWithRestriction;
    private LazyDataModelImpl<Person> dataModelPersonWithJoin;

    private Restrictions restrictions = new Restrictions();

    public void printCurrentRestrictions() {
        if (restrictions == null) {
            System.out.println("null restrictions");
        } else {
            System.out.println(restrictions);
        }
    }

    public void verifyRestrictions() {

        List<Restriction> restrictions = RestrictionCollector.getCurrentRestrictions();

        if (restrictions == null || restrictions.isEmpty()) {
            FacesMessageUtils.warning("No restriction informed");
            RestrictionCollector.ignoreRestrictions();
            return;
        }

    }

    public void validate() {

        if (personVO.getBirthdayBegin() != null && personVO.getBirthdayEnd() != null) {
            if (personVO.getBirthdayBegin().after(personVO.getBirthdayEnd())) {
                FacesMessageUtils.warning("Birthday begin date is greater then end date");
                RestrictionCollector.ignoreRestrictions();
                return;
            }
        }

    }

    @PostConstruct
    public void init() {

        //data model simple
        dataModelPerson = new LazyDataModelImpl("name", personDAO);

        //data model on demand (not loaded)
        dataModelOnDemand = new LazyDataModelImpl("name", personDAO);
        dataModelOnDemand.setLoadData(false);

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

    public PersonVO getPersonVO() {
        return personVO;
    }

    public void setPersonVO(PersonVO personVO) {
        this.personVO = personVO;
    }

    public LazyDataModelImpl<Person> getDataModelOnDemand() {
        return dataModelOnDemand;
    }

    public void setDataModelOnDemand(LazyDataModelImpl<Person> dataModelOnDemand) {
        this.dataModelOnDemand = dataModelOnDemand;
    }

    public Restrictions getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(Restrictions restrictions) {
        this.restrictions = restrictions;
    }
    
    

}
