package com.example.rickandmorty.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rickandmorty.R;
import com.example.rickandmorty.activities.MainActivity;
import com.example.rickandmorty.application.rickandmortyusecases.getcharacters.CharactersUseCase;
import com.example.rickandmorty.application.rickandmortyusecases.getcharacters.ICharactersUseCase;
import com.example.rickandmorty.databinding.FragmentHomeBinding;
import com.example.rickandmorty.domain.sqlite.characters.Character;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button buttonRefresh;
    private ICharactersUseCase charactersUseCase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        charactersUseCase = new CharactersUseCase();

        buttonRefresh = binding.getRoot().findViewById(R.id.refresh_button);


        buttonRefresh.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                List<Character> characters = charactersUseCase.executeGetCharacters((MainActivity) getContext());

                Integer size = characters.size();
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}