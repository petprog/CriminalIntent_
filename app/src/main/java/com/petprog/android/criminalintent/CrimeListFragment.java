package com.petprog.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CrimeListFragment extends Fragment {

    public static final int REQUEST_CRIME = 1;
    public static final String POSITION_CLICKED = "com.petprog.android.criminalintent.position_clicked";


    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private int mPositionClicked;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecyclerView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {

        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else {
            mAdapter.notifyItemChanged(mPositionClicked);
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        private Crime mCrime;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {


            super(inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.crime_title);
            mDateTextView = itemView.findViewById(R.id.crime_date);

            mSolvedImageView = itemView.findViewById(R.id.crime_solved);

        }

        public CrimeHolder(View itemView) {
            super(itemView);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate());

            mSolvedImageView.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);

        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(getActivity(), mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getActivity(), CrimeActivity.class);
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            startActivityForResult(intent, REQUEST_CRIME);
            mPositionClicked = getAdapterPosition();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CRIME) {
            if (data == null) {
                return;
            }
        }

    }

//    private class CrimeRequiresPoliceHolder extends CrimeHolder {
//        private TextView mTitleTextView;
//        private TextView mDateTextView;
//        private Button mPoliceButton;
//
//        private Crime mCrime;
//
//        public CrimeRequiresPoliceHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.list_item_crime_requires, parent, false));
//            mTitleTextView = itemView.findViewById(R.id.crime_title);
//            mDateTextView = itemView.findViewById(R.id.crime_date);
//
//            mPoliceButton = itemView.findViewById(R.id.police_button);
//        }
//
//        public void bind(Crime crime) {
//            mCrime = crime;
//            mTitleTextView.setText(mCrime.getTitle());
//            mDateTextView.setText(mCrime.getDate());
//            mPoliceButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getActivity(), mCrime.getTitle() +
//                            " police request clicked! ", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private static final int POLICE_VIEW_TYPE = 1;
        private List<Crime> mCrimes;

        @Override
        public int getItemViewType(int position) {

            if (mCrimes.get(position).isRequiresPolice()) {
                return POLICE_VIEW_TYPE;
            }
            return 0;
        }

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            Log.d("CrimeListFragment", "onCreateViewHolder");
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            if (viewType == POLICE_VIEW_TYPE)
//                return new CrimeRequiresPoliceHolder(layoutInflater, parent);
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
//            Log.d("CrimeListFragment", "onBindViewHolder" + String.valueOf(position));
            holder.bind(mCrimes.get(position));
        }

        @Override
        public int getItemCount() {
//            Log.d("CrimeListFragment", "getItemCount");
            return mCrimes.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION_CLICKED, mPositionClicked);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
            mPositionClicked = savedInstanceState.getInt(POSITION_CLICKED);
    }
}