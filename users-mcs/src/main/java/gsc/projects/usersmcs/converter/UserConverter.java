package gsc.projects.usersmcs.converter;


import gsc.projects.usersmcs.dto.UserCreateDto;
import gsc.projects.usersmcs.dto.UserDto;
import gsc.projects.usersmcs.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {


    public UserDto toDto(User user){
        return new UserDto().builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public User fromCreateDto(UserCreateDto userCreateDto){
        return new User().builder()
                .withName(userCreateDto.getName())
                .withEmail(userCreateDto.getEmail())
                .withAge(userCreateDto.getAge())
                .withLocation(userCreateDto.getLocation())
                .build();
    }
}
