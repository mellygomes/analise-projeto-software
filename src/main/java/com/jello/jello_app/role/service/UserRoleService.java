package com.jello.jello_app.role.service;

import com.jello.jello_app.user.model.User;

public interface UserRoleService {
    User grantAdmin(Long userId);

    User revokeAdmin(Long userId);

    User grantModerator(Long userId);

    User revokeModerator(Long userId);
}
