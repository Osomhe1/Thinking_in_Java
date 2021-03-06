package com.mycompany.errorhandle;


public class Stormylnning {
    
    public static class BaseballException extends RuntimeException {
        private static final long serialVersionUID = 0;
    }
    public static class Foul extends BaseballException {
        private static final long serialVersionUID = 0;
    }
    public static class Strike extends BaseballException {
        private static final long serialVersionUID = 0;
    }
    public static abstract class Inning {
        public Inning() throws BaseballException {}
        public void event() throws BaseballException {
            // Doesn’t actually have to throw anything
        }
        public abstract void atBat() throws Strike, Foul;
        public void walk() {} // Throws no checked exceptions
    }
    public static class StormException extends RuntimeException {
        private static final long serialVersionUID = 0;
    }
    public static class RainedOut extends StormException {
        private static final long serialVersionUID = 0;
    }
    public static class PopFoul extends Foul {
        private static final long serialVersionUID = 0;
    }
    public static interface Storm {
        public void event() throws RainedOut;
        public void rainHard() throws RainedOut;
    }
    public static class StormyInning extends Inning implements Storm {
        // OK to add new exceptions for constructors, but you
        // must deal with the base constructor exceptions:
        public StormyInning() throws RainedOut, BaseballException {}
        public StormyInning(String s) throws Foul, BaseballException {}
        // Regular methods must conform to base class:
        public void walk() throws PopFoul {} // No Compile error now
        // Interface CANNOT add exceptions to existing
        // methods from the base class:
        // If the method doesn’t already exist in the
        // base class, the exception is OK:
        public void rainHard() throws RainedOut {}
        // You can choose to not throw any exceptions,
        // even if the base version does:
        public void event() {}
        // Overridden methods can throw inherited exceptions:
        public void atBat() throws PopFoul {}
    }

    public static void main(String[] args) {
        StormyInning si = new StormyInning();
        si.atBat();
        // Strike not thrown in derived version.
        // What happens if you upcast?
        Inning i = new StormyInning();
        i.atBat();
        // You must catch the exceptions from the
        // base-class version of the method:
    }
}
