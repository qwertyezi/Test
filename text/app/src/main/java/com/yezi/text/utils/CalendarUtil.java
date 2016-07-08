package com.yezi.text.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.TextUtils;

import com.yezi.text.R;
import com.yezi.text.TextApp;

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarUtil {

    private static final String calanderURL = "content://com.android.calendar/calendars";
    private static final String calanderEventURL = "content://com.android.calendar/events";
    private static final String calanderRemiderURL = "content://com.android.calendar/reminders";

    private static final int alarmAheadTime = 60 * 2;//两个小时

    private static ContentResolver getCR() {
        return TextApp.getAppContext().getContentResolver();
    }

    public static void showCalendar() {
        Cursor userCursor = getCR().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null);
        if (userCursor == null) {
            return;
        }
        StringBuffer buffer = new StringBuffer();
        for (userCursor.moveToFirst(); !userCursor.isAfterLast(); userCursor.moveToNext()) {
            buffer.append(userCursor.getString(userCursor.getColumnIndex("name")) + ",");
        }
        TextApp.showToast(buffer.toString());
        userCursor.close();
    }

    public static void deleteEvent() {
        boolean hasAccount = false;
        Cursor userCursor = getCR().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null);
        if (userCursor == null) {
            return;
        }
        for (userCursor.moveToFirst(); !userCursor.isAfterLast(); userCursor.moveToNext()) {
            if (TextApp.getAppContext().getString(R.string.app_name).equals(
                    userCursor.getString(userCursor.getColumnIndex("name")))) {
                hasAccount = true;
            }
        }
        userCursor.close();
        if (!hasAccount) {
            return;
        }

        long eventID = 4;
        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
        getCR().delete(deleteUri, null, null);
    }


    public static void addEvent(String title, String description, String eventLocation,
                                long startTime, long endTime) {
        String calId = "";
        Cursor userCursor = getCR().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null);
        if (userCursor == null) {
            return;
        }
        for (userCursor.moveToFirst(); !userCursor.isAfterLast(); userCursor.moveToNext()) {
            if (TextApp.getAppContext().getString(R.string.app_name).equals(
                    userCursor.getString(userCursor.getColumnIndex("name")))) {
                calId = userCursor.getString(userCursor.getColumnIndex("_id"));
            }
        }
        userCursor.close();
        if (TextUtils.isEmpty(calId)) {
            addAccount();
            addEvent(title, description, eventLocation, startTime, endTime);
            return;
        }

        ContentValues event = new ContentValues();
        event.put(CalendarContract.Events.TITLE, title);
        event.put(CalendarContract.Events.DESCRIPTION, description);
        event.put(CalendarContract.Events.EVENT_LOCATION, eventLocation);
        event.put(CalendarContract.Events.CALENDAR_ID, calId);

        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(startTime);
        long start = mCalendar.getTimeInMillis();
        mCalendar.setTimeInMillis(endTime);
        long end = mCalendar.getTimeInMillis();

        event.put(CalendarContract.Events.DTSTART, start * 1000);
        event.put(CalendarContract.Events.DTEND, end * 1000);
        event.put(CalendarContract.Events.HAS_ALARM, 1);
        event.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai");
        Uri newEvent = getCR().insert(CalendarContract.Events.CONTENT_URI, event);

        //事件提醒的设定
        long id = Long.parseLong(newEvent.getLastPathSegment());
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, id);
        values.put(CalendarContract.Reminders.MINUTES, alarmAheadTime);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        getCR().insert(CalendarContract.Reminders.CONTENT_URI, values);

        TextApp.showToast("插入事件成功!!!");
    }

    public static void addAccount() {
        Cursor userCursor = getCR().query(CalendarContract.Calendars.CONTENT_URI, null, null, null, null);
        if (userCursor == null) {
            return;
        }
        for (userCursor.moveToFirst(); !userCursor.isAfterLast(); userCursor.moveToNext()) {
            if (TextApp.getAppContext().getString(R.string.app_name).equals(
                    userCursor.getString(userCursor.getColumnIndex("name")))) {
                userCursor.close();
                return;
            }
        }
        userCursor.close();

        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        String appName = TextApp.getAppContext().getString(R.string.app_name);
        value.put(CalendarContract.Calendars.NAME, appName);

        value.put(CalendarContract.Calendars.ACCOUNT_NAME, appName);
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, appName);
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, appName);
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, -9206951);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, appName);
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);
        value.put(CalendarContract.Calendars.IS_PRIMARY,0);

        Uri calendarUri = CalendarContract.Calendars.CONTENT_URI;
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, appName)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, appName)
                .build();

        getCR().insert(calendarUri, value);
    }
}
