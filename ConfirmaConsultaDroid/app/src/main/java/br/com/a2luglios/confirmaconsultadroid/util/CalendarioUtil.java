package br.com.a2luglios.confirmaconsultadroid.util;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import br.com.a2luglios.confirmaconsultadroid.modelo.Consulta;

/**
 * Created by ettoreluglio on 01/09/17.
 */

public class CalendarioUtil {

    private Context ctx;
    private TimeZone timeZone;

    public CalendarioUtil(Context ctx) {
        this.ctx = ctx;
        timeZone = TimeZone.getDefault();
    }

    public void addEvento(Consulta consulta) {
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, consulta.getMedico());
        values.put(CalendarContract.Events.DESCRIPTION, "Consulta Marcada");
        values.put(CalendarContract.Events.EVENT_LOCATION, consulta.getConsultorio());
        values.put(CalendarContract.Events.DTSTART, consulta.getDataInicio());
        values.put(CalendarContract.Events.DTEND, consulta.getDataTermino());
        //values.put(CalendarContract.Events.DURATION, consulta.getDataTermino() - consulta.getDataInicio());
        values.put(CalendarContract.Events.STATUS, 1);
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());

        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Uri uri = ctx.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);
        consulta.setIdCalendario(uri.getLastPathSegment());

        ContentValues reminders = new ContentValues();
        reminders.put(CalendarContract.Reminders.EVENT_ID, Long.parseLong(uri.getLastPathSegment()));
        reminders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        reminders.put(CalendarContract.Reminders.MINUTES, consulta.getAlarme()*60);

        ctx.getContentResolver().insert(CalendarContract.Reminders.CONTENT_URI, reminders);
    }

    public void removeEvento(Consulta consulta) {
        Uri eventUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI,
                Long.parseLong(consulta.getIdCalendario()));
        ctx.getContentResolver().delete(eventUri, null, null);
    }

    public void updateEvent(Consulta consulta) {
        Uri eventUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI,
                Long.parseLong(consulta.getIdCalendario()));
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, 1);
        values.put(CalendarContract.Events.TITLE, consulta.getMedico());
        values.put(CalendarContract.Events.DESCRIPTION, "Consulta Marcada");
        values.put(CalendarContract.Events.EVENT_LOCATION, consulta.getConsultorio());
        values.put(CalendarContract.Events.DTSTART, consulta.getDataInicio());
        values.put(CalendarContract.Events.DTEND, consulta.getDataTermino());
        //values.put(CalendarContract.Events.DURATION, consulta.getDataTermino() - consulta.getDataInicio());
        values.put(CalendarContract.Events.STATUS, 1);
        values.put(CalendarContract.Events.HAS_ALARM, 1);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());

        ctx.getContentResolver().update(eventUri, values, null, null);
    }

}
