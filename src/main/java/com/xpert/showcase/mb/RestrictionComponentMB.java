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
import org.omnifaces.util.Ajax;

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
    private LazyDataModelImpl<Person> dataModelWithRestriction;
    private LazyDataModelImpl<Person> dataModelPersonWithJoin;

    public void verifyRestrictions() {

        List<Restriction> restrictions = RestrictionCollector.getCurrentRestrictions();

        if (restrictions == null || restrictions.isEmpty()) {
            FacesMessageUtils.warning("No restriction informed");
            RestrictionCollector.ignoreRestrictions();
            return;
        }

        if (personVO.getBirthdayBegin() != null && personVO.getBirthdayEnd() != null) {
            if (personVO.getBirthdayBegin().after(personVO.getBirthdayEnd())) {
                FacesMessageUtils.warning("Birthday begin date is greater then end date");
                RestrictionCollector.ignoreRestrictions();
                return;
            }
        }

        for (Restriction restriction : restrictions) {
            FacesMessageUtils.info("Restriction: " + restriction.getProperty() + " " + restriction.getRestrictionType().getSymbol() + " " + restriction.getValue());
        }

    }

    @PostConstruct
    public void init() {
        
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

    public PersonVO getPersonVO() {
        return personVO;
    }

    public void setPersonVO(PersonVO personVO) {
        this.personVO = personVO;
    }

}
