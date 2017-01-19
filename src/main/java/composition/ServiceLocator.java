package composition;

public final class ServiceLocator {
    private final static ComponentsComposer componentComposer = new ComponentsComposer();

    public static ComponentsComposer getComposer() {
        return componentComposer;
    }
}
