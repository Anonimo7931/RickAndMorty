package com.example.rickandmorty.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button buttonRefresh;
    private Button buttonNext;
    private Button buttonBack;
    private Integer indicatorOfCharacterId = 0;
    private ImageView characterImage;
    private List<Character> characters = new ArrayList<>();
    private TextView tittleImage;
    private ICharactersUseCase charactersUseCase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        charactersUseCase = new CharactersUseCase();

        buttonRefresh = binding.getRoot().findViewById(R.id.refresh_button);
        buttonNext = binding.getRoot().findViewById(R.id.next);
        buttonBack = binding.getRoot().findViewById(R.id.back);

        characterImage = binding.getRoot().findViewById(R.id.image_view);
        tittleImage = binding.getRoot().findViewById(R.id.tittle_text);

        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(characters.isEmpty())
                    return;

                indicatorOfCharacterId = (indicatorOfCharacterId + 1) % characters.size();

                tittleImage.setText(characters.get(indicatorOfCharacterId).name);

                Picasso.get()
                        .load(characters.get(indicatorOfCharacterId).image)
                        .placeholder(R.drawable.placeholder_image)
                        .into(characterImage);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(characters.isEmpty())
                    return;

                indicatorOfCharacterId = indicatorOfCharacterId - 1;

                if(indicatorOfCharacterId < 0)
                    indicatorOfCharacterId = characters.size() - 1;

                tittleImage.setText(characters.get(indicatorOfCharacterId).name);

                Picasso.get()
                        .load(characters.get(indicatorOfCharacterId).image)
                        .placeholder(R.drawable.placeholder_image)
                        .into(characterImage);
            }
        });

        buttonRefresh.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                characters = charactersUseCase.executeGetCharacters((MainActivity) getContext());

                if(characters.isEmpty())
                    return;

                tittleImage.setText(characters.get(indicatorOfCharacterId).name);

                Picasso.get()
                        .load(characters.get(indicatorOfCharacterId).image)
                        .placeholder(R.drawable.placeholder_image)
                        .into(characterImage);
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