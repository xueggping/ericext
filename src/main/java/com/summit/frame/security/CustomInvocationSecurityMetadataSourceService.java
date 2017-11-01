package com.summit.frame.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;

import com.summit.frame.util.SummitTools;
import com.summit.system.function.service.FunctionService;



@Service("customInvocationSecurityMetadataSourceService")
public class CustomInvocationSecurityMetadataSourceService implements
        FilterInvocationSecurityMetadataSource {
    /**
     * Logger for this class
     */
    private AntPathRequestMatcher pathMatcher;
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    @Autowired
	FunctionService fs;
    @Autowired
	SummitTools st;
    @PostConstruct
    public void loadResourceDefine() {
        if (resourceMap == null){
        	resourceMap = fs.getResourceMap();
        }
    }
 
	// According to a URL, Find out permission configuration of this URL.
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {

        // guess object is a URL.
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if(st.stringNotNull(resURL)){
            	pathMatcher = new AntPathRequestMatcher(resURL);
	            if (pathMatcher.matches(request)) {
	                Collection<ConfigAttribute> returnCollection = resourceMap
	                        .get(resURL);
	                return returnCollection;
	            }
            }
        }
        return null;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
    	Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
		for (String entry : resourceMap.keySet()) {
			allAttributes.addAll(resourceMap.get(entry));
		}
		return allAttributes;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }
}