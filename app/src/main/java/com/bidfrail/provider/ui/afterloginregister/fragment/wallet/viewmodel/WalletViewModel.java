package com.bidfrail.provider.ui.afterloginregister.fragment.wallet.viewmodel;

import com.bidfrail.provider.data.repository.LocalRepository;
import com.bidfrail.provider.data.repository.RemoteRepository;
import com.bidfrail.provider.ui.base.viewmodel.BaseViewModel;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WalletViewModel extends BaseViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    @Inject
    public WalletViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }
}
