package de.dhbw.java.exercise.gson;

import com.google.gson.*;

import java.lang.reflect.*;

public class GSONTest {
    
    private static final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();
    
    public static void main(String[] args) {
    
    
        final MyClass src = new MyClass("1", "2", "3");
        final String x = gson.toJson(src);
        System.out.println(x);
    
        System.out.println(gson.fromJson(x, MyClass.class).aBC);
    }
    
    
    private static final class MyClass {
        
        public String aBC, b, c;
        
        public MyClass(String a, String b, String c) {
            this.aBC = a;
            this.b = b;
            this.c = c;
        }
    }
}
