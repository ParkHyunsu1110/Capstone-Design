package com.capstonedesign.lovebaby;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class AppDatabase_Impl extends AppDatabase {
  private volatile VaccineDAO _vaccineDAO;

  private volatile GraphInfoDAO _graphInfoDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `GraphInfo` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `month` INTEGER NOT NULL, `weight` INTEGER NOT NULL, `height` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Vaccine` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `month` INTEGER NOT NULL, `day` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"4c28e4de55296dfccbeac5e6ecbbf23a\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `GraphInfo`");
        _db.execSQL("DROP TABLE IF EXISTS `Vaccine`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsGraphInfo = new HashMap<String, TableInfo.Column>(4);
        _columnsGraphInfo.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsGraphInfo.put("month", new TableInfo.Column("month", "INTEGER", true, 0));
        _columnsGraphInfo.put("weight", new TableInfo.Column("weight", "INTEGER", true, 0));
        _columnsGraphInfo.put("height", new TableInfo.Column("height", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGraphInfo = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesGraphInfo = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoGraphInfo = new TableInfo("GraphInfo", _columnsGraphInfo, _foreignKeysGraphInfo, _indicesGraphInfo);
        final TableInfo _existingGraphInfo = TableInfo.read(_db, "GraphInfo");
        if (! _infoGraphInfo.equals(_existingGraphInfo)) {
          throw new IllegalStateException("Migration didn't properly handle GraphInfo(com.capstonedesign.lovebaby.GraphInfo).\n"
                  + " Expected:\n" + _infoGraphInfo + "\n"
                  + " Found:\n" + _existingGraphInfo);
        }
        final HashMap<String, TableInfo.Column> _columnsVaccine = new HashMap<String, TableInfo.Column>(4);
        _columnsVaccine.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsVaccine.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsVaccine.put("month", new TableInfo.Column("month", "INTEGER", true, 0));
        _columnsVaccine.put("day", new TableInfo.Column("day", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysVaccine = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesVaccine = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVaccine = new TableInfo("Vaccine", _columnsVaccine, _foreignKeysVaccine, _indicesVaccine);
        final TableInfo _existingVaccine = TableInfo.read(_db, "Vaccine");
        if (! _infoVaccine.equals(_existingVaccine)) {
          throw new IllegalStateException("Migration didn't properly handle Vaccine(com.capstonedesign.lovebaby.Vaccine).\n"
                  + " Expected:\n" + _infoVaccine + "\n"
                  + " Found:\n" + _existingVaccine);
        }
      }
    }, "4c28e4de55296dfccbeac5e6ecbbf23a", "9b652fe16a83923a24471a0282282ae9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "GraphInfo","Vaccine");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `GraphInfo`");
      _db.execSQL("DELETE FROM `Vaccine`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public VaccineDAO vaccineDAO() {
    if (_vaccineDAO != null) {
      return _vaccineDAO;
    } else {
      synchronized(this) {
        if(_vaccineDAO == null) {
          _vaccineDAO = new VaccineDAO_Impl(this);
        }
        return _vaccineDAO;
      }
    }
  }

  @Override
  public GraphInfoDAO graphInfoDAO() {
    if (_graphInfoDAO != null) {
      return _graphInfoDAO;
    } else {
      synchronized(this) {
        if(_graphInfoDAO == null) {
          _graphInfoDAO = new GraphInfoDAO_Impl(this);
        }
        return _graphInfoDAO;
      }
    }
  }
}
