package com.techyourchance.testdoublesfundamentals.exercise4;

import com.techyourchance.testdoublesfundamentals.example4.networking.NetworkErrorException;
import com.techyourchance.testdoublesfundamentals.exercise4.networking.UserProfileHttpEndpointSync;
import com.techyourchance.testdoublesfundamentals.exercise4.users.User;
import com.techyourchance.testdoublesfundamentals.exercise4.users.UsersCache;

import org.jetbrains.annotations.Nullable;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.techyourchance.testdoublesfundamentals.exercise4.FetchUserProfileUseCaseSync.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class FetchUserProfileUseCaseSyncTest {

    private FetchUserProfileUseCaseSync SUT;
    private UserProfileHttpEndpointSyncTd userProfileHttpEndpointSync;
    private UsersCacheTd usersCache;
    private final static String USER_ID = "userID";
    private final static String FULL_NAME = "Android Studio";
    private final static String IMAGE_URL = "www.jpeg.org";

    @Before
    public void setup() {
        userProfileHttpEndpointSync = new UserProfileHttpEndpointSyncTd();
        usersCache = new UsersCacheTd();
        SUT = new FetchUserProfileUseCaseSync(userProfileHttpEndpointSync, usersCache);
    }

    @Test
    public void getUserProfile_userID_passed_to_endpoint() {
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(userProfileHttpEndpointSync.userId, is(USER_ID));
    }

    @Test
    public void getUserProfile_user_is_cached() {
        SUT.fetchUserProfileSync(USER_ID);
        User user = usersCache.getUser(USER_ID);
        assertThat(user.getUserId(), is(USER_ID));
        assertThat(user.getFullName(), is(FULL_NAME));
        assertThat(user.getImageUrl(), is(IMAGE_URL));
    }

    @Test
    public void getUserProfile_generalError_userNotCached() {
        userProfileHttpEndpointSync.isGeneralError = true;
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(usersCache.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void getUserProfile_authError_userNotCached() {
        userProfileHttpEndpointSync.isAuthError = true;
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(usersCache.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void getUserProfile_serverError_userNotCached() {
        userProfileHttpEndpointSync.isServerError = true;
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(usersCache.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void getUserProfile_networkError_userNotCached() {
        userProfileHttpEndpointSync.isNetworkError = true;
        SUT.fetchUserProfileSync(USER_ID);
        assertThat(usersCache.getUser(USER_ID), is(nullValue()));
    }

    @Test
    public void getUserProfile_success_successReturned() {
        UseCaseResult result  = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(UseCaseResult.SUCCESS));
    }

    @Test
    public void getUserProfile_authError_errorReturned() {
        userProfileHttpEndpointSync.isAuthError = true;
        UseCaseResult result  = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void getUserProfile_generalError_errorReturned() {
        userProfileHttpEndpointSync.isGeneralError = true;
        UseCaseResult result  = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void getUserProfile_serverError_errorReturned() {
        userProfileHttpEndpointSync.isServerError = true;
        UseCaseResult result  = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(UseCaseResult.FAILURE));
    }

    @Test
    public void getUserProfile_networkError_errorReturned() {
        userProfileHttpEndpointSync.isNetworkError = true;
        UseCaseResult result  = SUT.fetchUserProfileSync(USER_ID);
        assertThat(result, is(UseCaseResult.NETWORK_ERROR));
    }

    private static class UserProfileHttpEndpointSyncTd implements UserProfileHttpEndpointSync {

        public boolean isGeneralError;
        public boolean isAuthError;
        public boolean isServerError;
        public boolean isNetworkError;
        String userId;

        @Override
        public EndpointResult getUserProfile(String userId) throws NetworkErrorException {
            this.userId = userId;
            if (isGeneralError) {
                return new EndpointResult(EndpointResultStatus.GENERAL_ERROR, "", "", "");
            } else if (isAuthError) {
                return new EndpointResult(EndpointResultStatus.AUTH_ERROR, "", "", "");
            } else if (isServerError) {
                return new EndpointResult(EndpointResultStatus.SERVER_ERROR, "", "", "");
            } else if (isNetworkError) {
                throw new NetworkErrorException();
            }
            return new EndpointResult(EndpointResultStatus.SUCCESS, userId, FULL_NAME, IMAGE_URL);
        }
    }

    private static class UsersCacheTd implements UsersCache {

        private List<User> users = new ArrayList<>(1);

        @Override
        public void cacheUser(User user) {
            User existingUser = getUser(user.getUserId());
            if (existingUser != null) {
                users.remove(existingUser);
            }
            users.add(user);
        }

        @Override
        @Nullable
        public User getUser(String userId) {
            for (User user : users) {
                if (user.getUserId().equals(userId)) {
                    return user;
                }
            }
            return null;
        }
    }

}