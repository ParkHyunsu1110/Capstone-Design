package com.capstonedesign.lovebaby;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class VaccineDAO_Impl implements VaccineDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfVaccine;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfVaccine;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfVaccine;

  public VaccineDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfVaccine = new EntityInsertionAdapter<Vaccine>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Vaccine`(`id`,`name`,`month`,`day`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Vaccine value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getMonth());
        stmt.bindLong(4, value.getDay());
      }
    };
    this.__deletionAdapterOfVaccine = new EntityDeletionOrUpdateAdapter<Vaccine>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Vaccine` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Vaccine value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfVaccine = new EntityDeletionOrUpdateAdapter<Vaccine>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Vaccine` SET `id` = ?,`name` = ?,`month` = ?,`day` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Vaccine value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getMonth());
        stmt.bindLong(4, value.getDay());
        stmt.bindLong(5, value.getId());
      }
    };
  }

  @Override
  public void insert(Vaccine vaccine) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfVaccine.insert(vaccine);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Vaccine vaccine) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfVaccine.handle(vaccine);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Vaccine vaccine) {
    __db.beginTransaction();
    try {
      __updateAdapterOfVaccine.handle(vaccine);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Vaccine> getAll() {
    final String _sql = "SELECT * FROM Vaccine";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfMonth = _cursor.getColumnIndexOrThrow("month");
      final int _cursorIndexOfDay = _cursor.getColumnIndexOrThrow("day");
      final List<Vaccine> _result = new ArrayList<Vaccine>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Vaccine _item;
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item = new Vaccine(_tmpName);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpMonth;
        _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
        _item.setMonth(_tmpMonth);
        final int _tmpDay;
        _tmpDay = _cursor.getInt(_cursorIndexOfDay);
        _item.setDay(_tmpDay);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Vaccine> getVaccine(String sname) {
    final String _sql = "SELECT * FROM Vaccine WHERE name > ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (sname == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, sname);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfMonth = _cursor.getColumnIndexOrThrow("month");
      final int _cursorIndexOfDay = _cursor.getColumnIndexOrThrow("day");
      final List<Vaccine> _result = new ArrayList<Vaccine>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Vaccine _item;
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item = new Vaccine(_tmpName);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpMonth;
        _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
        _item.setMonth(_tmpMonth);
        final int _tmpDay;
        _tmpDay = _cursor.getInt(_cursorIndexOfDay);
        _item.setDay(_tmpDay);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
