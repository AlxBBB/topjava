package ru.javawebinar.topjava.util.exception;

public class ErrorInfo {
    private final String url;
    private final String cause;
    private final String detail;

    public ErrorInfo(CharSequence url, Throwable ex) {
        this.url = url.toString();
        this.cause = ex.getClass().getSimpleName();
        if (ex.getLocalizedMessage().contains("meals_unique_user_datetime_idx")) {
            this.detail = "Уже есть еда с такой датой и временем";
        } else {
            this.detail = ex.getLocalizedMessage();
        }
    }
}