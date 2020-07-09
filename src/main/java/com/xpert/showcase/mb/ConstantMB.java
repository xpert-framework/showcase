package com.xpert.showcase.mb;

import com.xpert.Constants;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import org.primefaces.context.PrimeRequestContext;

/**
 *
 * @author Ayslan
 */
@ManagedBean
public class ConstantMB implements Serializable{
    
    public String getXpertFrameworkVersion(){
        return Constants.VERSION;
    }
    public String getPrimefacesVersion(){
        return PrimeRequestContext.getCurrentInstance().getApplicationContext().getEnvironment().getBuildVersion();
    }
    
}
