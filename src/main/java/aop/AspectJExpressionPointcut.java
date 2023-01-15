package aop;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class AspectJExpressionPointcut implements PointCut,ClassFilter,MethodMatcher {

    private static final Set<PointcutPrimitive>  SUPPORTED_PRIMITIVES =  new HashSet<>();

    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    private final PointcutExpression pointcutExpression;

    public AspectJExpressionPointcut(String expression) {
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES,
                this.getClass().getClassLoader());
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }


    public ClassFilter getClassFilter(){
        return this;
    }

    public MethodMatcher getMethocMatcher(){
        return this;
    }

    public boolean matches(Class<?> clazz){
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    public boolean matches(Method method, Class<?> targetClass){
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

}
