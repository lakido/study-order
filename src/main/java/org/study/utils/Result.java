package org.study.utils;

public abstract class Result<T> {

    public abstract T getOrNull();

    public abstract T getOrException() throws Exception;

    static final class Progress extends Result<Void> {

        @Override
        public Void getOrNull() {
            return null;
        }

        @Override
        public Void getOrException() throws Exception {
            throw new IllegalArgumentException("This component has not a value");
        }
    }

    static final class Error extends Result<Void> {

        private final Exception exception;

        public Exception getException() {
            return exception;
        }

        public Error(Exception exception) {
            this.exception = exception;
        }

        @Override
        public Void getOrNull() {
            return null;
        }

        @Override
        public Void getOrException() throws Exception {
            throw exception;
        }

        static final class Correct<T> extends Result<T> {

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
}
