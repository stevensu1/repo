package com.mybatisflex.test;

import com.mybatisflex.core.query.QueryWrapper;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MyUserStorageProvider
        implements UserStorageProvider, UserLookupProvider, UserQueryProvider, CredentialInputValidator {

    protected KeycloakSession session;

    protected ComponentModel model;

    public MyUserStorageProvider(KeycloakSession session, ComponentModel model) {

        this.session = session;
        this.model = model;
    }

    @Override
    public boolean supportsCredentialType(String credentialType) {

        return PasswordCredentialModel.TYPE.endsWith(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {

        return supportsCredentialType(credentialType);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput credentialInput) {

        if (!this.supportsCredentialType(credentialInput.getType())) {
            return false;
        }

        StorageId sid = new StorageId(user.getId());
        String id = sid.getExternalId();

        SysUserMapper sysUserMapper = Util.getMapper(SysUserMapper.class);
        SysUser sysUser = sysUserMapper.selectOneById(id);
        String pwd = sysUser.getSysUserPassword();
        return pwd.equals(credentialInput.getChallengeResponse());

    }

    @Override
    public void close() {

    }

    @Override
    public UserModel getUserById(String id, RealmModel realm) {

        SysUserMapper sysUserMapper = Util.getMapper(SysUserMapper.class);
        SysUser sysUser = sysUserMapper.selectOneById(id);
        return mapUser(realm, sysUser);

    }

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {

        SysUserMapper sysUserMapper = Util.getMapper(SysUserMapper.class);
        List<SysUser> sysUsers =
                sysUserMapper.selectListByQuery(new QueryWrapper().where(SysUser::getSysUserName).like(username));
        if (!sysUsers.isEmpty()) {
            return mapUser(realm, sysUsers.get(0));
        } else {
            return null;
        }

    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {

        return null;
    }

    @Override
    public int getUsersCount(RealmModel realmModel) {

        SysUserMapper sysUserMapper = Util.getMapper(SysUserMapper.class);
        long count = sysUserMapper.selectCountByQuery(new QueryWrapper());
        return Integer.valueOf(count + "");
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm) {

        return null;
    }

    @Override
    public List<UserModel> getUsers(RealmModel realm, int firstResult, int maxResults) {

        SysUserMapper sysUserMapper = Util.getMapper(SysUserMapper.class);
        List<SysUser> sysUserList = sysUserMapper.selectAll();
        List<UserModel> users = new ArrayList<>();
        for (SysUser sysUser : sysUserList) {
            users.add(mapUser(realm, sysUser));
        }
        return users;
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm) {

        return searchForUser(search, realm, 0, 5000);
    }

    @Override
    public List<UserModel> searchForUser(String search, RealmModel realm, int firstResult, int maxResults) {

        SysUserMapper sysUserMapper = Util.getMapper(SysUserMapper.class);
        List<SysUser> sysUserList =
                sysUserMapper.selectListByQuery(new QueryWrapper().where(SysUser::getSysUserName).like(search));
        List<UserModel> users = new ArrayList<>();
        for (SysUser sysUser : sysUserList) {
            users.add(mapUser(realm, sysUser));
        }
        return users;
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {

        return searchForUser(params, realm, 0, 5000);
    }

    @Override
    public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm, int firstResult,
                                         int maxResults) {

        return getUsers(realm, firstResult, maxResults);
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {

        return Collections.emptyList();
    }

    @Override
    public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group, int firstResult, int maxResults) {

        return Collections.emptyList();
    }

    @Override
    public List<UserModel> searchForUserByUserAttribute(String attrName, String attrValue, RealmModel realm) {

        return Collections.emptyList();
    }

    private UserModel mapUser(RealmModel realm, SysUser sysUser) {

        return new UserAdapter(session, realm, model, sysUser);
    }

}