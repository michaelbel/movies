package org.michaelbel.moviemade.injection.component;

import org.michaelbel.moviemade.injection.PerFragment;
import org.michaelbel.moviemade.injection.module.FragmentModule;

import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    //void inject(NowPlayingFragment fragment);
}
