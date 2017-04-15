package com.example.tejas.tinohacks;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by TinoHacks on 4/15/17.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PolicyViewHolder> {

    public static class PolicyViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView policyName;
        TextView policyDesc;

        Policy cp;


        PolicyViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            policyName = (TextView)itemView.findViewById(R.id.policyName);
            policyDesc = (TextView)itemView.findViewById(R.id.policyDesc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), cp.name, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    List<Policy> policies;

    RVAdapter(List<Policy> policies){
        this.policies = policies;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PolicyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        PolicyViewHolder pvh = new PolicyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PolicyViewHolder personViewHolder, int i) {


        personViewHolder.policyName.setText(policies.get(i).name);
        personViewHolder.policyDesc.setText(policies.get(i).description);
        personViewHolder.cp = policies.get(i);
    }

    @Override
    public int getItemCount() {
        return policies.size();
    }
}
