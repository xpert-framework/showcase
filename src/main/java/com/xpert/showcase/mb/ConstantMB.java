package com.xpert.showcase.mb;

import com.xpert.Constants;
import javax.faces.bean.ManagedBean;
import org.primefaces.PrimeFaces;
import org.primefaces.context.PrimeRequestContext;

/**
 *
 * @author Ayslan
 */
@ManagedBean
public class ConstantMB {
    
    public String getXpertFrameworkVersion(){
        return Constants.VERSION;
    }
    public String getPrimefacesVersion(){
        return PrimeRequestContext.getCurrentInstance().getApplicationContext().getEnvironment().getBuildVersion();
    }
    
}
