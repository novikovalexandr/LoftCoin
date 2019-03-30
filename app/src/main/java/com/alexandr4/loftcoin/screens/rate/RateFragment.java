package com.alexandr4.loftcoin.screens.rate;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alexandr4.loftcoin.App;
import com.alexandr4.loftcoin.R;
import com.alexandr4.loftcoin.data.api.Api;
import com.alexandr4.loftcoin.data.db.Database;
import com.alexandr4.loftcoin.data.db.model.CoinEntity;
import com.alexandr4.loftcoin.data.db.model.CoinEntityMapper;
import com.alexandr4.loftcoin.data.db.model.CoinEntityMapperImpl;
import com.alexandr4.loftcoin.data.prefs.Prefs;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateFragment extends Fragment implements RateView {

    public RateFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    @BindView(R.id.rate_content)
    ViewGroup content;

    private RatePresenter presenter;
    private RateAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = getActivity();

        if (activity == null) {
            return;
        }

        Api api = ((App) getActivity().getApplication()).getApi();
        Prefs prefs = ((App) getActivity().getApplication()).getPrefs();

        Database database = ((App) getActivity().getApplication()).getDatabase();
        CoinEntityMapper mapper = new CoinEntityMapperImpl();

        presenter = new RatePresenterImpl(prefs, api, database, mapper);
        adapter = new RateAdapter(prefs);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        toolbar.setTitle(R.string.rate_screen_title);
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);

        refresh.setOnRefreshListener(() -> presenter.onRefresh());

        presenter.attachView(this);
        presenter.getRate();
    }

    @Override
    public void setCoins(List<CoinEntity> coins) {
        adapter.setItems(coins);
    }

    @Override
    public void setRefreshing(Boolean refreshing) {
        refresh.setRefreshing(refreshing);
    }

    @Override
    public void showCurrencyDialog() {

    }
}
