package br.com.dio.barber_shop.exception;

public class ScheduleInUseException extends RuntimeException {
    public ScheduleInUseException(String message) {
        super(message);
    }
}
