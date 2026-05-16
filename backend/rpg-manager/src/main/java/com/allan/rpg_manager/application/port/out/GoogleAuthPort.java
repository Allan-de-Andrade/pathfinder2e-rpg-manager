package com.allan.rpg_manager.application.port.out;

import com.allan.rpg_manager.application.dtos.GoogleUserInfo;

public interface GoogleAuthPort {
    GoogleUserInfo verifyIdToken(String idToken);

}
