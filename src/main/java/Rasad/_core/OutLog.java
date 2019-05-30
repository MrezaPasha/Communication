package main.java.Rasad._core;


import java.util.Arrays;

/**
 * @author dev1
 * @version 1.95.03.31
 */
public class OutLog {

    /*
     * System print
     * Shorthand: olsl
     */
    public static void sl(String msg) {
        System.out.println("-=========================================-<[ "
                + Thread.currentThread().getStackTrace()[2].getClassName() + " :: "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " : "
                + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + " ]>-\n"
                + " |  " + msg + "\n");
    }

    /*
     * System print
     * Shorthand: ols
     */
    public static void s(String msg) {
        System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber()
                + ")>==>    " + msg);
    }

    /*
     * System print
     * Shorthand: olso
     */
    public static void so() {
        System.out.println("#== "
                + Thread.currentThread().getStackTrace()[2].getClassName() + " :: "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " : "
                + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + "  =====|");
    }

    /*
     * System print
     * Shorthand: olso
     */
    public static void so(String msg) {
        System.out.println("#== "
                + Thread.currentThread().getStackTrace()[2].getClassName() + " :: "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " : "
                + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + "  ====>  " + msg);
    }

    // shorthand: olpl
    public static void pl(String msg, TtOutLog type) {

        System.out.println("-----------------------------------------<[ "
                + Thread.currentThread().getStackTrace()[2].getClassName() + " :: "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " : "
                + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + " ]>----<" + type + ">\n"
                + " |  " + msg + "\n");

        if (type == TtOutLog.FatalException
                || type == TtOutLog.FatalInfo) {
            System.out.println("||||SMS||||");
        }
    }

    public static void pl(Object msg, TtOutLog type) {
        System.out.println("-----------------------------------------<[ "
                + Thread.currentThread().getStackTrace()[2].getClassName() + " :: "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " : "
                + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + " ]>----<" + type + ">\n"
                + " |  " + msg.toString() + "\n");

        if (type == TtOutLog.FatalException
                || type == TtOutLog.FatalInfo) {
            System.out.println("||||SMS||||");
        }
    }

    public static void pl(String msg) {
        System.out.println("-----------------------------------------<[ "
                + Thread.currentThread().getStackTrace()[2].getClassName() + " :: "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " : "
                + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + " ]>-\n"
                + " |  " + msg + "\n");
    }

    public static void pl(int msg) {
        System.out.println("-----------------------------------------<[ "
                + Thread.currentThread().getStackTrace()[2].getClassName() + " :: "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " : "
                + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + " ]>-\n"
                + " |  " + msg + "\n");
    }

    public static void pl(Boolean msg) {
        System.out.println("-----------------------------------------<[ "
                + Thread.currentThread().getStackTrace()[2].getClassName() + " :: "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " : "
                + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + " ]>-\n"
                + " |  " + msg + "\n");
    }

    public static void pl() {
        System.out.println("***--------------------------------------<[ "
                + Thread.currentThread().getStackTrace()[2].getClassName() + " :: "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " : "
                + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + " ]>-\n");
    }

    // shorthand: olpo
    public static void po() {
        System.out.println("## "
                + Thread.currentThread().getStackTrace()[2].getClassName() + " :: "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " : "
                + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + "  -----|");
    }

    public static void po(String msg) {
        System.out.println("## "
                + Thread.currentThread().getStackTrace()[2].getClassName() + " :: "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " : "
                + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + "  ----->  " + msg);
    }

    public static void po(int msg) {
        System.out.println("## "
                + Thread.currentThread().getStackTrace()[2].getClassName() + " :: "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " : "
                + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + "  ----->  " + msg);
    }

    public static String getErrorPosition() {
        String finalError = "***--------------------------------------<[ "
                + Thread.currentThread().getStackTrace()[2].getClassName() + " :: "
                + Thread.currentThread().getStackTrace()[2].getMethodName() + " : "
                + Thread.currentThread().getStackTrace()[2].getLineNumber()
                + " ]>-\n";
        return finalError;
    }

    // shorthand: olp
    public static void p(String msg) {
        System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber()
                + ")>>>>    " + msg);
    }

    public static void p(int msg) {
        System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber()
                + ")>>>>    " + msg);
    }

    public static void p(String... msg) {
        System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber()
                + ")>>>>    " + Arrays.toString(msg));
    }

    public static void p(int... msg) {
        System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber()
                + ")>>>>    " + Arrays.toString(msg));
    }

    public static void p(double... msg) {
        System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber()
                + ")>>>>    " + Arrays.toString(msg));
    }

    public static void p(boolean... msg) {
        System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber()
                + ")>>>>    " + Arrays.toString(msg));
    }

    public static void p() {
        System.out.println(Thread.currentThread().getStackTrace()[2].getLineNumber()
                + ")>>>>    ");
    }
}
