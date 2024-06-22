package minecraft.client.entities;

public class SharedCounter {
    private Integer value = 0;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void incrementValue(Integer delta) {
        value += delta;
    }

}
