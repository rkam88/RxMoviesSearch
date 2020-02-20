package net.rusnet.rxmoviessearch.commons.injection;

import net.rusnet.rxmoviessearch.search.presentation.SearchContract;
import net.rusnet.rxmoviessearch.search.presentation.SearchPresenter;

import dagger.Binds;
import dagger.Module;

@Module(includes = SearchModule.SearchAbstractModule.class)
public class SearchModule {

    @Module
    interface SearchAbstractModule {
        @Binds
        SearchContract.Presenter bindSearchPresenter(
                SearchPresenter presenter);
    }
}
