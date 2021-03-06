package cuongvo.mvp_example.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import cuongvo.mvp_example.BaseFragment;
import cuongvo.mvp_example.model.data.StoreListData;
import cuongvo.mvp_example.presenter.EatPresenter;
import cuongvo.mvp_example.presenter.util.AppScheduler;
import cuongvo.mvp_example.view.IEatListView;
import cuongvo.mvp_example.view.component.ViewList;
import cuongvo.mvp_example.R;

/**
 * Created by cuongvo on 7/21/17.
 */

public class EatFragment extends BaseFragment implements IEatListView, ViewList.OnLoadMoreListener {

    @BindView(R.id.fragment_eat_list)
    ViewList mViewList;

    private EatPresenter mEatPresenter;

    public EatFragment() {
        mFragmentType = FragmentType.EatFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        ButterKnife.bind(this, view);

        mViewList.setOnLoadMoreListener(this);

        mEatPresenter = new EatPresenter(mContext, new AppScheduler());
        mEatPresenter.setEatListView(this);
        mEatPresenter.getEatListData();

        return view;
    }

    @Override
    public void displayEatListError() {

    }

    @Override
    public void displayLoadMoreError() {
        mViewList.hideLoadMoreProgress();
    }

    @Override
    public void displayEatListData(StoreListData storeListData) {
        mViewList.setStoreList(storeListData.getStoreDataList());
        mViewList.displayList();
    }

    @Override
    public void updateCityListAfterLoadMore(StoreListData storeListData) {
        mViewList.updateStoreList(storeListData.getStoreDataList());
        mViewList.hideLoadMoreProgress();
    }

    @Override
    public void onLoadMore(int page) {
        if (!mViewList.isLoadMore()) {
            mViewList.displayLoadMoreProgress();
            mEatPresenter.getEatListData(page, true);
        }
    }
}
