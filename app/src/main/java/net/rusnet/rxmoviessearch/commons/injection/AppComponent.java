package net.rusnet.rxmoviessearch.commons.injection;

import net.rusnet.rxmoviessearch.favorites.presentation.FavoritesActivity;
import net.rusnet.rxmoviessearch.search.presentation.SearchActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        LocalDbModule.class,
        NetworkModule.class,
        RxSchedulersModule.class,
        SearchModule.class,
        FavoritesModule.class
})
public interface AppComponent {

    void inject(SearchActivity searchActivity);

    void inject(FavoritesActivity favoritesActivity);

}
