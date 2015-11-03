package fr.plaisance.aspect;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	@Before("execution(public * fr.plaisance.model.*.*(..))")
	public void logModel(JoinPoint joinPoint) {
		logger(joinPoint).trace(message(joinPoint));
	}

	@Before("execution(public * fr.plaisance.service.*.*(..))")
	public void logService(JoinPoint joinPoint) {
		logger(joinPoint).debug(message(joinPoint));
	}

	@Before("execution(public * fr.plaisance.ws.*.*(..))")
	public void logWebservice(JoinPoint joinPoint) {
		logger(joinPoint).info(message(joinPoint));
	}

	private static String message(JoinPoint joinPoint) {
		StringBuilder builder = new StringBuilder();
		builder.append(joinPoint.getSignature().getName());
		if (ArrayUtils.isNotEmpty(joinPoint.getArgs())) {
			builder.append(" : ");
			builder.append(Arrays.toString(joinPoint.getArgs()));
		}
		return builder.toString();
	}

	private static Logger logger(JoinPoint joinPoint) {
		return LoggerFactory.getLogger(joinPoint.getTarget().getClass());
	}
}
