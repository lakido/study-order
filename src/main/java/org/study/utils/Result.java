package org.study.utils;

public abstract class Result<T> {

    public abstract T getOrNull();

    public abstract T getOrException() throws Exception;

    public static final class Progress<T> extends Result<T> {

        @Override
        public T getOrNull() {
            return null;
        }

        @Override
        public T getOrException() throws Exception {
            throw new IllegalArgumentException("This component has not a value");
        }
    }

    public static final class Error<T> extends Result<T> {

        private final Exception exception;

        public Exception getException() {
            return exception;
        }

        public Error(Exception exception) {
            this.exception = exception;
        }

        @Override
        public T getOrNull() {
            return null;
        }

        @Override
        public T getOrException() throws Exception {
            throw exception;
        }
    }

    public static final class Correct<T> extends Result<T> {

        private final T value;

        public Correct(T value) {
            this.value = value;
        }

        @Override
        public T getOrNull() {
            return value;
        }

        @Override
        public T getOrException() throws Exception {
            return value;
        }
    }
}

