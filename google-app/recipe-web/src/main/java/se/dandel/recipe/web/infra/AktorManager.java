package se.dandel.recipe.web.infra;

public class AktorManager {

    private static ThreadLocal<Aktor> instance = new ThreadLocal<>();

    public static void initialize(Aktor aktor) {
        if (isInitialized()) {
            throw new IllegalStateException("Aktor is already initialized in thread");
        }
        instance.set(aktor);
    }

    public static boolean isInitialized() {
        return instance.get() != null;
    }

    public static Aktor get() {
        if (!isInitialized()) {
            throw new IllegalStateException("Aktor has not been initialized in thread");
        }
        return instance.get();
    }

    public static void destroy() {
        instance.set(null);
    }

}
