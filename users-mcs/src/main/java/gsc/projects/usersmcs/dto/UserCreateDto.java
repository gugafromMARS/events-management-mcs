package gsc.projects.usersmcs.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {

    private String name;

    private String email;

    private int age;

    private String location;
}
