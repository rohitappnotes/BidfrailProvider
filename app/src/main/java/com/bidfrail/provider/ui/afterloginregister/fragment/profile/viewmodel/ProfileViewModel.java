package com.bidfrail.provider.ui.afterloginregister.fragment.profile.viewmodel;

import com.bidfrail.provider.data.repository.LocalRepository;
import com.bidfrail.provider.data.repository.RemoteRepository;
import com.bidfrail.provider.ui.base.viewmodel.BaseViewModel;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProfileViewModel extends BaseViewModel {

    private LocalRepository localRepository;
    private RemoteRepository remoteRepository;

    @Inject
    public ProfileViewModel(LocalRepository localRepository, RemoteRepository remoteRepository) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
    }
}
