package myframework.extension.logger;

import org.apache.logging.log4j.LogManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Aspect
public class AspectLogger {

  private Object processMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    Class cl = Class.forName(joinPoint.getSignature().getDeclaringTypeName());

    ThreadLocal<StringBuffer> buffer = new ThreadLocal<>();
    buffer.set(new StringBuffer());

    buffer
        .get()
        .append(joinPoint.getSignature().getDeclaringTypeName())
        .append(".")
        .append(joinPoint.getSignature().getName());

    buffer.get().append("(");
    if (joinPoint.getArgs().length > 0) {

      Method curMethod = null;
      for (Method method : cl.getMethods()) {
        if (method.getName().equals(joinPoint.getSignature().getName())
            && method.getParameterCount() == joinPoint.getArgs().length) {
          curMethod = method;
        }
      }
      assert curMethod != null;
      for (int i = 0; i < curMethod.getParameters().length; ++i) {
        buffer
            .get()
            .append(curMethod.getParameters()[i].getName())
            .append(" = ")
            .append(joinPoint.getArgs()[i])
            .append(", ");
      }
      buffer.get().deleteCharAt(buffer.get().length() - 1);
      buffer.get().deleteCharAt(buffer.get().length() - 1);
    }
    buffer.get().append(")");

    String message = buffer.get().toString();
    LogManager.getLogger(cl).info(message);

    Object toReturn = joinPoint.proceed();

    if (toReturn instanceof Boolean) {
      message = "result - " + toReturn;
      LogManager.getLogger(cl).info(message);
    }

    return toReturn;
  }

  @Around("execution(* myframework.pages..*(..))")
  public Object logPage(ProceedingJoinPoint joinPoint) throws Throwable {

    return processMethod(joinPoint);
  }
}
