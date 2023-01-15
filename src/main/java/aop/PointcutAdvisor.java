package aop;

public interface PointcutAdvisor extends Advisor {
    // get the pointcut which drive the advisor
    PointCut getPointCut();
}
