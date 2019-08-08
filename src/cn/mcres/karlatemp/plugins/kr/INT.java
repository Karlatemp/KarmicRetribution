
package cn.mcres.karlatemp.plugins.kr;

public class INT extends Number{

    public int value;

    public INT() {
        this(0);
    }

    public INT(int i) {
        value = i;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public int hashCode() {
        return Integer.hashCode(value);
    }

    public boolean equals(Object i) {
        if (i instanceof INT) {
            return value == ((INT) i).value;
        }
        if (i instanceof Integer) {
            return i.equals(value);
        }
        return false;
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }
}
