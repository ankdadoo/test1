package com.boeing.ssow.convertors;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import com.boeing.ssow.model.Acronyms;

public class JsonHttpMessageConvertor extends
		MappingJacksonHttpMessageConverter {

	

	@Override
	protected JavaType getJavaType(Class<?> clazz) {
		//System.out.println("Came in here to test the class");
		   if (List.class.isAssignableFrom(clazz)) {
		     return TypeFactory.collectionType(ArrayList.class, Acronyms.class);
		   } else {
		     return super.getJavaType(clazz);
		   }
		}
	
	

}
