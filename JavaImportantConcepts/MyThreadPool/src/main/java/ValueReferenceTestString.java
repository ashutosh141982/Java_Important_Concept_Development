import java.lang.reflect.Field;

public class ValueReferenceTestString {
    public static void main(String[] args) {


        String s1 = "abc";
        String s2 = new String("abc");
        Field f;
        try {
            f = String.class.getDeclaredField("value");
            f.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }

        try {
            System.out.println("s1.value == s2.value: " + (f.get(s1)
                    == f.get(s2)));
        } catch (IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }
}