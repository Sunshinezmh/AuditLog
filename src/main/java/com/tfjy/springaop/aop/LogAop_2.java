package com.tfjy.springaop.aop;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.tfjy.springaop.bean.AuditJournalEntity;
import com.tfjy.springaop.controller.JournalRecord;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAop_2 {

	ThreadLocal<Long> time=new ThreadLocal<Long>();
	ThreadLocal<String> tag=new ThreadLocal<String>();
	
	@Pointcut("@annotation(com.tfjy.springaop.annotation.Log)")
	public void log(){
		System.out.println("我是一个切入点");
	}
	
	/**
	 * 在所有标注@Log的地方切入
	 * @param joinPoint
	 */
	@Before("log()")
	public void beforeExec(JoinPoint joinPoint){
		time.set(System.currentTimeMillis());
	}
	
	@After("log()")
	public void afterExec(JoinPoint joinPoint){
		tag.set(IdWorker.getIdStr());
		AuditJournalEntity auditJournalEntity=new AuditJournalEntity();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String classname = joinPoint.getTarget().getClass().getSimpleName();
		String methodname = joinPoint.getSignature().getName();
		Object[] os=joinPoint.getArgs();
		String remoteAddr = request.getRemoteAddr();
		System.out.println("——后置通知——");
		StringBuilder stringBuilder=new StringBuilder();
		for(int i=0;i<os.length;i++) {
			stringBuilder.append("参数").append(i+1).append(":").append(os[i]);
		}
		Long l = System.currentTimeMillis() - time.get();
		auditJournalEntity.setId(tag.get());
		auditJournalEntity.setClassName(classname).setCompanyId("1").setFunctionTime(l.toString()).setMethodName(methodname).setOperatorId("1").setOperator("zmh");
		auditJournalEntity.setOperatorIp(remoteAddr).setParameter(stringBuilder.toString()).setProjectId("1");
		JournalRecord.auditJournal(auditJournalEntity);

	}
	
	@Around("log()")
	public void aroundExec(ProceedingJoinPoint pjp) throws Throwable{
		pjp.proceed();
	}

	@AfterThrowing("log()")
	public void afterThrowingExec(JoinPoint joinPoint){
		if (StringUtils.isEmpty(tag.get())) {
			JournalRecord.throwJournal(tag.get());
		}
	}

}
