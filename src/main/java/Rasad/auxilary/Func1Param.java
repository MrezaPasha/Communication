package Rasad.auxilary;

@FunctionalInterface
public interface Func1Param<T, TResult>
{
    TResult invoke(T t);
}