package com.github.moncat.common.generator.id;

import org.springframework.context.annotation.Bean;

public class NextId {
	
	@Bean
	public static Long getId() {
		IdGenerator gen = new DistributedIdGenerator();
		return gen.next();
	}
	
}
