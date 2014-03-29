package evolutionaryAlgoritm;

import java.util.Arrays;

/**
 * User: sigurd
 * Date: 28.03.14
 * Time: 14:08
 */
public class Genotype {
    private Object[] value;

    public Genotype(Object[] value) {
        this.value = value;
    }

    public Object[] getValue() {
        return value;
    }

    public void setValue(Object[] value) {
        this.value = value;
    }

    public Genotype clone() {
        return new Genotype(Arrays.copyOf(value, value.length));
    }
}
