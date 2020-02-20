package net.rusnet.rxmoviessearch.commons.injection;

import net.rusnet.rxmoviessearch.favorites.presentation.FavoritesContract;
import net.rusnet.rxmoviessearch.favorites.presentation.FavoritesPresenter;

import dagger.Binds;
import dagger.Module;

@Module(includes = FavoritesModule.FavoritesAbstractModule.class)
public class FavoritesModule {

    @Module
    interface FavoritesAbstractModule {
        @Binds
        FavoritesContract.Presenter bindSearchPresenter(
                FavoritesPresenter presenter);
    }
}
