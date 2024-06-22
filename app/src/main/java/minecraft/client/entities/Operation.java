package minecraft.client.entities;

@FunctionalInterface
public interface Operation {
    void Run(Object... args);
}