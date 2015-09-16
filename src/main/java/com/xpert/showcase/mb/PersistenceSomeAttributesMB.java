package com.xpert.showcase.mb;

import com.xpert.faces.utils.FacesMessageUtils;
import com.xpert.showcase.dao.PersonDAO;
import com.xpert.showcase.model.Person;
import com.xpert.showcase.model.Status;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Ayslan
 */
@ManagedBean
public class PersistenceSomeAttributesMB {
    
    @EJB
    private PersonDAO personDAO;
    private List<Person> people;
    private String attributes;
    
    public void search() {
        
        List<Person> list = personDAO.getQueryBuilder()
                .debug()
                .select("code, name, id")
                .from(Person.class)
                .getResultList(Person.class);
        
        List<Person> list2 = personDAO.getQueryBuilder()
                .select("p")
              //  .select("p")
                .from(Person.class, "p")
                .innerJoin("p.group", "g")
                .getResultList();
        
        try {
            people = personDAO.listAttributes(attributes, "id");
        } catch (Exception ex) {
            FacesMessageUtils.error(ex.getCause().getMessage());
        }
    }
    
    public String getAttributes() {
        return attributes;
    }
    
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
    
    public List<Person> getPeople() {
        return people;
    }
    
    public void setPeople(List<Person> people) {
        this.people = people;
    }
}
