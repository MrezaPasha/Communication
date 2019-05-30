package Rasad.auxilary;

@FunctionalInterface
public interface Action1Param<T>
{
    void invoke(T t);
}