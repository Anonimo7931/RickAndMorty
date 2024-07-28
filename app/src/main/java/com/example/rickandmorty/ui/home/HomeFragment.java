package com.example.rickandmorty.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    private Button buttonSave;
    private Button buttonDelete;
    private Integer indicatorOfCharacterId = 0;
    private ImageView characterImage;
    private List<Character> characters = new ArrayList<>();
    private EditText descriptionCharacter;
    private TextView tittleImage;
    private Character globalCharacter;
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
        buttonSave = binding.getRoot().findViewById(R.id.save_button);
        buttonDelete = binding.getRoot().findViewById(R.id.delete_button);

        characterImage = binding.getRoot().findViewById(R.id.image_view);
        tittleImage = binding.getRoot().findViewById(R.id.tittle_text);
        descriptionCharacter = binding.getRoot().findViewById(R.id.description_text);

        characters = charactersUseCase.executeGetCharacters((MainActivity) getContext());

        updateCharacter();

        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(characters.isEmpty())
                    return;

                indicatorOfCharacterId = (indicatorOfCharacterId + 1) % characters.size();

                updateCharacter();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                indicatorOfCharacterId = indicatorOfCharacterId - 1;

                if(indicatorOfCharacterId < 0)
                    indicatorOfCharacterId = characters.size() - 1;

                updateCharacter();
            }
        });

        buttonRefresh.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                characters = charactersUseCase.executeGetCharacters((MainActivity) getContext());

                indicatorOfCharacterId = 0;

                updateCharacter();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(globalCharacter == null)
                    return;

                globalCharacter.description = descriptionCharacter.getText().toString();

                charactersUseCase.executeUpdateCharacter((MainActivity) getContext(), globalCharacter);

                characters = charactersUseCase.executeGetCharacters((MainActivity) getContext());
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(globalCharacter == null)
                    return;

                charactersUseCase.executeDeleteCharacter((MainActivity) getContext(), globalCharacter);

                characters = charactersUseCase.executeGetCharacters((MainActivity) getContext());

                indicatorOfCharacterId = 0;

                updateCharacter();
            }
        });

        return root;
    }

    private void updateCharacter(){

        if(characters.isEmpty()) {
            globalCharacter = null;

            tittleImage.setText(R.string.example_tittle_name);

            descriptionCharacter.setText("");

            Picasso.get()
                    .load(R.drawable.placeholder_image)
                    .into(characterImage);


            charactersUseCase.executeSetCharacter(getContext());

            return;
        }

        globalCharacter = characters.get(indicatorOfCharacterId);

        tittleImage.setText(globalCharacter.name);

        descriptionCharacter.setText(globalCharacter.description);

        Picasso.get()
                .load(globalCharacter.image)
                .placeholder(R.drawable.placeholder_image)
                .into(characterImage);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}