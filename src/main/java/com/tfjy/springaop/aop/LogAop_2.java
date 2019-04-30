package com.tfjy.springaop.aop;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.tfjy.springaop.bean.AuditJournalEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

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
		tag.set(UUID.randomUUID().toString());
	}
	
	@After("log()")
	public void afterExec(JoinPoint joinPoint){
		AuditJournalEntity auditJournalEntity=new AuditJournalEntity();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		MethodSignature ms=(MethodSignature) joinPoint.getSignature();
		String classname = joinPoint.getTarget().getClass().getSimpleName();
		String methodname = joinPoint.getSignature().getName();
		Object[] os=joinPoint.getArgs();
		String remoteAddr = request.getRemoteAddr();
		System.out.println("——后置通知——");
		String parameter="";
		for(int i=0;i<os.length;i++){
			System.out.println("\t==>参数的值["+i+"]:\t"+os[i].toString());
			parameter+=os[i].toString();
		}

		StringBuilder stringBuilder=new StringBuilder();
		for(int i=0;i<os.length;i++) {
			stringBuilder.append("参数").append(i+1).append(":").append(os[i]);
		}
		System.out.println("参数stringbuilder"+stringBuilder);

		Long l = System.currentTimeMillis() - time.get();
		Calendar cale = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sqlDate = df.format(cale.getTime());

		System.out.println("类名:"+classname+"中的方法:"+methodname+"运行消耗了"+(System.currentTimeMillis()-time.get())+"ms");
		auditJournalEntity.setId(IdWorker.getIdStr());
		auditJournalEntity.setClassName(classname).setCompanyId("1").setFunctionTime(l.toString()).setIsException(0).setMethodName(methodname).setOperatorId("1").setOperator("zmh");
		auditJournalEntity.setOperatorIp(remoteAddr).setParameter(parameter).setProjectId("1");
		String url="jdbc:mysql://192.168.22.58:3306/message_dev?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
		String username="root";
		String password="admin_pwd";
		String sql="INSERT INTO tc_audit_journal VALUES ('"+auditJournalEntity.getId()+"','"+auditJournalEntity.getClassName()+"'," +
				"'"+auditJournalEntity.getMethodName()+"','"+auditJournalEntity.getParameter()+"','1'," +
				"'"+auditJournalEntity.getOperatorIp()+"',0,'"+auditJournalEntity.getFunctionTime()+"','2'," +
				"'3','','"+"zmh"+"',0,'"+sqlDate+"','"+sqlDate+"')";
		System.out.println(sql);
		try {
			Connection connection= DriverManager.getConnection(url,username,password);
			PreparedStatement statement = connection.prepareStatement(sql);
			int resultSet=statement.executeUpdate(sql);
			System.out.println(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
	
	@Around("log()")
	public void aroundExec(ProceedingJoinPoint pjp) throws Throwable{
		pjp.proceed();
	}

}
