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
public class GraphInfoDAO_Impl implements GraphInfoDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfGraphInfo;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfGraphInfo;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfGraphInfo;

  public GraphInfoDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfGraphInfo = new EntityInsertionAdapter<GraphInfo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `GraphInfo`(`id`,`month`,`weight`,`height`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GraphInfo value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getMonth());
        stmt.bindLong(3, value.getWeight());
        stmt.bindLong(4, value.getHeight());
      }
    };
    this.__deletionAdapterOfGraphInfo = new EntityDeletionOrUpdateAdapter<GraphInfo>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `GraphInfo` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GraphInfo value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfGraphInfo = new EntityDeletionOrUpdateAdapter<GraphInfo>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `GraphInfo` SET `id` = ?,`month` = ?,`weight` = ?,`height` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, GraphInfo value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getMonth());
        stmt.bindLong(3, value.getWeight());
        stmt.bindLong(4, value.getHeight());
        stmt.bindLong(5, value.getId());
      }
    };
  }

  @Override
  public void insert(GraphInfo graphInfo) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfGraphInfo.insert(graphInfo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(GraphInfo graphInfo) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfGraphInfo.handle(graphInfo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(GraphInfo graphInfo) {
    __db.beginTransaction();
    try {
      __updateAdapterOfGraphInfo.handle(graphInfo);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<GraphInfo> getAll() {
    final String _sql = "SELECT * FROM GraphInfo";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfMonth = _cursor.getColumnIndexOrThrow("month");
      final int _cursorIndexOfWeight = _cursor.getColumnIndexOrThrow("weight");
      final int _cursorIndexOfHeight = _cursor.getColumnIndexOrThrow("height");
      final List<GraphInfo> _result = new ArrayList<GraphInfo>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final GraphInfo _item;
        final int _tmpMonth;
        _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
        final int _tmpWeight;
        _tmpWeight = _cursor.getInt(_cursorIndexOfWeight);
        final int _tmpHeight;
        _tmpHeight = _cursor.getInt(_cursorIndexOfHeight);
        _item = new GraphInfo(_tmpMonth,_tmpWeight,_tmpHeight);
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
