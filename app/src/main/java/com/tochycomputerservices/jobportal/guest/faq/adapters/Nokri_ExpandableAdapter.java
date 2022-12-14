package com.tochycomputerservices.jobportal.guest.faq.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tochycomputerservices.jobportal.guest.faq.models.Child;
import com.tochycomputerservices.jobportal.guest.faq.viewholders.ChildViewHolder;
import com.tochycomputerservices.jobportal.guest.faq.viewholders.ParentViewHolder;
import com.tochycomputerservices.jobportal.R;
import com.tochycomputerservices.jobportal.manager.Nokri_FontManager;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Glixen Technologies on 09/01/2018.
 */

public class Nokri_ExpandableAdapter extends ExpandableRecyclerViewAdapter<ParentViewHolder,ChildViewHolder> {
    private Context context;

    public Nokri_ExpandableAdapter(List<? extends ExpandableGroup> groups, Context context) {
        super(groups);
        this.context = context;

    }

    @Override
    public ParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_parent,parent,false);
        return new ParentViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_child,parent,false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        Child child = (Child) group.getItems().get(childIndex);
        holder.setChildTextView(child.getChildText());
        new Nokri_FontManager().nokri_setOpenSenseFontTextView(holder.childTextView,context.getAssets());
    }

    @Override
    public void onBindGroupViewHolder(ParentViewHolder holder, int flatPosition, ExpandableGroup group) {
        holder.setTitle(group.getTitle());
        new Nokri_FontManager().nokri_setOpenSenseFontTextView(holder.titleTextView,context.getAssets());
    }
}
