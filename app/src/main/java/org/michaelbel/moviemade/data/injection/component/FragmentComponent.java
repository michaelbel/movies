package org.michaelbel.moviemade.data.injection.component;

import org.michaelbel.moviemade.data.injection.PerFragment;
import org.michaelbel.moviemade.data.injection.module.FragmentModule;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    //void inject(NowPlayingFragment fragment);
}
