package org.study.utils;

public interface ResultMapper<T, R> {
    R map(T element);
}
