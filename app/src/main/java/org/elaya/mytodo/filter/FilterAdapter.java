package org.elaya.mytodo.filter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.elaya.mytodo.R;

/**
 * Adapter for the filter selection list.
 */

class FilterAdapter extends CursorAdapter {

    public FilterAdapter(Context pContext,@NonNull Cursor pCursor) {
        super(pContext, pCursor, 0);
    }

    @Override
    public View newView(Context pContext, Cursor pCursor, ViewGroup pViewGroup) {

        LayoutInflater lInflater=LayoutInflater.from(pContext);
        return lInflater.inflate(R.layout.filter_item,pViewGroup,false);

    }

    @Override
    public void bindView(@NonNull View pView, Context pContext, @NonNull Cursor pCursor) {
        FilterItem lFilter=new FilterItem(pCursor);
        TextView lNameElement=pView.findViewById(R.id.filterName);
        lNameElement.setText(lFilter.getName());
        pView.setTag(lFilter);
    }
}
