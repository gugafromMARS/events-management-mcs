package gsc.projects.usersmcs.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String email;

    private int age;

    private String location;

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {

        private User user;

        public UserBuilder() {
            user = new User();
        }

        public UserBuilder withName(String name){
            user.setName(name);
            return this;
        }

        public UserBuilder withEmail(String email){
            user.setEmail(email);
            return this;
        }
        public UserBuilder withAge(int age){
            user.setAge(age);
            return this;
        }

        public UserBuilder withLocation(String location){
            user.setLocation(location);
            return this;
        }

        public User build(){
            return user;
        }

    }
}
