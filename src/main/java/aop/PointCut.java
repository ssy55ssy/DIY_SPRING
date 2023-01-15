package aop;

public interface PointCut {

    // return the class filter for the point cut
    ClassFilter getClassFilter();

    // return the method matcher for the point cut
    MethodMatcher getMethocMatcher();

}
