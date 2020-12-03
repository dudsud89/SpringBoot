package com.example.mybatis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Around("execution(* com.example.mybatis..controller.*Controller.*(..)) or execution(* com.board..mapper.*Mapper.*(..))")
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();
		long start = System.currentTimeMillis();

		if (name.contains("Controller") == true) {
			type = "Controller ===> ";

		} else if (name.contains("Mapper") == true) {
			type = "Mapper ===> ";
		}
		
        long end = System.currentTimeMillis();

        
        logger.info("----------------------------------------------------------------");
		logger.info(type + name + "." + joinPoint.getSignature().getName() + "()");
		logger.info("Running Time : " + (end-start));
		
		return joinPoint.proceed();
	}

}
