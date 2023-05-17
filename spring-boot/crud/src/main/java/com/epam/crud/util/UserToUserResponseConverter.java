package com.epam.crud.util;

import com.epam.crud.model.User;
import com.epam.crud.model.UserResponse;

public class UserToUserResponseConverter {

    public static UserResponse convertToUserResponse(User user) {
        return new UserResponse(user.getName(), user.getEmail());
    }
}
