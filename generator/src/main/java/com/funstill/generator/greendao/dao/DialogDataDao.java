package com.funstill.generator.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.funstill.generator.greendao.entity.DialogData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DIALOG_DATA".
*/
public class DialogDataDao extends AbstractDao<DialogData, Long> {

    public static final String TABLENAME = "DIALOG_DATA";

    /**
     * Properties of entity DialogData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property LastMsgId = new Property(1, Long.class, "lastMsgId", false, "LAST_MSG_ID");
        public final static Property Users = new Property(2, String.class, "users", false, "USERS");
        public final static Property UnreadCount = new Property(3, Integer.class, "unreadCount", false, "UNREAD_COUNT");
        public final static Property DialogType = new Property(4, Integer.class, "dialogType", false, "DIALOG_TYPE");
        public final static Property DialogName = new Property(5, String.class, "dialogName", false, "DIALOG_NAME");
        public final static Property CreateDate = new Property(6, java.util.Date.class, "createDate", false, "CREATE_DATE");
        public final static Property UpdateDate = new Property(7, java.util.Date.class, "updateDate", false, "UPDATE_DATE");
    }


    public DialogDataDao(DaoConfig config) {
        super(config);
    }
    
    public DialogDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DIALOG_DATA\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"LAST_MSG_ID\" INTEGER UNIQUE ," + // 1: lastMsgId
                "\"USERS\" TEXT," + // 2: users
                "\"UNREAD_COUNT\" INTEGER," + // 3: unreadCount
                "\"DIALOG_TYPE\" INTEGER NOT NULL ," + // 4: dialogType
                "\"DIALOG_NAME\" TEXT," + // 5: dialogName
                "\"CREATE_DATE\" INTEGER," + // 6: createDate
                "\"UPDATE_DATE\" INTEGER);"); // 7: updateDate
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DIALOG_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DialogData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long lastMsgId = entity.getLastMsgId();
        if (lastMsgId != null) {
            stmt.bindLong(2, lastMsgId);
        }
 
        String users = entity.getUsers();
        if (users != null) {
            stmt.bindString(3, users);
        }
 
        Integer unreadCount = entity.getUnreadCount();
        if (unreadCount != null) {
            stmt.bindLong(4, unreadCount);
        }
        stmt.bindLong(5, entity.getDialogType());
 
        String dialogName = entity.getDialogName();
        if (dialogName != null) {
            stmt.bindString(6, dialogName);
        }
 
        java.util.Date createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindLong(7, createDate.getTime());
        }
 
        java.util.Date updateDate = entity.getUpdateDate();
        if (updateDate != null) {
            stmt.bindLong(8, updateDate.getTime());
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DialogData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long lastMsgId = entity.getLastMsgId();
        if (lastMsgId != null) {
            stmt.bindLong(2, lastMsgId);
        }
 
        String users = entity.getUsers();
        if (users != null) {
            stmt.bindString(3, users);
        }
 
        Integer unreadCount = entity.getUnreadCount();
        if (unreadCount != null) {
            stmt.bindLong(4, unreadCount);
        }
        stmt.bindLong(5, entity.getDialogType());
 
        String dialogName = entity.getDialogName();
        if (dialogName != null) {
            stmt.bindString(6, dialogName);
        }
 
        java.util.Date createDate = entity.getCreateDate();
        if (createDate != null) {
            stmt.bindLong(7, createDate.getTime());
        }
 
        java.util.Date updateDate = entity.getUpdateDate();
        if (updateDate != null) {
            stmt.bindLong(8, updateDate.getTime());
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public DialogData readEntity(Cursor cursor, int offset) {
        DialogData entity = new DialogData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // lastMsgId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // users
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // unreadCount
            cursor.getInt(offset + 4), // dialogType
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // dialogName
            cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)), // createDate
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)) // updateDate
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DialogData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLastMsgId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setUsers(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setUnreadCount(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setDialogType(cursor.getInt(offset + 4));
        entity.setDialogName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCreateDate(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
        entity.setUpdateDate(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DialogData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DialogData entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DialogData entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
