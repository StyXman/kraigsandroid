package com.angrydoughnuts.android.alarmclock;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Settings;

public class AlarmSettings {
  static public final long DEFAULT_SETTINGS_ID = -1;

  private boolean dirty;
  private Uri tone;
  private String toneName;
  private int snoozeMinutes;
  private boolean vibrate;
  private int volumeStartPercent;
  private int volumeEndPercent;
  private int volumeChangeTimeSec;

  public ContentValues contentValues(long alarmId) {
    ContentValues values = new ContentValues();
    values.put(DbHelper.SETTINGS_COL_ID, alarmId);
    values.put(DbHelper.SETTINGS_COL_TONE_URL, tone.toString());
    values.put(DbHelper.SETTINGS_COL_TONE_NAME, toneName);
    values.put(DbHelper.SETTINGS_COL_SNOOZE, snoozeMinutes);
    values.put(DbHelper.SETTINGS_COL_VIBRATE, vibrate);
    values.put(DbHelper.SETTINGS_COL_VOLUME_STARTING, volumeStartPercent);
    values.put(DbHelper.SETTINGS_COL_VOLUME_ENDING, volumeEndPercent);
    values.put(DbHelper.SETTINGS_COL_VOLUME_TIME, volumeChangeTimeSec);
    return values;
  }

  static public String[] contentColumns() {
    return new String[] {
      DbHelper.SETTINGS_COL_ID,
      DbHelper.SETTINGS_COL_TONE_URL,
      DbHelper.SETTINGS_COL_TONE_NAME,
      DbHelper.SETTINGS_COL_SNOOZE,
      DbHelper.SETTINGS_COL_VIBRATE,
      DbHelper.SETTINGS_COL_VOLUME_STARTING,
      DbHelper.SETTINGS_COL_VOLUME_ENDING,
      DbHelper.SETTINGS_COL_VOLUME_TIME
    };
  }

  public AlarmSettings() {
    dirty = false;
    tone = Settings.System.DEFAULT_ALARM_ALERT_URI;
    toneName = "Default";
    snoozeMinutes = 10;
    vibrate = false;
    volumeStartPercent = 0;
    volumeEndPercent = 100;
    volumeChangeTimeSec = 20;
  }

  public AlarmSettings(Cursor cursor) {
    dirty = false;
    cursor.moveToFirst();
    tone = Uri.parse(cursor.getString(cursor.getColumnIndex(DbHelper.SETTINGS_COL_TONE_URL)));
    toneName = cursor.getString(cursor.getColumnIndex(DbHelper.SETTINGS_COL_TONE_NAME));
    snoozeMinutes = cursor.getInt(cursor.getColumnIndex(DbHelper.SETTINGS_COL_SNOOZE));
    vibrate = cursor.getInt(cursor.getColumnIndex(DbHelper.SETTINGS_COL_VIBRATE)) == 1;
    volumeStartPercent = cursor.getInt(cursor.getColumnIndex(DbHelper.SETTINGS_COL_VOLUME_STARTING));
    volumeEndPercent = cursor.getInt(cursor.getColumnIndex(DbHelper.SETTINGS_COL_VOLUME_ENDING));
    volumeChangeTimeSec = cursor.getInt(cursor.getColumnIndex(DbHelper.SETTINGS_COL_VOLUME_TIME));
  }

  // TODO(cgallek): this is a silly way of dirty-ing... Do something smarter in
  // the settings activity.
  public boolean dirty() {
    return dirty;
  }

  public Uri getTone() {
    return tone;
  }

  public void setTone(Uri tone, String name) {
    if (this.tone != tone || this.toneName != name) {
      dirty = true;
    }
    this.tone = tone;
    this.toneName = name;
  }

  public String getToneName() {
    return toneName;
  }

  public int getSnoozeMinutes() {
    return snoozeMinutes;
  }

  public void setSnoozeMinutes(int minutes) {
    if (this.snoozeMinutes != minutes) {
      dirty = true;
    }
    // TODO(cgallek): These all need validation checks.  Add validation
    // checks and then update all internal modifiers to use setters.
    this.snoozeMinutes = minutes;
  }

  public boolean getVibrate() {
    return vibrate;
  }

  public void setVibrate(boolean vibrate) {
    if (this.vibrate != vibrate) {
      dirty = true;
    }
    this.vibrate = vibrate;
  }

  public int getVolumeStartPercent() {
    return volumeStartPercent;
  }

  public void setVolumeStartPercent(int volumeStartPercent) {
    if (this.volumeStartPercent != volumeStartPercent) {
      dirty = true;
    }
    this.volumeStartPercent = volumeStartPercent;
  }

  public int getVolumeEndPercent() {
    return volumeEndPercent;
  }

  public void setVolumeEndPercent(int volumeEndPercent) {
    if (this.volumeEndPercent != volumeEndPercent) {
      dirty = true;
    }
    this.volumeEndPercent = volumeEndPercent;
  }

  public int getVolumeChangeTimeSec() {
    return volumeChangeTimeSec;
  }

  public void setVolumeChangeTimeSec(int volumeChangeTimeSec) {
    if (this.volumeChangeTimeSec != volumeChangeTimeSec) {
      dirty = true;
    }
    this.volumeChangeTimeSec = volumeChangeTimeSec;
  }
}
