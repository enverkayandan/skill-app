package prodyna.skillApp.aspect;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class MethodExecutionTimeAspect {

    private final MeterRegistry meterRegistry;

    public MethodExecutionTimeAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Pointcut("execution(* prodyna.skillApp.controller.*.*(..))")
    private void methodsInController() {
        // This is a pointcut, where we defined to match all methods in the controller package
        // You can adjust this to match the methods you're interested in
    }

    @Around("methodsInController()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();  // execute the method
        long duration = System.currentTimeMillis() - start;

        // record with the method name
        Timer timer = Timer.builder("method.execution.time")
                .tag("class", joinPoint.getSignature().getDeclaringTypeName())
                .tag("method", joinPoint.getSignature().getName())
                .register(meterRegistry);

        timer.record(duration, TimeUnit.MILLISECONDS);

        return proceed;
    }
}

