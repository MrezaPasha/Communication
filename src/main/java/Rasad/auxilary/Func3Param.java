package Rasad.auxilary;

@FunctionalInterface
public interface Func3Param<T1, T2, T3, TResult>
{
    TResult invoke(T1 t1, T2 t2, T3 t3);
}