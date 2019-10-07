package de.dhbw_ka.myrandom;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

@SuppressWarnings("DuplicatedCode")
public class TestRandom {
    
    public static void main(String[] args) {
        final int min = 0;
        final int maxExclusive = 10_000_000;
        final int cycles = 100_000;
        
    
        final long start1 = System.nanoTime();
        final Map<Integer, AtomicLong> matches1 = showDistribution1(min, maxExclusive, cycles);
    
        final long start2 = System.nanoTime();
        final Map<Integer, AtomicLong> matches2 = showDistribution2(min, maxExclusive, cycles);
        final long end = System.nanoTime();
        
        System.out.println("1: " + (start2 - start1) / 1e9 + " seconds.");
        System.out.println("2: " + (end - start2) / 1e9 + " seconds.");
    
        System.out.println("Size 1: " + matches1.size());
        System.out.println("Size 2: " + matches2.size());
        
    }
    
    public static Map<Integer, AtomicLong> showDistribution1(int min, int maxExclusive, int cycles) {
        final Map<Integer, AtomicLong> matches = new HashMap<>(maxExclusive - min);
        
        IntStream.generate(() -> RandomMethod.calculate(min, maxExclusive)).limit(cycles).mapToObj(i -> matches.computeIfAbsent(i, ignored -> new AtomicLong(0))).forEach(AtomicLong::incrementAndGet);
        return matches;
    }
    
    public static Map<Integer, AtomicLong> showDistribution2(int min, int maxExclusive, int cycles) {
        final Map<Integer, AtomicLong> matches = new HashMap<>(maxExclusive - min);
        for(int i = min; i < maxExclusive; i++) {
            matches.put(i, new AtomicLong(0));
        }
        
        IntStream.generate(() -> RandomMethod.calculate(min, maxExclusive)).limit(cycles).mapToObj(matches::get).forEach(AtomicLong::incrementAndGet);
        
        return matches;
    }
    
    private static void printDistribution(int min, int maxExclusive, int cycles, Map<Integer, AtomicLong> matches) {
        for(int i = min; i < maxExclusive; i++) {
            final long x = matches.getOrDefault(i, new AtomicLong(0)).longValue();
            System.out.print(i);
            System.out.print(":\t");
            System.out.printf("%2.2f", x / (double) cycles * 100.0D);
            System.out.print("%\t");
            System.out.println(x);
        }
        System.out.flush();
    }
    
}
