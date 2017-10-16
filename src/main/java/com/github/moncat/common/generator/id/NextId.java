package com.github.moncat.common.generator.id;

import org.springframework.context.annotation.Bean;

public class NextId {
	
		
	static IdGenerator gen = new DistributedIdGenerator();
	
	
	@Bean
	public static Long getId() {
		return gen.next();
	}
	
}
