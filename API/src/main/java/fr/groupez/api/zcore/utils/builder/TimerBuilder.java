package fr.groupez.api.zcore.utils.builder;

import fr.groupez.api.messages.Messages;

public class TimerBuilder {

    public static String getFormatLongDays(long temps) {
        long totalSecs = temps / 1000L;

        long days = totalSecs / 86400L;
        long hours = totalSecs % 86400L / 3600L;
        long minutes = totalSecs % 3600L / 60L;
        long seconds = totalSecs % 60L;

        String message = Messages.TIME_DAY.msg();
        message = message.replace("%second%", (seconds <= 1 ? Messages.FORMAT_SECOND : Messages.FORMAT_SECONDS).msg());
        message = message.replace("%minute%", (minutes <= 1 ? Messages.FORMAT_MINUTE : Messages.FORMAT_MINUTES).msg());
        message = message.replace("%hour%", (hours <= 1 ? Messages.FORMAT_HOUR : Messages.FORMAT_HOURS).msg());
        message = message.replace("%day%", (days <= 1 ? Messages.FORMAT_DAY : Messages.FORMAT_DAYS).msg());
        return format(String.format(message, days, hours, minutes, seconds));
    }

    public static String getFormatLongHours(long temps) {
        long totalSecs = temps / 1000L;

        long hours = totalSecs / 3600L;
        long minutes = totalSecs % 3600L / 60L;
        long seconds = totalSecs % 60L;

        String message = Messages.TIME_HOUR.msg();
        message = message.replace("%second%", (seconds <= 1 ? Messages.FORMAT_SECOND : Messages.FORMAT_SECONDS).msg());
        message = message.replace("%minute%", (minutes <= 1 ? Messages.FORMAT_MINUTE : Messages.FORMAT_MINUTES).msg());
        message = message.replace("%hour%", (hours <= 1 ? Messages.FORMAT_HOUR : Messages.FORMAT_HOURS).msg());
        return format(String.format(message, hours, minutes, seconds));
    }

    public static String getFormatLongMinutes(long temps) {

        long totalSecs = temps / 1000L;

        long minutes = totalSecs % 3600L / 60L;
        long seconds = totalSecs % 60L;

        String message = Messages.TIME_MINUTE.msg();
        message = message.replace("%second%", (seconds <= 1 ? Messages.FORMAT_SECOND : Messages.FORMAT_SECONDS).msg());
        message = message.replace("%minute%", (minutes <= 1 ? Messages.FORMAT_MINUTE : Messages.FORMAT_MINUTES).msg());
        return format(String.format(message, minutes, seconds));
    }

    public static String getFormatLongSecondes(long temps) {
        long totalSecs = temps / 1000L;

        long seconds = totalSecs % 60L;
        String message = Messages.TIME_SECOND.msg();
        message = message.replace("%second%", (seconds <= 1 ? Messages.FORMAT_SECOND : Messages.FORMAT_SECONDS).msg());
        return format(String.format(message, seconds));
    }

    public static String getStringTime(long second) {
        if (second < 60) {
            return (TimerBuilder.getFormatLongSecondes(second * 1000L));
        } else if (second >= 60 && second < 3600) {
            return (TimerBuilder.getFormatLongMinutes(second * 1000L));
        } else if (second >= 3600 && second < 86400) {
            return (TimerBuilder.getFormatLongHours(second * 1000L));
        } else {
            return (TimerBuilder.getFormatLongDays(second * 1000L));
        }
    }

    public static String format(String message) {
        message = message.replace(" 00 " + Messages.FORMAT_SECOND.msg(), "");
        message = message.replace(" 00 " + Messages.FORMAT_HOUR.msg(), "");
        message = message.replace(" 00 " + Messages.FORMAT_DAY.msg(), "");
        message = message.replace(" 00 " + Messages.FORMAT_MINUTE.msg(), "");
        return message;
    }
}
