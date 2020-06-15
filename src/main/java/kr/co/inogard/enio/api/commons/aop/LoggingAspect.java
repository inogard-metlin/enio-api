package kr.co.inogard.enio.api.commons.aop;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import lombok.extern.slf4j.Slf4j;

//@Aspect
//@Component
@Slf4j
public class LoggingAspect {

	@Around("within(kr.co.inogard.enio.api.web..*) && @target(org.springframework.web.bind.annotation.RestController)")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("logAround() is running!");
		log.debug("hijacked method : " + joinPoint.getSignature().getName());
		log.debug("hijacked arguments : " + Arrays.toString(joinPoint.getArgs()));
		log.debug("Around before is running!");
		
		try {
			Object result = joinPoint.proceed();
			return result;
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			log.debug("Around after is running!");
			log.debug("******");
		}
	}
}