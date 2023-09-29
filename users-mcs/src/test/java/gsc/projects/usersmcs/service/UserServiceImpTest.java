package gsc.projects.usersmcs.service;

import gsc.projects.usersmcs.converter.UserConverter;
import gsc.projects.usersmcs.dto.UserDto;
import gsc.projects.usersmcs.dto.UserUpdateDto;
import gsc.projects.usersmcs.model.User;
import gsc.projects.usersmcs.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserServiceImp userServiceImp;

    User user;

    User user2;

    UserDto userDto;

    UserUpdateDto userUpdateDto;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setEmail("test@email.com");

        user2 = new User();
        user2.setEmail("test@email.com");

        userDto = new UserDto();
        userDto.setEmail(user.getEmail());

        userUpdateDto = new UserUpdateDto();
        userUpdateDto.setEmail("newtest@email.com");

    }

    @Nested
    @Tag("Unit tests")
    public class UserUnitTests{


        @Test
        @DisplayName("Create a valid user")
        public void createAValidUser() {

            given(userRepository.findById(user.getId())).willReturn(null);

            when(userRepository.save(user)).thenReturn(user);

            userDto.setId(user.getId());

            assertEquals(userDto.getId(), user.getId());
        }

        @Test
        @DisplayName("Try to Create a exists user")
        public void tryCreateExistsUser() {

            given(userRepository.save(user)).willReturn(user);

            when(userRepository.save(user2)).thenReturn(null);

            assertEquals(userDto.getEmail(), user.getEmail());
        }

        @Test
        @DisplayName("Delete a valid user")
        public void deleteAValidUser() {

            given(userRepository.save(user)).willReturn(user);

            when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));

            userRepository.delete(user);

            assertNotEquals(1L, user.getId());
        }

        @Test
        @DisplayName("Update an valid user")
        public void updateAnValidUser(){

            given(userRepository.save(user)).willReturn(user);

            when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));

            given(userServiceImp.updateUser(user.getId(), userUpdateDto)).willReturn(userDto);

            assertEquals(user.getEmail(), userUpdateDto.getEmail());
        }
    }

}