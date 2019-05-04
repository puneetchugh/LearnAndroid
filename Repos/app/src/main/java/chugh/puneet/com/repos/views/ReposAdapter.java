package chugh.puneet.com.repos.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chugh.puneet.com.repos.R;
import chugh.puneet.com.repos.model.data.Repo;
import chugh.puneet.com.repos.views.ReposListFrag.OnListFragmentInteractionListener;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {

    private final List<Repo> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ReposAdapter(List<Repo> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.repo = mValues.get(position);
        holder.repoNameView.setText(mValues.get(position).getName());
        holder.repoOwnerView.setText(mValues.get(position).getOwner().getLogin());
        holder.repoStarsWatcher.setText(String.format("Stars : %d",mValues.get(position).getStargazersCount()));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(mValues.get(position).getGitUrl());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView repoNameView;
        public final TextView repoOwnerView;
        public final TextView repoStarsWatcher;
        public Repo repo;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            repoNameView = view.findViewById(R.id.id_repo_name);
            repoOwnerView = view.findViewById(R.id.id_repo_owner);
            repoStarsWatcher = view.findViewById(R.id.id_repo_stars);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + repoNameView.getText() + "'";
        }
    }
}
