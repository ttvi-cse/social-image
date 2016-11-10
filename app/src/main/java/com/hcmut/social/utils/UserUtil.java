package com.hcmut.social.utils;

import com.hcmut.social.controller.restcontroller.RESTController;

/**
 * Created by John on 11/11/2016.
 */

public class UserUtil {
    public static String getAvatarLink(String userId) {
        return RESTController.ROOT_URL + String.format(RESTController.AVATAR_PATH, userId);
    }
}
