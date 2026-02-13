package com.internmatch.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

public final class SimpleCache {

    private static final SimpleCache INSTANCE = new SimpleCache();

    private final ConcurrentMap<String, Object> cache;

    private SimpleCache() {
        this.cache = new ConcurrentHashMap<>();
    }

    public static SimpleCache getInstance() {
        return INSTANCE;
    }

    public <T> void put(String key, T value) {
        cache.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) cache.get(key);
    }

    public boolean contains(String key) {
        return cache.containsKey(key);
    }

    public void clear() {
        cache.clear();
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrCompute(String key, Supplier<T> supplier) {
        return (T) cache.computeIfAbsent(key, k -> supplier.get());
    }
}