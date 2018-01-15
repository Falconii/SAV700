package br.com.brotolegal.savdatabase.dao;

import android.database.Cursor;

import java.util.List;

/**
 * Created by Falconi on 22/10/2015.
 */
public interface IDao2<T> {

    public T insert(T obj);

    public Integer Delete(String[] values);

    public Boolean Update(T obj);

    public T cursorToObj(Cursor cursor);

    public List<T> getAll();

    public T seek(String[] values);

}
