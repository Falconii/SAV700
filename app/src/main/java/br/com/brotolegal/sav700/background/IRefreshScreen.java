package br.com.brotolegal.sav700.background;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Falconi on 10/07/2017.
 */

public interface IRefreshScreen<T> {

    public void refresh(ArrayList<T> result);

    public void refreshOver(ArrayList<T> result);

    public List<Object> Loading();


}
