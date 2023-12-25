package tdtu.report.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import tdtu.report.Repository.MusicRepository;

public class SuggestViewModelFactory implements ViewModelProvider.Factory {

    private final MusicRepository musicRepository;

    public SuggestViewModelFactory(MusicRepository repository) {
        this.musicRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SuggestViewModel.class)) {
            return (T) new SuggestViewModel(musicRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
