package com.in28minutes.rest.webservices.restfulwebservicess.filtering;


import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;


@RestController
public class FilteringController {

	@GetMapping("/filtering")
	public MappingJacksonValue retrieveSomeBean(){
		//dynamic filtering to return only v1, v2
		SomeBean someBean = new SomeBean("v1","v2","v3");
		
	//	SimpleBeanPropertyFilter filter = new SimpleBeanPropertyFilter().filterOutAllExcept("f1","f2");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("f1","f2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter );
		
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
		mapping.setFilters(filters);
		
		return mapping;
	}
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue retrieveAllBeans(){
		
		List<SomeBean> list =  Arrays.asList(new SomeBean("v11","v22","v33"), new SomeBean("v31","v32","v33"));
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("f2","f3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter );
		
		MappingJacksonValue mapping = new MappingJacksonValue(list);
		mapping.setFilters(filters);
		
		return mapping;
	}
}
