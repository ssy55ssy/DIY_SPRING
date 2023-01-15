package aop;


import org.aopalliance.intercept.MethodInterceptor;

public class AdvisedSupport {

    private boolean proxyTargetClass = false;

    // the object which will be proxyed
    private TargetSource targetSource;

    private MethodInterceptor methodInterceptor;

    private MethodMatcher methodMatcher;

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) { this.methodInterceptor = methodInterceptor; }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }
}
