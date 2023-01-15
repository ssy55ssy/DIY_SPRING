package aop.aspectJ;

import aop.AspectJExpressionPointcut;
import aop.PointCut;
import aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    // point cut is used to get join point
    private AspectJExpressionPointcut pointcut;
    // advice determines which kind of operation will be used
    private Advice advice;

    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setAdvice(Advice advice){
        this.advice = advice;
    }

    public PointCut getPointCut(){
        if (null == pointcut) {
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }

    public Advice getAdvice(){
        return advice;
    }
}

